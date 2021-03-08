package com.example.atividade2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editTextTextPersonName);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
        intent.putExtra("message", et.getText().toString());
        startActivity(intent);
    }
}