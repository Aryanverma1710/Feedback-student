package com.example.ksvcem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Introduction extends AppCompatActivity {

    Button Login;
    Button Register;
    TextView Contact;
    private boolean isBackPressedOnce = false;

    @Override
    public void onBackPressed() {
        if(isBackPressedOnce) {
         super.onBackPressed();
         return;
        }
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();
        isBackPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        getSupportActionBar().hide();


        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);
        Contact = findViewById(R.id.contact_us);

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(Introduction.this, "Logged In", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(Introduction.this , loginSignIn.class);
                startActivity(intent1);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent2 = new Intent(Introduction.this , login_authenticate.class);
                startActivity(intent2);
            }
        });

        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Contact.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://ksvira.edu.in/vira_home.php"));
                startActivity(i);
            }
        });


    }
}