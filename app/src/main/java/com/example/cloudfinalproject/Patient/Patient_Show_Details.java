package com.example.cloudfinalproject.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.PatientTopicModule;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Patient_Show_Details extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    TextView  address,details;
    ImageView imageView;

//upload video
    String url;
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    int currentWindow=0;
    boolean playeWhenReady=true;
    long playBackPostion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_show_details);
        address=findViewById(R.id.show_details_address);
        details=findViewById(R.id.show_details_details);
        playerView=findViewById(R.id.show_details_video);
        imageView=findViewById(R.id.show_details_img);

        url = getIntent().getStringExtra("url");

        GetPatient();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        DocumentReference docRef = db.collection("DoctorTopic").document("topic_img");


//        storageRef.child("uploads/681340927663.jpg.png").getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        // Display the image using Glide or any other image loading library
//                        ImageView imageView = findViewById(R.id.show_details_img);
//                        Picasso.get(Patient_Show_Details.this).load(uri).into(imageView);
//                    }
//                });


    }
    ///////////////////////get doctor info

    private void GetPatient(String title) {

//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("DoctorTopic");
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("https://firebasestorage.googleapis.com/v0/b/cloudtrack-f24ba.appspot.com/o/images%2Fdownload.jpg?alt=media&token=4bece33d-4f5e-4ae1-8fbd-2b1c4269e955");
//
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    String text = dataSnapshot.getValue(String.class);
//                    String detail = dataSnapshot.getValue(String.class);
//
//                    address.setText(text);
//                    details.setText(detail);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle any errors
//            }
//        });
//
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Use an image loading library like Picasso or Glide to load and display the image
//                Picasso.get().load(uri).into(imageView);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Handle any errors
//            }
//        });


        db.collection("DoctorTopic").whereEqualTo("topic_address" , title).get()
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

                                    address.setText(title);
                                    details.setText(content);
//                                    Log.e("get", topic_items.toString());

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

    public void initVideo(){

        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);

        Uri uri = Uri.parse(url);
        DataSource.Factory datasource = new DefaultDataSourceFactory(this,"video");

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(datasource).createMediaSource(MediaItem.fromUri(uri));

        simpleExoPlayer.setPlayWhenReady(playeWhenReady);
        simpleExoPlayer.seekTo(currentWindow,playBackPostion);
        simpleExoPlayer.prepare(mediaSource,false,false);
    }
    /// stop video from playbackplayer
    public void realiseVideo(){
        if(simpleExoPlayer != null){
            playeWhenReady = simpleExoPlayer.getPlayWhenReady();
            playBackPostion = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();

            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }

    @Override
    protected void onPause() {
        realiseVideo();
        super.onPause();
    }

    @Override
    protected void onStop() {
        realiseVideo();
        super.onStop();
    }

    @Override
    protected void onStart() {
        initVideo();
        super.onStart();
    }
}