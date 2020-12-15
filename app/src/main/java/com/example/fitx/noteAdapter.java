package com.example.fitx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class noteAdapter extends FirestoreRecyclerAdapter<note,noteAdapter.noteHolder>{



    public noteAdapter(@NonNull FirestoreRecyclerOptions<note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull noteHolder holder, int position, @NonNull note model) {
        holder.email.setText(model.getEmail());
        holder.steps.setText(model.getSteps());
    }

    @NonNull
    @Override
    public noteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list,parent,false);
        return new noteHolder(v);
    }

    class noteHolder extends RecyclerView.ViewHolder{

        TextView email;
        TextView steps;
        public noteHolder(@NonNull View itemView) {
            super(itemView);
            email =itemView.findViewById(R.id.email1);
            steps=itemView.findViewById(R.id.walk);
        }
    }
}
