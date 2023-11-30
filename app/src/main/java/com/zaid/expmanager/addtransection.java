package com.zaid.expmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zaid.expmanager.databinding.ActivityAddtransectionBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addtransection extends AppCompatActivity {

    ActivityAddtransectionBinding binding;
    String type;

    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddtransectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        binding.expencecheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Expense";
                binding.expencecheckbox.setChecked(true);
                binding.incomechcekbox.setChecked(false);
            }
        });
        binding.incomechcekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Income";
                binding.expencecheckbox.setChecked(false);
                binding.incomechcekbox.setChecked(true);
            }
        });

        binding.useraddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=binding.useramountadd.getText().toString().trim();
                String note=binding.usernoteadd.getText().toString();
                if(amount.length()<=0){
                    return;
                }
                String id= UUID.randomUUID().toString();
                Map<String, String> transection=new HashMap<>();
                transection.put("id",id);
                transection.put("Amount",amount);
                transection.put("note",note);
                transection.put("type",type);

                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes").document(id).set(transection);
            }
        });
    }
}