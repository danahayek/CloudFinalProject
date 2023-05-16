package com.example.cloudfinalproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cloudfinalproject.Adapter.Doctor_Adapter;
import com.example.cloudfinalproject.ChooseActivity;
import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.Topics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeDoctor extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerDoc;
    Doctor_Adapter adapterDoc;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<Topics> topic_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);


        recyclerDoc = findViewById(R.id.recycler_doc);
        topic_items = new ArrayList<Topics>();
        adapterDoc = new Doctor_Adapter(this,topic_items,null);
        recyclerDoc.setAdapter(adapterDoc);

       // String nn = getIntent().getStringExtra("name");
        GetNote();
    }
    private void GetNote() {

        db.collection("Topics").get()
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
                                    String title = documentSnapshot.getString("topic_title");
                                    String content = documentSnapshot.getString("topic_content");
                                    String image = documentSnapshot.getString("image");



                                    Topics c = new Topics(title,content,image);
                                    topic_items.add(c);

                                    recyclerDoc.setLayoutManager(layoutManager);
                                    recyclerDoc.setHasFixedSize(true);
                                    recyclerDoc.setAdapter(adapterDoc);
                                    ;
                                    adapterDoc.notifyDataSetChanged();
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


    //option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_topic:
                startActivity(new Intent(HomeDoctor.this, Doctor_addTopics.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(HomeDoctor.this, Doctor_Profile.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(HomeDoctor.this, Login_doctor.class));
                return true;
            case R.id.chat:
                Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}