package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    TextView signupPage;
    EditText edtLoginUser,edtLoginPAssword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        setContentView(R.layout.activity_login);
        signupPage = findViewById(R.id.signupPage);
        edtLoginUser = findViewById(R.id.edtLoginUser);
        edtLoginPAssword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        if(ParseUser.getCurrentUser() != null){
            Intent in =new Intent(LoginActivity.this,FrontActivity.class);
            startActivity(in);
            finish();
        }





    }
    public void signupPage(View v){
        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(intent);
    }
    public void helpSign(View v){
        Toast.makeText(this, "Sorry Bro I Can't Help", Toast.LENGTH_SHORT).show();
    }
    public void fbLogin(View v){
        Toast.makeText(this, "Dude Facebook is Waiste of Time, Login With Email", Toast.LENGTH_SHORT).show();
    }
    public void btnLoginClicked(View v){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loging in...");
        progressDialog.show();
      //  Toast.makeText(this, "Login Clicked", Toast.LENGTH_SHORT).show();
        ParseUser.logInInBackground(edtLoginUser.getText().toString(), edtLoginPAssword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null && e==null){
                    if(edtLoginUser.getText().toString().equals("") && edtLoginPAssword.getText().toString().equals("")){
                        Toast.makeText(LoginActivity.this, "Username/Password Should Not Be Empty", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,FrontActivity.class);
                        startActivity(intent);
                    }

                }else{
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
        progressDialog.dismiss();


    }

}