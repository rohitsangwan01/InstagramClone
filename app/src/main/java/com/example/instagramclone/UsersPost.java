package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

public class UsersPost extends AppCompatActivity {
    ImageView imgUser;
    TextView desuser;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

//        imgUser = findViewById(R.id.imgUser);
//        desuser = findViewById(R.id.desUser);
        linearLayout = findViewById(R.id.linearLayout);


        Intent intent = getIntent();
        final String recievedUserName = intent.getStringExtra("username");
        setTitle(recievedUserName);
       // Toast.makeText(this, recievedUserName+" Welcome", Toast.LENGTH_SHORT).show();

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",recievedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(UsersPost.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e==null){
                    for(ParseObject user:objects){

                        final TextView imageDes = new TextView(UsersPost.this);
                        imageDes.setText(user.get("image_des")+"");

                        ParseFile parseFile =(ParseFile) user.get("picture");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPost.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);


                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    imageDes.setLayoutParams(des_params);
                                    imageDes.setGravity(Gravity.CENTER);
                                    imageDes.setTextSize(30f);
                                    imageDes.setTextColor(Color.GREEN);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(imageDes);
                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(UsersPost.this, recievedUserName+" dosen't Have Any Post", Toast.LENGTH_SHORT).show();
                    finish();
                }
                progressDialog.dismiss();

            }
        });


    }
}