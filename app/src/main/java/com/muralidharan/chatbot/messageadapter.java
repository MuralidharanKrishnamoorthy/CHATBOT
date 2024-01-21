package com.muralidharan.chatbot;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;

import java.util.List;

public class messageadapter extends RecyclerView.Adapter<messageadapter.myviewholder>{

    public messageadapter(List<message> messagelist) {
        this.messagelist = messagelist;
    }

    List<message> messagelist;
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        myviewholder myviewholder = new myviewholder(chatview);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        message message=messagelist.get(position);
        if(message.getSentby().equals(message.sentbyme)) {
            holder.leftchat.setVisibility(View.GONE);
            holder.rightchat.setVisibility(View.VISIBLE);
            holder.righttext.setText(message.getMessage());
        }else {
            holder.rightchat.setVisibility(View.GONE);
            holder.leftchat.setVisibility(View.VISIBLE);
            holder.lefttext.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        LinearLayout leftchat,rightchat;
        TextView lefttext,righttext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            leftchat=itemView.findViewById(R.id.left_chat_view);
            rightchat=itemView.findViewById(R.id.right_chat_view);
            lefttext=itemView.findViewById(R.id.left_chat_text_view);
            righttext=itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}
