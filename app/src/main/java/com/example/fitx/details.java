package com.example.fitx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.RoundingMode;
import java.util.HashMap;

import org.w3c.dom.Text;

public class details extends AppCompatActivity {
    private RadioGroup gender;
    private RadioGroup ex;
    private EditText age;
    private EditText height;
    private EditText weight;
    private RadioButton gen;
    private RadioButton exe;
    private Button next;
    private TextView textView5;
    public double index=0;
    public double BMR =0;
    public double calories=0;
    private String id;
    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore =FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        apply();

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                check();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                check();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                check();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        id = mauth.getCurrentUser().getEmail();
        textView5.setText(id+"");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkerrors();
            }
        });


    }

    private void checkerrors() {
        gender = findViewById(R.id.radioGroup);
        ex = findViewById(R.id.radioGroup3);
      int  r1= gender.getCheckedRadioButtonId();
       int  r2 =ex.getCheckedRadioButtonId();

        gen= findViewById(r1);
        exe = findViewById(r2);


        if(Integer.parseInt(height.getText().toString())>0){
            if(Integer.parseInt(weight.getText().toString())>0){
                if(Integer.parseInt(age.getText().toString())>0){
                    if(gen.getText().equals("Male")) {
                        double w =Double.parseDouble(weight.getText().toString());
                        double h =Double.parseDouble(height.getText().toString());
                        double a =Double.parseDouble(age.getText().toString());
                        BMR= 66.47+(13.75*w)+(5.003*h)-(6.755*a);//bmr formula
                        if(exe.getText().equals("neglegible sports 0-2 days/week")){
                            index =1.2;
                        }else if(exe.getText().equals("light exercise/sports 1-3 days/week")){
                            index =1.375;
                        }
                        else if(exe.getText().equals("moderate exercise/sport 3-5 days/week")){
                            index =1.55;
                        }
                        calories=BMR*index;
                        //trimming
                        DecimalFormat df = new DecimalFormat("#.##");
                        df.setRoundingMode(RoundingMode.CEILING);

                        Toast.makeText(details.this,"Loading...",Toast.LENGTH_SHORT).show();
                        next.setEnabled(false);
                        next.setTextColor(Color.argb(50,0,0,0));
//                        HashMap<String,Double> userdata = new HashMap<>();
//                        userdata.put();
//                        userdata.put("weight",w);
//                        userdata.put("age",a);

                        firestore.collection("USERS").document(id).update("height",h,"weight",w,"age",a,"index",index,"BMR",BMR,"calories",calories,"gender","Male")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent in = new Intent(details.this, goals.class);
                                            startActivity(in);

                                        }else{
                                            next.setEnabled(true);
                                            next.setTextColor(Color.rgb(0,0,0));
                                            Toast.makeText(details.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                    else if(gen.getText().equals("Female"))
                    {
                        int w =Integer.parseInt(weight.getText().toString());
                        int h =Integer.parseInt(height.getText().toString());
                        int a =Integer.parseInt(age.getText().toString());
                        BMR= 655.1+(9.563*w)+(1.85*h)-(4.676*a);//bmr formula
                        if(exe.getText().equals("negligible sports 0-2 days/week")){
                            index =1.2;
                        }else if(exe.getText().equals("light exercise/sports 1-3 days/week")){
                            index =1.375;
                        }
                        else if(exe.getText().equals("moderate exercise/sport 3-5 days/week")){
                            index =1.55;
                        }
                        calories=BMR*index;

                        DecimalFormat df = new DecimalFormat("#.##");
                        df.setRoundingMode(RoundingMode.CEILING);


                        Toast.makeText(details.this,"Loading...",Toast.LENGTH_SHORT).show();
                        next.setEnabled(false);
                        next.setTextColor(Color.argb(50,0,0,0));
//                        HashMap<String,Double> userdata = new HashMap<>();
//                        userdata.put();
//                        userdata.put("weight",w);
//                        userdata.put("age",a);

                        firestore.collection("USERS").document(id).update("height",h,"weight",w,"age",a,"index",index,"BMR",BMR,"calories",calories,"gender","Female")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent in = new Intent(details.this, goals.class);
                                            startActivity(in);

                                        }else{
                                            next.setEnabled(true);
                                            next.setTextColor(Color.rgb(0,0,0));
                                            Toast.makeText(details.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }

                }else{
                    age.setError("age can not be zero");
                }
            }else{
                weight.setError("Weight can not be zero");
            }
        }else{
            height.setError("Height can not be zero");
        }
    }

    private void check() {

        if(!TextUtils.isEmpty(height.getText()))
        {
            if(!TextUtils.isEmpty(weight.getText()))
            {
                if(!TextUtils.isEmpty(age.getText()))
                {
                    next.setEnabled(true);
                    next.setTextColor(Color.rgb(0,0,0));
                }
                else{
                    next.setEnabled(false);
                    next.setTextColor(Color.argb(50,0,0,0));
                }
            }
            else {
                next.setEnabled(false);
                next.setTextColor(Color.argb(50,0,0,0));
            }
        }
        else{
            next.setEnabled(false);
            next.setTextColor(Color.argb(50,0,0,0));
        }
    }

    private void apply() {

        height= findViewById(R.id.editText);
        weight = findViewById(R.id.editText2);
        age = findViewById(R.id.age);
        next = findViewById(R.id.button);
        textView5 = findViewById(R.id.textView5);



    }
}
