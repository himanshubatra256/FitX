package com.example.fitx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class goals extends AppCompatActivity {
private RadioGroup gol;
private RadioButton select;
private Button back;
private Button next;
private String id;
    public double burn =0.0;
    public double cal =0.0;
private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
private FirebaseAuth mauth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        id = mauth.getCurrentUser().getEmail();

        firestore.collection("USERS").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    burn = documentSnapshot.getDouble("calories");
                    cal = burn;
                }else{
                    Toast.makeText(goals.this,"Document doesn't exixts", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(goals.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        back =  findViewById(R.id.button3);
        next = findViewById(R.id.button2);
        gol = findViewById(R.id.radioGroup2);
        int rid = gol.getCheckedRadioButtonId();
        select = findViewById(rid);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(goals.this, details.class);
                startActivity(in);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gol = findViewById(R.id.radioGroup2);
                int rid = gol.getCheckedRadioButtonId();
                select = findViewById(rid);

                if(select.getText().equals("Lose Fat"))
                {
                    burn = burn+200;
                }
                else if(select.getText().equals("Gain weight"))
                {
                    burn = burn - 200;
                }
                else if(select.getText().equals("Maintain this STATE"))
                {
                    burn = cal;
                }
                firestore.collection("USERS").document(id).update("burn",burn,"steps","").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent nxt = new Intent(goals.this,profile.class);
                            startActivity(nxt);
                        }else{
                            Toast.makeText(goals.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}
