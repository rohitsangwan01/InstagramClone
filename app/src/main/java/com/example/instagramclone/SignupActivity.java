package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import okhttp3.internal.ws.RealWebSocket;

public class SignupActivity extends AppCompatActivity {
    EditText edtSignupEmail,edtSignupUser,edtSignupPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}
        setContentView(R.layout.activity_signup);
        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupUser =findViewById(R.id.edtSignupUser);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        btnSignup = findViewById(R.id.btnSignup);
    }
    public void btnSignupClicked(View v){
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(edtSignupUser.getText().toString());
        parseUser.setEmail(edtSignupEmail.getText().toString());
        parseUser.setPassword(edtSignupPassword.getText().toString());
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in..");
        progressDialog.show();


        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(edtSignupEmail.getText().toString().equals("") || edtSignupUser.getText().toString().equals("") || edtSignupPassword.getText().toString().equals("")){
                        Toast.makeText(SignupActivity.this, "Email/Username/Password Should not be Empty", Toast.LENGTH_SHORT).show();
                }else if(e == null){
                        //Toast.makeText(SignupActivity.this, "SignUp Succesfully", Toast.LENGTH_SHORT).show();
                        alertdg("Signup Succesfuly","Please log In to Continue","Ok");
                } else{
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        progressDialog.dismiss();
    }
    public void alertdg(String title,String message,String btnTxt){
        AlertDialog.Builder alertb = new AlertDialog.Builder(this);
        alertb.setTitle(title);
        alertb.setMessage(message);
        alertb.setPositiveButton(btnTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        alertb.create().show();
    }
}