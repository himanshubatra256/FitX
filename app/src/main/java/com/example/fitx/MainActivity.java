package com.example.fitx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static int sp=1200;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser==null)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(MainActivity.this,login.class);
                    startActivity(in);
                    finish();
                }
            },sp);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(MainActivity.this,menu.class);
                    startActivity(in);
                    finish();
                }
            },sp);
        }
    }
}
