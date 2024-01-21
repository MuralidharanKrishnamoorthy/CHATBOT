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
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity1 extends AppCompatActivity {
    TextView textsignup,textforgotpassword;
    EditText editemail,editpassword;
    Button btnsignin;
    ProgressBar progressbar;
    String stremail,strpassword;
    FirebaseAuth mAuth;
    String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity1);

        textforgotpassword=findViewById(R.id.txtForgotPassword);
        textsignup=findViewById(R.id.txtSignUp);
        editemail=findViewById(R.id.edtSignInEmail);
        editpassword=findViewById(R.id.edtSignInPassword);
        btnsignin=findViewById(R.id.btnSignIn);
        progressbar=findViewById(R.id.signInProgressBar);
       mAuth=FirebaseAuth.getInstance();
       textsignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(loginactivity1.this,signupactivity.class);
               startActivity(intent);
               finish();
           }
       });
        textforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginactivity1.this,forgotpasswordactivity.class);
                startActivity(intent);
              //  finish();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stremail = editemail.getText().toString().trim();
                strpassword=editpassword.getText().toString();
                if(isvalidate()){
                    signin();
                }

            }

            private void signin() {
                btnsignin.setVisibility(View.INVISIBLE);
                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(stremail,strpassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent= new Intent(loginactivity1.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(loginactivity1.this,"ERROR-"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                btnsignin.setVisibility(View.VISIBLE);
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                        });
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
        if(TextUtils.isEmpty(strpassword)){
            editpassword.setError("password can't empty");
            return false;
        }
        return true;
    }
}