package com.example.fitx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class food extends AppCompatActivity {
private Button wl;
private Button wg;
private Button men;
private Button maintain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        wl= findViewById(R.id.button4);
        wg= findViewById(R.id.button7);
        maintain= findViewById(R.id.button8);
        men= findViewById(R.id.button9);

        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(food.this,menu.class);
                startActivity(in);
            }
        });

        wl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn = new Intent(food.this,wl.class);
                startActivity(inn);
            }
        });

        wg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn = new Intent(food.this,wg.class);
                startActivity(inn);
            }
        });

        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inn = new Intent(food.this,maintain.class);
                startActivity(inn);
            }
        });

    }
}
