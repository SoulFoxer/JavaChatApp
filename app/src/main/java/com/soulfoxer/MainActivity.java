package com.soulfoxer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitButton;
    private EditText ipAddress;
    private EditText username;
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.buttonSubmit);
        ipAddress = findViewById(R.id.editTextIP);
        username = findViewById(R.id.editTextUserName);
        port = findViewById(R.id.editTextPort);
        submitButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {//todo text validation
        Intent toChatActivity = new Intent(this,ChatActivity.class);
        toChatActivity.putExtra("port",port.getText().toString());
        toChatActivity.putExtra("ipAddress",ipAddress.getText().toString());
        toChatActivity.putExtra("username",username.getText().toString());
        startActivity(toChatActivity);
        this.finish();
    }
}