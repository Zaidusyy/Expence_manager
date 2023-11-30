package com.zaid.expmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zaid.expmanager.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    ArrayList<TransectionModel> transectionModelArrayList;
    TransectionAdapters transectionAdapters;

    int sumexp=0,sumic=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        transectionModelArrayList=new ArrayList<>();




        binding.history.setLayoutManager(new LinearLayoutManager(this));
        binding.history.setHasFixedSize(true);
        binding.addtrcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,addtransection.class);
                startActivity(intent);
            }
        });
        binding.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddata();
            }
        });
        loaddata();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loaddata();
    }

    private  void loaddata(){
        firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        transectionModelArrayList.clear();
                        sumexp=0;sumic=0;

                        for(DocumentSnapshot ds:task.getResult()){
                            TransectionModel model=new TransectionModel(ds.getString("id"),
                                    ds.getString("note"),
                                    ds.getString("Amount"),
                                    ds.getString("type"),
                                    ds.getString("date"));

                            try {
                                int amount=Integer.parseInt(ds.getString("Amount"));
                                if ((ds.getString("type").charAt(0))=='e' || (ds.getString("type").charAt(0))=='E') {
                                    sumexp = sumexp + amount;
                                } else {
                                    sumic = sumic + amount;
                                }
                            }
                            catch (NullPointerException e){
                                System.out.println(e);
                            };
                            transectionModelArrayList.add(model);
                        }
                        transectionAdapters=new TransectionAdapters(MainActivity.this,transectionModelArrayList);
                        binding.history.setAdapter(transectionAdapters);
                        binding.sumexpp.setText(String.valueOf(sumexp));
                        binding.sumicm.setText(String.valueOf(sumic));
                        binding.balance.setText(String.valueOf(sumic-sumexp));

                    }
                });
    }
}