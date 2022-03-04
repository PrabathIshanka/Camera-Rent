package com.example.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText id,name,age,address,nic,lname,password,email,mobile,cpassword;
    TextView btn_signin;
    ImageView imageView;
    Button btn_save,btn_image;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference adddataRef;
    private static final int CAMERA_REQUEST =123 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.txt_name);
        age = findViewById(R.id.txt_Age);
        address = findViewById(R.id.txt_address);
        nic = findViewById(R.id.txt_nic);
        lname = findViewById(R.id.txt_lname);
        password = findViewById(R.id.txt_pwd);
        cpassword=findViewById(R.id.txt_cpwd);
        email = findViewById(R.id.txt_email);
        mobile = findViewById(R.id.txt_mobile);
        btn_save = findViewById(R.id.btn_sub);
        imageView=findViewById(R.id.imageView);
        btn_image=findViewById(R.id.imagebuton);
        btn_signin = findViewById(R.id.btn_signin);
        progressDialog = new ProgressDialog(this);
FirebaseApp.initializeApp(this);
        adddataRef = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
//
//        if(!firebaseAuth.getCurrentUser().isEmailVerified())
//        {
//
//        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this,SignIn.class);
                startActivity(intent);

            }
        });



    }
    private void Register() {
        String nametxt = name.getText().toString();
        String agetxt = age.getText().toString();
        String addresstxt = address.getText().toString();
        String nictxt = nic.getText().toString();
        String lnametxt = lname.getText().toString();
        String passwordtxt = password.getText().toString();
        String cpasswordtxt = cpassword.getText().toString();
        String emailtxt = email.getText().toString();
        String mobiletxt = mobile.getText().toString();

          if (TextUtils.isEmpty(nametxt)) {
            name.setError("First name cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(agetxt)) {
            age.setError("Age cannot be empty");
            return;
        }
          else if (TextUtils.isEmpty(addresstxt)) {
              address.setError("Home Address cannot be empty");
              return;
          }
          else if (TextUtils.isEmpty(nictxt)) {
              nic.setError("NIC cannot be empty");
              return;
          }
          else if (TextUtils.isEmpty(lnametxt)) {
              lname.setError("Last name cannot be empty");
              return;
          }
          else if (TextUtils.isEmpty(mobiletxt)) {
              mobile.setError("Mobile number cannot be empty");
              return;
          }
        else if (TextUtils.isEmpty(emailtxt)) {
            email.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(passwordtxt)) {
            password.setError("Enter your password");
            return;
        }
          else if (passwordtxt.length()<6) {
              password.setError("Password should be more than 6 characters");
              return;
          }
        else if (!passwordtxt.equals(cpasswordtxt)) {
            cpassword.setError("Different password");
            return;
        }  else if (!isValidEmail(emailtxt)) {
            email.setError("Invalid email");
            return;
        }


           progressDialog.setMessage("Please Wait");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
           firebaseAuth.createUserWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(this,new  OnCompleteListener<AuthResult>()
           {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful())
                   {
                       firebaseAuth.getCurrentUser().sendEmailVerification()
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   UserModel userModel = new UserModel(nametxt, agetxt, addresstxt, nictxt, lnametxt, passwordtxt, emailtxt, mobiletxt);
                                   adddataRef.push().setValue(userModel);


                                   Toast.makeText(SignUp.this, "Your account is already created.Please check your mail to verify your account", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(SignUp.this,SignIn.class);
                                   startActivity(intent);
                                   finish();
                               }
                               else
                               {
                                   Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

//                       UserModel userModel=new UserModel(nametxt,agetxt,addresstxt,nictxt,lnametxt,passwordtxt,emailtxt,mobiletxt);
//                       adddataRef.push().setValue(userModel);
//
//
//                       Toast.makeText(SignUp.this,"Successfully Register",Toast.LENGTH_SHORT).show();
//                       Intent intent=new Intent(SignUp.this,SignIn.class);
//                       startActivity(intent);
//                       finish();
                   }
                   else
                   {
                       Toast.makeText(SignUp.this,"Error Register",Toast.LENGTH_SHORT).show();
                   }
                   progressDialog.dismiss();
               }
           });
       }
       private Boolean isValidEmail(CharSequence target)
       {
           return  (!TextUtils.isEmpty(target)&& Patterns.EMAIL_ADDRESS.matcher(target).matches());
       }





}