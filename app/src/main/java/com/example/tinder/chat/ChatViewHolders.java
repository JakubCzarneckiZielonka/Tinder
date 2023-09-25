package com.example.tinder.chat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.R;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMessage;
    public LinearLayout mContainer;

    public ChatViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMessage = itemView.findViewById(R.id.message);
        mContainer = itemView.findViewById(R.id.sendLayout);
    }


    @Override
    public void onClick(View view) {

    }
}
