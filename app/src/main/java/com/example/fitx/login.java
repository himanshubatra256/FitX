package com.example.fitx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private TextView t1;
    private EditText email;
    private EditText pass;
    private Button b1;
    private FirebaseAuth mAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apply();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(login.this,signup.class);
                startActivity(in);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });
    }

    private void checkEmail() {
        if(email.getText().toString().matches(emailPattern))
        {
            if(pass.getText().toString().length()>=8)
            {
                Toast.makeText(login.this,"Loading please wait...",Toast.LENGTH_SHORT).show();
                b1.setEnabled(false);
                b1.setTextColor(Color.argb(50,0,0,0));
                mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(login.this,menu.class);
                                    startActivity(in);
                                }
                                else{
                                    String err=task.getException().getMessage();
                                    Toast.makeText(login.this,err,Toast.LENGTH_SHORT).show();
                                    b1.setEnabled(true);
                                    b1.setTextColor(Color.rgb(0,0,0));
                                }
                            }
                        });
            }
            else{
                pass.setError("Atleast 8 Character");
            }
        }
        else{
            email.setError("Invaild email.");
        }
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(pass.getText()))
            {
                b1.setEnabled(true);
                b1.setTextColor(Color.rgb(0,0,0));
            }
            else{
                b1.setEnabled(false);
                b1.setTextColor(Color.argb(50,0,0,0));
            }
        }
        else{
            b1.setEnabled(false);
            b1.setTextColor(Color.argb(50,0,0,0));
        }
    }

    private void apply() {
        t1= (TextView)findViewById(R.id.t1);
        email=(EditText)findViewById(R.id.lemail);
        pass=(EditText)findViewById(R.id.loginpass);
        b1= (Button)findViewById(R.id.b);
        mAuth=FirebaseAuth.getInstance();
    }
    }

