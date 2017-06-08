package com.ashik619.prokriyademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ashik619.prokriyademo.Models.User;
import com.ashik619.prokriyademo.custum_view.CustomTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.phoneNumText)
    EditText phoneNumText;
    @BindView(R.id.nameText)
    EditText nameText;

    DatabaseReference mDatabase;
    DatabaseReference userReference;
    String username;
    @BindView(R.id.movieName)
    CustomTextView movieName;
    @BindView(R.id.otpText)
    EditText otpText;
    @BindView(R.id.nextButton)
    Button nextButton;
    User user;
    boolean otpFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ProkriyaApplication.getLocalPrefInstance().getName()!=null){
            Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        user = new User();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpFlag){
                    addUser();
                }else showOtp();

            }
        });
    }


    void showOtp(){
        username = nameText.getText().toString();
        if(!username.equals("")) {
            otpFlag = true;
            nextButton.setText("NEXT");
            otpText.setVisibility(View.VISIBLE);
        }
    }

    void addUser() {
        user.setName(username);
        userReference = mDatabase.child("Users");
        userReference.push().setValue(user);
        ProkriyaApplication.getLocalPrefInstance().setName(username);
        Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
