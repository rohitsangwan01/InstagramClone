package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class BioActivity extends AppCompatActivity {
    EditText edtProfileName,edtProfileBio,edtProfileProfession,
            edtProfileHobbies,edtProfileFavSport;
    TextView posrNo;
    ImageView btnBioHome;
    TextView edtBioUsername;
    Button bioLogout,btnEdtprofile;
    TextView edtUnderpicUsername,edtUnderpicBio,edtUnderpicProfession;
    RecyclerView recyclerView;
    int ob;
    Boolean rv = true;
    ImageView addimgbtn,userDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        edtBioUsername = findViewById(R.id.edtBioUsername);
        userDp = findViewById(R.id.dpUpload);
        posrNo = findViewById(R.id.posrNo);
        addimgbtn = findViewById(R.id.addImgBtn);
        bioLogout = findViewById(R.id.bioLogout);
        btnBioHome = findViewById(R.id.btnBioHome);
        edtBioUsername.setText(ParseUser.getCurrentUser().getUsername());
        edtUnderpicUsername = findViewById(R.id.edtUnderpicUsername);
        edtUnderpicProfession = findViewById(R.id.edtUnderpicProfession);
        edtUnderpicBio = findViewById(R.id.edtUnderpicBIo);
        btnEdtprofile = findViewById(R.id.btnEdtprofile);
        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        String name = intent.getStringExtra("profileName");
        String bio = intent.getStringExtra("profileBio");
        String profession = intent.getStringExtra("profileProfession");
        edtUnderpicUsername.setText(ParseUser.getCurrentUser().get("profileName")+"");
        edtUnderpicBio.setText(ParseUser.getCurrentUser().get("profileBio")+"");
        edtUnderpicProfession.setText(ParseUser.getCurrentUser().get("profileProfession")+"");


        ParseQuery<ParseObject> parseQuery = new ParseQuery("Photo");
        parseQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects != null && e == null){
                    ob = objects.size();
                    AndroidAdapter androidAdapter = new AndroidAdapter(ob);
                    recyclerView.setAdapter(androidAdapter);
                    posrNo.setText(ob+"");
                }
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        bioLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.getCurrentUser().logOut();
                finish();
                Intent intent = new Intent(BioActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnEdtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BioActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });
        addimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(BioActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 7000);
                } else {
                    getChosenImage();
                }
            }
        });

    }
    public void upldPic(View v){
        if(rv){
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            rv = false;
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
            rv = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 7000){
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }
    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,8000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8000){
            if(resultCode == Activity.RESULT_OK){

                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int ColumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(ColumnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("img.png",bytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture",parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog dialog = new ProgressDialog(BioActivity.this);
                    dialog.setMessage("Loading...");
                    dialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText(BioActivity.this, "Done..", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(BioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(BioActivity.this, "Catch Called", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


}
