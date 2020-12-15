package com.example.fitx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class search extends AppCompatActivity {
    private ImageView imangeview;

FirebaseAuth mauth = FirebaseAuth.getInstance();
FirebaseFirestore firestore = FirebaseFirestore.getInstance();

CollectionReference noteref = firestore.collection("USERS");
private noteAdapter adapter;
public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imangeview = findViewById(R.id.imageView14);
        id = mauth.getCurrentUser().getEmail();
        setuprecycle();


        imangeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(search.this,menu.class);
                startActivity(in);
            }
        });
    }

    private void setuprecycle() {

        Query query = noteref.orderBy("email", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<note> options = new FirestoreRecyclerOptions.Builder<note>()
                .setQuery(query,note.class).build();
        adapter = new noteAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
