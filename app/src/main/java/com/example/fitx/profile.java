package com.example.fitx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class profile extends AppCompatActivity {
private Double steps;
public Double calories=0.0;
public Double burn=0.0;
public Double height=0.0;
public Double weight=0.0;
    public Double age=0.0;
    public Double BMR=0.0;
    public Double index=0.0;
    public String gender;
    public String name;
    private TextView t1;
    private TextView t17;
    private TextView t18;
    private TextView t19;
    private TextView t20;
    private TextView t21;
    private TextView t22;
    private TextView bmr;
    private TextView step;
    private Button menu;
private ImageView edit;
public int getstep =90;


    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;

FirebaseFirestore firestore = FirebaseFirestore.getInstance();
FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        t1=findViewById(R.id.t1);
        t17=findViewById(R.id.t17);
        t18=findViewById(R.id.t18);
        t19=findViewById(R.id.t19);
        t20=findViewById(R.id.t20);
        t21=findViewById(R.id.t21);
        t22=findViewById(R.id.t22);
        bmr=findViewById(R.id.bmr);
        edit = findViewById(R.id.edit);
        menu = findViewById(R.id.menu);
        step = findViewById(R.id.step);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(profile.this,details.class);
                startActivity(in);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent men = new Intent(profile.this,menu.class);
                startActivity(men);
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {


                if (sensorEvent!= null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];

                    double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                    double MagnitudeDelta = Magnitude - MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if (MagnitudeDelta > 5){
                        stepCount++;
                     //   getstep = Integer.parseInt(stepCount.toString());
                        String str = mauth.getCurrentUser().getEmail();

                        firestore.collection("USERS").document(str).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    df.setRoundingMode(RoundingMode.CEILING);

                                    t1.setText(documentSnapshot.getString("name"));

                                    t17.setText(documentSnapshot.getString("gender"));
                                    t18.setText(df.format(documentSnapshot.getDouble("calories")));
                                    t19.setText(df.format(documentSnapshot.getDouble("burn")));
                                    t20.setText(df.format(documentSnapshot.getDouble("height")));
                                    t21.setText(df.format(documentSnapshot.getDouble("weight")));
                                    t22.setText(df.format(documentSnapshot.getDouble("age")));
                                    bmr.setText(df.format(documentSnapshot.getDouble("BMR")));


                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        firestore.collection("USERS").document(str).update("steps",stepCount.toString());

                    }
                    step.setText(stepCount.toString());
                   // step.setText(getstep+"");





                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);


    }


    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);

    }

}
