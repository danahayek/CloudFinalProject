package com.example.cloudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeDoctor extends AppCompatActivity {

    Button add_btnbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);

        add_btnbtn = findViewById(R.id.add_topics_DOC);
        add_btnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeDoctor.this,Doctor_addTopics.class));

            }
        });
    }
}