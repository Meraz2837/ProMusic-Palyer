package com.example.promusicpalyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccessInfo extends AppCompatActivity {

    ListView data;
    TextView adname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_info);
        data = findViewById(R.id.listdata);
        adname = findViewById(R.id.adnameId);

        Intent myIntent = getIntent();
        String nameAdmin = myIntent.getStringExtra(AdminLogin.var);
        adname.setText(nameAdmin);


        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item,list);
        data.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("feedback");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshots : snapshot.getChildren()){
                    Information info = snapshots.getValue(Information.class);
                    String txt ="Name: "+ info.getName()  + "\nRating: " + info.getFeedback_star()+"\nSuggest Others: "+info.getYesnofeedback();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}