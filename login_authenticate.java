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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class login_authenticate extends AppCompatActivity {

    private EditText mEmail, mPass, mPhone, mName, mRollNo, mBranch, mYear;

    private TextView mTextView;
    private Button submit;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(login_authenticate.this, Introduction.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_authenticate);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        submit =  findViewById(R.id.loggedin);
        mEmail =  findViewById(R.id.Email);
        mPass =   findViewById(R.id.Password);
        mPhone =  findViewById(R.id.Phone);
        mBranch = findViewById(R.id.branch);
        mYear = findViewById(R.id.year);



        mName =    findViewById(R.id.name);
        mRollNo =  findViewById(R.id.Roll_No);
        mTextView = findViewById(R.id.alreadylogin) ;

        progressDialog = new ProgressDialog(login_authenticate.this);
        progressDialog.setMessage("Loading...");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                createUser();
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_authenticate.this , loginSignIn.class));
            }
        });
    }

    private void createUser() {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();
        String fullName = mName.getText().toString().trim();
        String rollNumber = mRollNo.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String branchCourse = mBranch.getText().toString().trim();
        String yearCourse = mYear.getText().toString().trim();

        if(fullName.isEmpty()) {
            mName.setError("Enter a Valid Name");
            mName.requestFocus();
            return;
        }

        if(branchCourse.isEmpty()) {
            mBranch.setError("Enter a Valid Branch");
            mBranch.requestFocus();
            return;
        }

        if(yearCourse.isEmpty()) {
            mYear.setError("Enter a Valid Year");
            mYear.requestFocus();
            return;
        }

        if(rollNumber.isEmpty()) {
            mRollNo.setError("Enter a Valid Roll No");
            mRollNo.requestFocus();
            return;
        }

        if(phone.isEmpty()) {
            mPhone.setError("Enter a Valid Number");
            mPhone.requestFocus();
            return;
        }

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    user data = new user(fullName, rollNumber, email, pass, phone, branchCourse, yearCourse);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                            .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                progressDialog.hide();
                                                Toast.makeText(login_authenticate.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                            }else {
                                                progressDialog.hide();
                                                Toast.makeText(login_authenticate.this, "Failed to register", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login_authenticate.this, "Failed to register", Toast.LENGTH_LONG).show();
                    }
                });

            }else{
                mPass.setError("Enter a Valid Password");
            }

        } else if (email.isEmpty()) {
            mEmail.setError("Enter a Valid Email Address");
        } else {
            mEmail.setError("Enter the Valid Email Address");
        }

    }
}