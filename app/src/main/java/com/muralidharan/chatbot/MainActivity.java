package com.muralidharan.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.ConnectivityManager;
import com.example.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    EditText editText;
    ImageView imageView;
    List<message> messageList;
    messageadapter messageadapter;
    ImageView btnlogout;
    ImageView voice;
    FirebaseAuth mAuth;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
       //getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        recyclerView=findViewById(R.id.chat_rv);
        textView=findViewById(R.id.txtWelcome);
        editText=findViewById(R.id.message_edit_text);
        imageView=findViewById(R.id.send_btn);
        btnlogout=findViewById(R.id.btnlogout);
       // voice=findViewById(R.id.voice_recognition_btn);
        mAuth = FirebaseAuth.getInstance();
      //  SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent= new Intent(MainActivity.this,loginactivity1.class);
                startActivity(intent);
                finish();
            }
        });
        messageList=new ArrayList<>();

        // recyclerview code
        messageadapter=new messageadapter(messageList);
        recyclerView.setAdapter(messageadapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=editText.getText().toString().trim();
                addtochat(question,message.sentbyme);
                editText.setText("");
                if(isNetworkAvailable()) {
                    callapi(question);
                    textView.setVisibility(View.GONE);
                }
                else{
                    addresponse("NO internet connection");
                }

            }

        });

    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    void addtochat(String message, String sentby){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new message(message,sentby));
                messageadapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageadapter.getItemCount());
            }
        });
    }

    void addresponse(String response){
        messageList.remove(messageList.size()-1);
        addtochat(response,message.sentbybot);
    }
    void callapi(String question){
        messageList.add(new message("Typing.....",message.sentbybot));
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("model","gpt-3.5-turbo");
            JSONArray messagearr = new JSONArray();
            JSONObject obj =new JSONObject();
            obj.put("role","user");
            obj.put("content",question);
            messagearr.put(obj);
            jsonObject.put("messages",messagearr);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.MINUTES)
                // Set overall timeout for the call
                .readTimeout(2,TimeUnit.MINUTES)
                .build();
        RequestBody body=RequestBody.create(jsonObject.toString(),JSON);
        Request request=new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addresponse("Failed to Load response due to "+e.getMessage());
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
if(response.isSuccessful()){
    try {
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray jsonArray=null;
        jsonArray=jsonObject.getJSONArray("choices");
        String result = jsonArray.getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
        addresponse(result.trim());
    }
    catch (JSONException e){
        throw new RuntimeException(e);
    }

}
else {
    addresponse("Failed to Response due to "+response.body().string());
}
            }
        });
        }
}