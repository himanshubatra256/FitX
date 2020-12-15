package com.example.fitx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity {
private ImageButton prof;
private ImageButton srch;
private ImageButton fod;
private ImageButton exr;
private Button sout;
FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        prof = findViewById(R.id.imageButton);
        srch = findViewById(R.id.imageButton4);
        fod= findViewById(R.id.imageButton5);
        exr = findViewById(R.id.imageButton3);
        sout = findViewById(R.id.button5);

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profl = new Intent(menu.this, profile.class);
                startActivity(profl);
            }
        });
        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serch = new Intent(menu.this,search.class);
                startActivity(serch);
            }
        });

        fod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(menu.this,food.class);
                startActivity(in);
            }
        });
        exr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn = new Intent(menu.this,workout.class);
                startActivity(inn);
            }
        });

        sout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                Intent onn = new Intent(menu.this,login.class);
                startActivity(onn);
            }
        });

    }
}
