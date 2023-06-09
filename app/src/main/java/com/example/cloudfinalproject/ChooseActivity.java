package com.example.cloudfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cloudfinalproject.Doctor.Signup_Doctor;
import com.example.cloudfinalproject.Patient.Signup_patient;

public class ChooseActivity extends AppCompatActivity {

    CardView patientCard;
    CardView doctorCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        patientCard = findViewById(R.id.patientCard);
        doctorCard = findViewById(R.id.doctorCard);

        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this, Signup_Doctor.class));

            }
        });

        patientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseActivity.this, Signup_patient.class));

            }
        });
    }
}