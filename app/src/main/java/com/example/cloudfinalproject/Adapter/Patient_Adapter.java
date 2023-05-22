package com.example.cloudfinalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudfinalproject.Patient.Patient_Show_Details;
import com.example.cloudfinalproject.R;
import com.example.cloudfinalproject.module.DoctorTopicModule;
import com.example.cloudfinalproject.module.PatientTopicModule;

import java.util.ArrayList;

public class Patient_Adapter extends RecyclerView.Adapter<Patient_Adapter.viewholder> {

    Context context;
    ArrayList<PatientTopicModule> topicArrayListp;

    public Patient_Adapter(Context context, ArrayList<PatientTopicModule> topicArrayListp) {
        this.context = context;
        this.topicArrayListp = topicArrayListp;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_patient_item,parent,false);
        return new Patient_Adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        PatientTopicModule p =topicArrayListp.get(position);
        holder.address.setText(p.getTaddress());
        holder.show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://firebasestorage.googleapis.com/v0/b/cloud-video-540e5.appspot.com/o/v%2FIMG_0.MOV?alt=media&token=f35c6fba-dacc-4d2e-b189-283a81347b9b";
                Intent intent = new Intent(context,Patient_Show_Details.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
//                 Intent intent = new Intent(context, Patient_Show_Details.class);
//                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicArrayListp.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {

        TextView address;
        Button show_details;



        public viewholder(@NonNull View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.show_address_p);
            this.show_details = itemView.findViewById(R.id.show_details_patient);



        }
    }

}
