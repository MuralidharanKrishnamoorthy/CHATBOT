package com.muralidharan.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatbot.R;

public class loginactivity extends AppCompatActivity {
Button btnlogon,btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        btnlogon=findViewById(R.id.btnMainLogin);
        btnregister=findViewById(R.id.btnMainSignUp);
        btnlogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginactivity.this,loginactivity1.class);
                startActivity(intent);
                finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginactivity.this,signupactivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}