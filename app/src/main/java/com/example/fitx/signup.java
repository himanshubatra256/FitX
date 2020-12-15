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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private TextView t1;
    private EditText name;
    public EditText email;
    private EditText pass;
    private EditText cpass;
    private EditText phn;
    private Button b1;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseAuth mAuth;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        apply();
        String dmail = name.getText().toString();
        name.addTextChangedListener(new TextWatcher() {
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
        phn.addTextChangedListener(new TextWatcher() {
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
        cpass.addTextChangedListener(new TextWatcher() {
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
                Intent in = new Intent(signup.this,login.class);
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

    private void checkInputs() {
        if(!TextUtils.isEmpty(name.getText()))
        {
            if(!TextUtils.isEmpty(email.getText()))
            {
                if(!TextUtils.isEmpty(phn.getText()))
                {
                    if((!TextUtils.isEmpty(pass.getText())))
                    {
                        if(!TextUtils.isEmpty(cpass.getText()))
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

                }else{
                    b1.setEnabled(false);
                    b1.setTextColor(Color.argb(50,0,0,0));
                }
            }else{
                b1.setEnabled(false);
                b1.setTextColor(Color.argb(50,0,0,0));
            }
        }else{
            b1.setEnabled(false);
            b1.setTextColor(Color.argb(50,0,0,0));
        }
    }

    private void checkEmail() {
        if(email.getText().toString().matches(emailPattern))
        {
            if(phn.getText().toString().length()==10)
            {
                if(pass.getText().toString().length()>=8)
                {

                    if(pass.getText().toString().equals(cpass.getText().toString()))
                    {
                        Toast.makeText(signup.this,"Loading please wait...",Toast.LENGTH_SHORT).show();
                        b1.setEnabled(false);
                        b1.setTextColor(Color.argb(50,0,0,0));
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {

                                            Map<String,String> userdata =new HashMap<>();
                                            userdata.put("name",name.getText().toString());
                                            userdata.put("phone",phn.getText().toString());
                                            userdata.put("email",email.getText().toString());



                                            firestore.collection("USERS").document(email.getText().toString()).set(userdata)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {

                                                                //Toast.makeText(signup.this,"Success",Toast.LENGTH_SHORT).show();
                                                                Intent in = new Intent(signup.this,details.class);
                                                                startActivity(in);
                                                            }
                                                            else
                                                            {
                                                                b1.setEnabled(true);
                                                                b1.setTextColor(Color.rgb(0,0,0));
                                                                String err = task.getException().getMessage();
                                                                Toast.makeText(signup.this,err,Toast.LENGTH_SHORT).show();
                                                            }
                                                        }


                                                    });
                                        }
                                        else
                                        {
                                            b1.setEnabled(true);
                                            b1.setTextColor(Color.rgb(0,0,0));
                                            String err = task.getException().getMessage();
                                            Toast.makeText(signup.this,err,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });





                    }else{

                        cpass.setError("Password didn't matched");
                    }


                }
                else{
                    pass.setError("Atleast 8 characters");
                }
            }else{
                phn.setError("Enter a valid number");
            }
        }else{
            email.setError("Invalid email");
        }
    }


    private void apply() {
        mAuth = FirebaseAuth.getInstance();
        t1 = (TextView)findViewById(R.id.t1);
        name=(EditText)findViewById(R.id.lemail);
        email=(EditText)findViewById(R.id.loginpass);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.cpass);
        phn=(EditText)findViewById(R.id.phn);
        b1=(Button)findViewById(R.id.b);

    }
}
