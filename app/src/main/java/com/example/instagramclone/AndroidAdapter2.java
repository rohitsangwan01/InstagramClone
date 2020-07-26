package com.example.instagramclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AndroidAdapter2 extends RecyclerView.Adapter<ViewHolder> {
    int a;
    public AndroidAdapter2(int a){
        this.a = a;
    }


    ArrayList<Integer> arrayBitmap = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.view_holder, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        arrayBitmap.add(position);
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
       // parseQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.orderByDescending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size() > 0 && e == null) {
                    ParseFile parseFile = (ParseFile) objects.get(position).get("picture");
                    holder.edtViewDes.setText(objects.get(position).get("createdAt")+"");
                    holder.edtViewUser.setText(objects.get(position).get("username")+"");
                    parseFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            holder.imgViewImage.setImageBitmap(bitmap);

                        }
                    });

                }
            }
        });



        // holder.imgViewImage.setImageBitmap(parseObjects.get(position));

    }

    @Override
    public int getItemCount() {
        return a;
    }
}

//    public void ab(){
//        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
//        parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
//        parseQuery.orderByDescending("createdAt");
//        parseQuery.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(objects.size()>0 && e==null){
//                    for(ParseObject user : objects){
//                        ParseFile parseFile = (ParseFile) user.get("picture");
//                        parseFile.getDataInBackground(new GetDataCallback() {
//                            @Override
//                            public void done(byte[] data, ParseException e) {
//                                if(data != null && e==null){
//                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
//                                   // arrayBitmap.add(bitmap);
//                                }else{
//                                    Log.i("tag",e.getMessage());
//                                }
//                            }
//                        });
//                        a = a+1;
//                    }
//                }
//            }
//        });
//    }
//
//}
