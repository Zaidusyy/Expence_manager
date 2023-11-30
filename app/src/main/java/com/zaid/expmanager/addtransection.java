package com.zaid.expmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zaid.expmanager.databinding.ActivityAddtransectionBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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

                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                String currentdateandtime=sdf.format(new Date());

                String id= UUID.randomUUID().toString();
                Map<String,Object> transection=new HashMap<>();
                transection.put("id",id);
                transection.put("Amount",amount);
                transection.put("note",note);
                transection.put("type",type);
                transection.put("date",currentdateandtime);

                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes").document(id).set(transection)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addtransection.this, "Success", Toast.LENGTH_SHORT).show();
                        binding.usernoteadd.setText("");
                        binding.useramountadd.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addtransection.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}