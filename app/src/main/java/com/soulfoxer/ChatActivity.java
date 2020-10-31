package com.soulfoxer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitMessage;
    private EditText message;
    private TextView messageHistory;
    private Client client;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        submitMessage = findViewById(R.id.buttonSubmitMessage);
        message = findViewById(R.id.editTextMessage);
        messageHistory = findViewById(R.id.textViewChat);
        messageHistory.setMovementMethod(new ScrollingMovementMethod());
        submitMessage.setOnClickListener(this);
        intent = this.getIntent();

        new Thread(new Runnable() {
            @Override
            public void run() {
                client = new Client(intent.getStringExtra("username"), intent.getStringExtra("ipAddress"), Integer.parseInt(intent.getStringExtra("port")), messageHistory);
            }
        }).start();


    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.sendMessage(message.getText().toString());
                message.setText("");
            }
        }).start();


    }
}