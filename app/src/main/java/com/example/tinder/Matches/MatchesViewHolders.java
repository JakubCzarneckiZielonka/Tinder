package com.example.tinder.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinder.chat.ChatActivity;
import com.example.tinder.R;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchId, mMatchName, mLastTimeStamp, mLastMessage, mNeed, mGive, mBudget, mProfile;

    public ImageView mNotificationDot;
    public ImageView mMatchImage;

    public MatchesViewHolders(@NonNull View itemView){
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = itemView.findViewById(R.id.Matchid);
        mMatchName = itemView.findViewById(R.id.MatchImage);
        mLastMessage = itemView.findViewById(R.id.lastMessage);
        mLastTimeStamp = itemView.findViewById(R.id.lastTimeStamp);

        mNeed = itemView.findViewById(R.id.needid);
        mGive = itemView.findViewById(R.id.giveid);
        mBudget = itemView.findViewById(R.id.budgetid);
        mMatchImage = itemView.findViewById(R.id.MatchImage);
        mProfile = itemView.findViewById(R.id.profileid);
        mNotificationDot = itemView.findViewById(R.id.notification_dot);
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getApplicationContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", mMatchId.getText().toString());
        b.putString("matchName", mMatchName.getText().toString());
        b.putString("lastMessage", mLastMessage.getText().toString());
        b.putString("lastTimeStamp", mLastTimeStamp.getText().toString());
        b.putString("budget", mBudget.getText().toString());
        b.putString("need", mNeed.getText().toString());
        b.putString("give", mGive.getText().toString());
        b.putString("profile", mProfile.getText().toString());
        intent.putExtras(b);
        view.getApplicationContext().startActivity(intent);
    }
}
