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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.*;

public class signupactivity extends AppCompatActivity {
    TextView textsignin;
    EditText editfullname, editemail, editmobile, editpassword, editconfirmpassword;
    Button buttonsignup;
    ProgressBar progressBar;
    String strfullname, stremail, strmobile, strpassword, strconfirmpassword;
    String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        textsignin = findViewById(R.id.txtSignIn);
        editfullname = findViewById(R.id.edtSignUpFullName);
        editemail = findViewById(R.id.edtSignUpEmail);
        editmobile = findViewById(R.id.edtSignUpMobile);
        editpassword = findViewById(R.id.edtSignUpPassword);
        editconfirmpassword = findViewById(R.id.edtSignUpConfirmPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        buttonsignup = findViewById(R.id.btnSignUp);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signupactivity.this, loginactivity.class);
                startActivity(intent);
                //finish();
            }
        });
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strfullname=editfullname.getText().toString();
                stremail=editemail.getText().toString().trim();
                strmobile=editmobile.getText().toString().trim();
                strpassword=editpassword.getText().toString();
                strconfirmpassword=editconfirmpassword.getText().toString();

                if(isvalidate()){
                    Signup();
                }
            }
        });


    }

    private void Signup(){

        buttonsignup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(stremail,strpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           // private Object db;

            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String,Object> user= new HashMap<>();
                user.put("Fullname",strfullname);
                user.put("Email",stremail);
                user.put("Mobile",strmobile);
                //String collectionName = "users";
                db.collection("Users")
                        .document(stremail)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(signupactivity.this, MainActivity.class);
                                startActivity(intent);
                               // finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signupactivity.this,"ERROR - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                buttonsignup.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean isvalidate() {
        if(TextUtils.isEmpty(strfullname)){
            editfullname.setError("fullname can't empty");
            return false;
        }
        if(TextUtils.isEmpty(stremail)){
            editemail.setError("email can't empty");
            return false;
        }
        if(!stremail.matches(emailpatterns)){
            editemail.setError("Enter a valid email id:");
            return false;
        }
        if(TextUtils.isEmpty(strmobile)){
            editmobile.setError("mobile can't empty");
            return false;
        }
        if(TextUtils.isEmpty(strpassword)){
            editpassword.setError("password can't empty");
            return false;
        }
        if(TextUtils.isEmpty(strconfirmpassword)){
            editconfirmpassword.setError("confirm password can't empty");
            return false;
        }
        if(!strpassword.equals(strpassword)){
            editconfirmpassword.setError("confirmpasssword and password must same");
            return false;
        }






        return true;
    }
}
