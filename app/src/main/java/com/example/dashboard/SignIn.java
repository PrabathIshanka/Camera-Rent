package com.example.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText Uloginemail;
    private EditText Uloginpassword;
    private Button btn_ulogin;
    private TextView btn_forgotpwd,btn_usignup;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Uloginemail = findViewById(R.id.txt_lemail);
        Uloginpassword = findViewById(R.id.txt_lpwd);
        btn_forgotpwd = findViewById(R.id.tviewforgotpwd);
        btn_ulogin = findViewById(R.id.btn_ulogin);
        btn_usignup = findViewById(R.id.btn_signup);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btn_ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        btn_usignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });
        btn_forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignIn.this,"Currently Configuration Mode",Toast.LENGTH_SHORT).show();

            }
        });



    }
    private void Login()
    {
        String emailtxt=Uloginemail.getText().toString();
        String passwordtxt=Uloginpassword.getText().toString();

        if(TextUtils.isEmpty(emailtxt))
        {Uloginemail.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(passwordtxt))
        {Uloginpassword.setError("Enter your password");
            return;
        }

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        Toast.makeText(SignIn.this,"Successfully Login",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignIn.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignIn.this,"Please verify your email address",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignIn.this,"Invalid email or password",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}