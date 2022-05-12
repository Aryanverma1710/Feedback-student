package com.example.ksvcem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class loginSignIn extends AppCompatActivity {


    private EditText email_type, Pass_type;
    private Button loggingIn;

    private ProgressDialog progressDialog;

    private TextView register;

    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(loginSignIn.this, Introduction.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();


        loggingIn = findViewById(R.id.loggedin);

        register = findViewById(R.id.newuser);

        loggingIn = findViewById(R.id.loggedin);
        email_type = findViewById(R.id.Email);
        Pass_type = findViewById(R.id.Password);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(loginSignIn.this);
        progressDialog.setMessage("Loading...");


        loggingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog.show();
                userinfo();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginSignIn.this, login_authenticate.class));
            }
        });
    }


    private void userinfo() {
        String Email = email_type.getText().toString().trim();
        String Password = Pass_type.getText().toString().trim();

        if (!Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            if (!Password.isEmpty()) {

                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    progressDialog.hide();

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user.isEmailVerified()) {

                                        Intent intent = new Intent(loginSignIn.this, feedback_Input.class);
                                        intent.putExtra("Exit", false);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                        Toast.makeText(loginSignIn.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        user.sendEmailVerification();
                                        Toast.makeText(loginSignIn.this, "Check your Email to verify the account", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    progressDialog.hide();
                                    Toast.makeText(loginSignIn.this, "Incorrect Username and Password", Toast.LENGTH_LONG).show();

                                }
                            }

                        });

            } else {
                progressDialog.hide();
                Pass_type.setError("Enter the valid Password");
            }

        } else if (Email.isEmpty()) {
            progressDialog.hide();
            email_type.setError("Enter the valid Email");
        } else {
            email_type.setError("Enter the valid Email");
        }

        if (Password.length() < 6) {
            Pass_type.setError("Enter the valid Password");
        }
    }


}