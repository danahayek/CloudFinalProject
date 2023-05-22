package com.example.cloudfinalproject.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.cloudfinalproject.Adapter.Doctor_Adapter;
import com.example.cloudfinalproject.Adapter.Patient_Adapter;
import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.DoctorTopicModule;
import com.example.cloudfinalproject.module.PatientTopicModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePatient extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerPAT;
    Patient_Adapter adapterPAT;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<PatientTopicModule> topic_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);

        recyclerPAT = findViewById(R.id.patient_recyler);
        topic_items = new ArrayList<PatientTopicModule>();
        adapterPAT = new Patient_Adapter(this,topic_items);
        recyclerPAT.setAdapter(adapterPAT);

        GetPatient();
    }

    private void GetPatient() {

        db.collection("DoctorTopic").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("get", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String title = documentSnapshot.getString("topic_address");
                                    String content = documentSnapshot.getString("topic_details");
                                    String image = documentSnapshot.getString("image");
                                    String video = documentSnapshot.getString("video");



                                    PatientTopicModule c = new PatientTopicModule(title);
                                    topic_items.add(c);

                                    recyclerPAT.setLayoutManager(layoutManager);
                                    recyclerPAT.setHasFixedSize(true);
                                    recyclerPAT.setAdapter(adapterPAT);
                                    ;
                                    adapterPAT.notifyDataSetChanged();
                                    Log.e("get", topic_items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("get", "get failed ");


                    }
                });
    }

}