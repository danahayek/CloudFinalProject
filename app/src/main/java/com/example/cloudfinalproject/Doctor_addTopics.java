package com.example.cloudfinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudfinalproject.image.upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Doctor_addTopics extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final int PICK_IMG_REQUEST = 1;
    Button choose_image_btn;
    Button load_btn;
    EditText textname;
    EditText textdetail;
    ProgressBar progressBar;
    ImageView imageView;
    private StorageTask storageTask;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_topics);

        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.image_add);
        textdetail = findViewById(R.id.topic_details);
        textname = findViewById(R.id.topic_address);
        load_btn = findViewById(R.id.add_btn);
        choose_image_btn = findViewById(R.id.choose_image);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        choose_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();
            }
        });
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storageTask != null && storageTask.isInProgress()){
                    Toast.makeText(Doctor_addTopics.this, "Upload in progress ", Toast.LENGTH_LONG).show();

                }else{
                    uploadFile();

                }
            }
        });
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor_addTopics.this,HomeDoctor.class));

            }
        });
    }
    private  void  openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
           // Picasso.with(this).loaad(uri).into(imageView);
            imageView.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    ///upload the image

    private  void uploadFile(){

        if (uri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(uri));

              storageTask=fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 2000);

                            Toast.makeText(Doctor_addTopics.this, "Upload successful", Toast.LENGTH_LONG).show();
                            upload upload = new upload(
                                    textname.getText().toString().trim(),
                                    textdetail.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Doctor_addTopics.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    /////////// add data to firebase
    private void add_topics(View v){
        String name = textname.getText().toString();
        String detail = textdetail.getText().toString();
        Map<String, Object> topic = new HashMap<>();
        if(!name.isEmpty() && !detail.isEmpty()) {

            topic.put("detail",detail);
            topic.put("name",name);

            db.collection("doctor_topics")
                    .add(topic)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("dana", "Note added successfully ");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("dana", "Failed to add note", e);
                        }
                    });

        }else {
            Toast.makeText(this,"please fill feild" ,Toast.LENGTH_SHORT).show();
        }
    }
}