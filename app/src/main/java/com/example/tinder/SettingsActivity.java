package com.example.tinder;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SettingsActivity extends AppCompatActivity {

    private EditText mNameField, mPhoneField;
    private ProgressBar spinner;
    private Button mConfirm;
    private ImageButton mBack;
    private ImageView mProgileImage;
    private EditText mBudget;
    private Spinner need, give;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String userId, name, phone, profileImageUrl, userSex, UserBudget, UserNeed,
    private int needIndex, giveIndex;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.pBar);
        spinner.setVisibility(View.GONE);

        mNameField = findViewById(R.id.name);
        mPhoneField = findViewById(R.id.phone);

        mProgileImage = findViewById(R.id.profileImage);
        mBack = findViewById(R.id.settingsBack);

        mConfirm = findViewById(R.id.confirm);
        mBudget = findViewById(R.id.budget_settings);
        need = findViewById(R.id.spinner_need_settings);
        give = findViewById(R.id.spinner_give_setting);


        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null && mAuth.getCurrentUser() != null)
            userId = mAuth.getCurrentUser().getUid();
        else  {
            finish();

        }
        
    }
}