package com.example.promusicpalyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {
    EditText name, email, address;
    RadioGroup yesnofeed;
    Spinner feedback_star;
    long maxid = 0;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback_star = findViewById(R.id.feedbackspinnerID);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(this,R.array.feedback_star, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedback_star.setAdapter(myAdapter);
        name = findViewById(R.id.namefeedbackID);
        email = findViewById(R.id.emailfeedbackID);
        address = findViewById(R.id.addressfeedbackID);
        feedback_star = findViewById(R.id.feedbackspinnerID);
        yesnofeed = findViewById(R.id.rdyesnoGroup);


    }

    public void Submit_feedback(View view) {
        String nm = name.getText().toString();
        String ema = email.getText().toString();
        String addr = address.getText().toString();
        String feedback_st = feedback_star.getSelectedItem().toString();
        String feedback;
        if(yesnofeed.getCheckedRadioButtonId()==R.id.rdyesID){
            feedback = "Yes";
        }
        else {
            feedback = "no";
        }

        db = FirebaseDatabase.getInstance().getReference().child("feedback");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        feedbackdataHolder obj = new feedbackdataHolder(nm, ema,addr,feedback_st,feedback);
        if (nm.equals("") || ema.equals("") || addr.equals("")){
            Toast.makeText(this, "Please fill out all field", Toast.LENGTH_SHORT).show();
        }
        else {
            db.child(String.valueOf(maxid+1)).setValue(obj);
            Toast.makeText(getApplicationContext(), "Thanks for your Feedback", Toast.LENGTH_SHORT).show();
        }
    }
}