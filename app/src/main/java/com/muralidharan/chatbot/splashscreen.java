package com.muralidharan.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;

public class splashscreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        getWindow().setBackgroundDrawableResource(R.drawable.background_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               /* if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(splashscreen.this,MainActivity.class));

                }else {
                    startActivity(new Intent(splashscreen.this,loginactivity.class));
                }
                finish();
 navigateToNextScreen();
            }*/
                navigateToNextScreen();
        }
        },3000);

    }
    private void navigateToNextScreen() {
        Intent intent;
        if (mAuth.getCurrentUser() != null) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, loginactivity.class);
        }
        startActivity(intent);
        finish();
    }
}
