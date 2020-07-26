package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditProfileActivity extends AppCompatActivity {
    EditText edtUpdateName,edtUpdateBio,edtUpdateprofession;
    Button btnUpdateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edtUpdateName = findViewById(R.id.edtUpdtaeName);
        edtUpdateBio = findViewById(R.id.edtUpdateBio);
        edtUpdateprofession = findViewById(R.id.edtupdateprofession);





    }
    public void btnUploadInfo(View v){
        ParseUser parseUser= ParseUser.getCurrentUser();
        parseUser.put("profileName",edtUpdateName.getText().toString());
        parseUser.put("profileBio",edtUpdateBio.getText().toString());
        parseUser.put("profileProfession",edtUpdateprofession.getText().toString());
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(EditProfileActivity.this, "Updated..", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        finish();
        Intent intent = new Intent(EditProfileActivity.this,BioActivity.class);
        intent.putExtra("profileName",edtUpdateName.getText().toString());
        intent.putExtra("profileBio",edtUpdateBio.getText().toString());
        intent.putExtra("profileProfession",edtUpdateprofession.getText().toString());
        startActivity(intent);



    }
}