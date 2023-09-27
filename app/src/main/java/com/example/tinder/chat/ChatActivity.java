package com.example.tinder.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tinder.Matches.MatchesActivity;
import com.example.tinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutParams mChatLayoutManager;

    private EditText mSendEditText;
    private ImageButton mBack;

    private ImageButton mSendButton;
    private String notification;
    private String currentUserID, matchId, chatId;
    private String matchName, matchGive, matchNeed, matchBudget, matchProfile;
    private String lastMessage, lastTimeStamp;
    private String message, createByUser, isSeen, messageId, currentUserName;
    private Boolean currentUserBoolean;
    private ValueEventListener seenListener;
    private DatabaseReference mDatabaseUser, mDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        matchId = getIntent().getExtras().getString("matchId");
        matchName = getIntent().getExtras().getString("matchName");
        matchGive = getIntent().getExtras().getString("matchGive");
        matchNeed = getIntent().getExtras().getString("matchNeed");
        matchBudget = getIntent().getExtras().getString("matchBudget");
        matchProfile = getIntent().getExtras().getString("matchProfile");
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(currentUserID).child("connections").child("matches").child(matchId).child("ChatId");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatId();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFocusable(false);
        mChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(getDataSetChat(), ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSendEditText = findViewById(R.id.message);
        mBack = findViewById(R.id.chatBack);

        mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = mSendEditText.getText().toString();
                sendMessage(messageText);
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldRight, int oldBottom, int oldTop) {
                if (bottom < oldBottom) {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, MatchesActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        Toolbar toolbar = findViewById(R.id.chatToolbar);
        setSupportActionBar(toolbar);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(createByUser);
        Map onchat = new HashMap();
        onchat.put("onChat", matchId);
        reference.updateChildren(onchat);

        DatabaseReference current = FirebaseDatabase.getInstance().getReference("Users")
                .child(matchId).child("connections").child("matches").child(currentUserID);
        Map lastSeen = new HashMap();
        lastSeen.put("lastSeen", "false");
        current.updateChildren(lastSeen);
    }

    @Override
    protected void onPause() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        Map onchat = new HashMap<>();
        onchat.put("onChat", "None");
        reference.updateChildren(onchat);
        super.onPause();
    }

    @Override
    protected void onStop() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        Map onchat = new HashMap<>();
        onchat.put("onChat","None");
        reference.updateChildren(onchat);
        super.onStop();
    }

    private void sendMessage(Object text){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(matchId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("onChat").exists())
                        if (dataSnapshot.child("notificationKey").exists())
                            notification = dataSnapshot.child("notificationKey").getValue().toString();
                    else {
                        notification = "";
                        }
                    if (!dataSnapshot.child("onZChat").getValue().equals().equals(currentUserID)) {
                        new SendNotification(text,"New message from:" + currentUserName, notification,
                                "activityToBeOpened", "MatchesActivity");
                    }else {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(currentUserID).child("connections").child("matches").child(matchId);
                        Map seenInfo = new HashMap();
                        seenInfo.put("lastSend", "false");
                        reference.updateChildren(seenInfo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        TextView mMatchNameTextView = findViewById(R.id.chatToolbar);
        mMatchNameTextView.setText(matchName);
        return true;
    }

    public void showProfile(View v) {
        LayoutInflater inflater = getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.item_profile, null);

        TextView name = popupView.findViewById(R.id.name);
        TextView image = popupView.findViewById(R.id.image);
        TextView budget = popupView.findViewById(R.id.budget);
        TextView mNeedIMage = popupView.findViewById(R.id.needImage);
        TextView mGiveIMage = popupView.findViewById(R.id.giveImage);

        name.setText(matchName);
        budget.setText(matchBudget);


        if (matchNeed.equals("Netflix")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.netflix));
        } else if (matchNeed.equals("Hulu")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hulu));
        } else if (matchNeed.equals("Vudu")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.vudu));
        } else if (matchNeed.equals("HBO Now")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hbo));
        } else if (matchNeed.equals("Youtube Originals")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.youtube));
        } else {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.none));
        }

        //give Image
        if (matchNeed.equals("Netflix")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.netflix));
        } else if (matchNeed.equals("Amazon Prime")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.amazon));
        } else if (matchNeed.equals("Hulu")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hulu));
        } else if (matchNeed.equals("Vudu")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.vudu));
        } else if (matchNeed.equals("HBO Now")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hbo));
        } else if (matchNeed.equals("Youtube Originals")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.youtube));
        } else {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.none));
        }
    }

    private List<ChatObject> getDataSetChat() {
        return null;
    }

    private void getChatId(){

    }
}