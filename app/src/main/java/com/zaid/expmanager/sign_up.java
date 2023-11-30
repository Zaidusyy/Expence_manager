package com.zaid.expmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zaid.expmanager.databinding.ActivitySignUpBinding;

public class sign_up extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.email.getText().toString();
                String pass1=binding.pass1.getText().toString();
                String pass2=binding.pass2.getText().toString();
                if(email.trim().length()<=0 || pass1.trim().length()<=0){
                    return;
                } else if (!pass1.equals(pass2)) {
                    Toast.makeText(sign_up.this, "Passwords must be same", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,pass1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(sign_up.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(sign_up.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_up.this, "Failed to Register "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        binding.guestb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sign_up.this,MainActivity.class);
                startActivity(intent);
            }
        });
        binding.signupq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sign_up.this,sign_in.class);
                startActivity(intent);
            }
        });
    }
}