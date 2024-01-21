package com.muralidharan.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpasswordactivity extends AppCompatActivity {
    Button btnreset,btnback;
    EditText editemail;
    String stremail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswordactivity);

        btnreset=findViewById(R.id.btnReset);
        btnback=findViewById(R.id.btnForgotPasswordBack);
        editemail=findViewById(R.id.edtForgotPasswordEmail);
        progressBar=findViewById(R.id.forgetPasswordProgressbar);
        mAuth=FirebaseAuth.getInstance();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stremail=editemail.getText().toString().trim();
                if(isvalidate()){
                    Resetpasssword();
                }

            }
        });


    }

    private void Resetpasssword() {
        btnreset.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(stremail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(forgotpasswordactivity.this,"RESET PASSWORD LINK SENT TO REGISTERED EMAIL ID",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(forgotpasswordactivity.this,loginactivity1.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(forgotpasswordactivity.this, "ERROR - "+e.getMessage(), Toast.LENGTH_SHORT).show();
                btnreset.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean isvalidate(){

        if(TextUtils.isEmpty(stremail)){
            editemail.setError("email can't empty");
            return false;
        }
        if(!stremail.matches(emailpatterns)){
            editemail.setError("Enter a valid email id:");
            return false;
        }
        return true;
    }
}
