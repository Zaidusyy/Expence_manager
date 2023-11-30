package com.zaid.expmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zaid.expmanager.databinding.ActivitySignInBinding;

public class sign_in extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.email.getText().toString();
                String pass=binding.pass.getText().toString();
                if(email.trim().length()<=0 || pass.trim().length()<=0){
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(sign_in.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sign_in.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_in.this, "Failed to Login " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });





        binding.signupq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(sign_in.this,sign_up.class);
//                startActivity(intent);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}