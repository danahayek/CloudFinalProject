package com.example.cloudfinalproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cloudfinalproject.Adapter.Doctor_Adapter;
import com.example.cloudfinalproject.ChooseActivity;
import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.DoctorModule;
import com.example.cloudfinalproject.module.DoctorTopicModule;
import com.example.cloudfinalproject.module.Topics;
import com.example.cloudfinalproject.module.showDoctor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeDoctor extends AppCompatActivity   implements Doctor_Adapter.ItemClickListener,Doctor_Adapter.EditClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerDoc;
    Doctor_Adapter adapterDoc;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<DoctorTopicModule> topic_items;

    EditText addresss,detailss;
    Button imagess,videos;

    ImageView imageView;
    VideoView videoView;

    StorageReference storageReference;
    Uri imageUri , videoUri;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);


        imageView=findViewById(R.id.editimage_add);
        videoView=findViewById(R.id.editvideoView);

        recyclerDoc = findViewById(R.id.recycler_doc);
        topic_items = new ArrayList<DoctorTopicModule>();
        adapterDoc = new Doctor_Adapter(this,topic_items,this,this);
        recyclerDoc.setAdapter(adapterDoc);


        addresss=findViewById(R.id.edittopic_address);
        detailss=findViewById(R.id.edittopic_details);
        imagess=findViewById(R.id.editchoose_image);
        videos=findViewById(R.id.editchoose_video);

        firebaseFirestore=FirebaseFirestore.getInstance();



//
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
//            @Override
//            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//                return 0;
//            }
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                adapterDoc.deleteItem(viewHolder.getAdapterPosition());
//            }
//        }).attachToRecyclerView(recyclerDoc);

        // String nn = getIntent().getStringExtra("name");
        GetDoctor();
    }

    private void GetDoctor() {

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


                                    DoctorTopicModule c = new DoctorTopicModule(title,id);
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
    public void Delete(final DoctorTopicModule showdoctor) {

        db.collection("DoctorTopic").document(showdoctor.getId())
                .delete()

                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void unused) {
                        topic_items.remove(showdoctor);
                        adapterDoc.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("logData", "get failed with delete");
                    }
                });
    }
    ///////////////////////edit///////////


    public void updateNote(final DoctorTopicModule n) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تعديل");
        final View customLayout = getLayoutInflater().inflate(R.layout.update_doctor, null);
        builder.setView(customLayout);
        builder.setPositiveButton(
                "تعديل",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addresss = customLayout.findViewById(R.id.edittopic_address);
                        detailss = customLayout.findViewById(R.id.edittopic_details);
                        imageView = customLayout.findViewById(R.id.editimage_add);
                        videoView = customLayout.findViewById(R.id.editvideoView);

                        db.collection("DoctorTopic").document(n.getId())
                                .update("topic_address", addresss.getText().toString() ,
                                        "topic_details",detailss.getText().toString(),
                                        "topic_img",uploadimage(imageView.setImageURI(imageUri)),
                                        "topic_video",videos.getText().toString())

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("edit", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("edit", "Error updating document", e);
                                    }
                                });
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void uploadimage(Uri uri) {
        storageReference = FirebaseStorage.getInstance().getReference("images/");
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(HomeDoctor.this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(HomeDoctor.this, HomeDoctor.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeDoctor.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
//    public void Delete(final Topics showdoctor) {
//
//        db.collection("Topics").document(showdoctor.getTopic_name())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        topic_items.remove(showdoctor);
//                        Toast.makeText(HomeDoctor.this, " Removed Successfully", Toast.LENGTH_SHORT).show();
//
//                        adapterDoc.notifyDataSetChanged();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("logData", "get failed with delete");
//                    }
//                });
//    }
//        AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(this);
//        alertDialogBuilderLabelDelete.create();
//        alertDialogBuilderLabelDelete.setIcon(R.drawable.delete);
//        alertDialogBuilderLabelDelete.setCancelable(false);
//        alertDialogBuilderLabelDelete.setTitle("Delete label");
//        alertDialogBuilderLabelDelete.setMessage("Are you sure to delete?");
//        alertDialogBuilderLabelDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogBox, int id) {
//
//
//            }
//        });
//        alertDialogBuilderLabelDelete.setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogBox, int id) {
//                        dialogBox.cancel();
//                    }
//                });        alertDialogBuilderLabelDelete.show();
//    }
//

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

    @Override
    public void onItemClick(int position, String id) {
        Delete(topic_items.get(position));

    }

    @Override
    public void onItemClick2(int position, String id) {
        updateNote(topic_items.get(position));

    }
}