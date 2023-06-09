package com.example.cloudfinalproject.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudfinalproject.ChooseActivity;
import com.example.cloudfinalproject.Doctor.Doctor_Update;
import com.example.cloudfinalproject.Doctor.Doctor_addTopics;
import com.example.cloudfinalproject.Doctor.Signup_Doctor;
import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.DoctorTopicModule;
import com.example.cloudfinalproject.module.showDoctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Doctor_Adapter extends RecyclerView.Adapter<Doctor_Adapter.ViewHolder> {

    Context context;
    ArrayList<DoctorTopicModule> topicArrayList;

    private ItemClickListener mClickListener;
    private EditClickListener eClickListener;


    public Doctor_Adapter(Context context, ArrayList<DoctorTopicModule> topicArrayList, ItemClickListener onClick, EditClickListener onClick2) {
        this.context = context;
        this.topicArrayList = topicArrayList;
        this.mClickListener = onClick;
        this.eClickListener=onClick2;




    }



    @NonNull
    @Override
    public Doctor_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_doc_item,parent,false);
        return new Doctor_Adapter.ViewHolder(view);
    }
    //delete
//    public void deleteItem(int position) {
//
//        topicArrayList.remove(position);
//        notifyItemRemoved(position);
//
////         FirebaseFirestore db = FirebaseFirestore.getInstance();
//        getSnapshots().getSnapshot(position).getReference().delete();
//
////        db.collection("DoctorTopic").whereEqualTo("topic_address position")
////                .delete();
//    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DoctorTopicModule n =topicArrayList.get(position);
        holder.show_name.setText(n.getTopic_address());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(holder.getAdapterPosition(),topicArrayList.get(position).id);            }
        });
       /// holder.show_img.setImageURI(uri);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eClickListener.onItemClick2(holder.getAdapterPosition(),topicArrayList.get(position).getTopic_address());

//                Intent intent = new Intent(context, Doctor_Update.class);
//                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView show_name;
        ImageView edit;
        ImageView delete;

        CardView show_card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.show_name = itemView.findViewById(R.id.show_title);
            this.edit=itemView.findViewById(R.id.edit_topic);
            this.delete=itemView.findViewById(R.id.delete_topic);
            this.show_card=itemView.findViewById(R.id.show_card);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }

    }
        public interface ItemClickListener {
            void onItemClick(int position, String id);

        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

    public interface EditClickListener {
        void onItemClick2(int position, String id);

    }

        void setClickListener(EditClickListener editClickListener) {
            this.eClickListener = editClickListener;
        }


    DoctorTopicModule getItem(int id) {

            return topicArrayList.get(id);
        }



    }