package com.example.promusicpalyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    EditText uname, pass;
    public static String var = "pass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        uname = findViewById(R.id.usernameID);
        pass = findViewById(R.id.PasswordID);
    }

    public void AdminPanel(View view) {
        String name = uname.getText().toString();
        String ps = pass.getText().toString();

        if(name.equals("Meraz") && ps.equals("2837")){
            Intent myIntent = new Intent(this,AccessInfo.class);
            myIntent.putExtra(var,name);
            startActivity(myIntent);
        }
        else {
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }
}