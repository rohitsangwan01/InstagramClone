package com.example.instagramclone;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView edtViewUser;
    TextView edtViewDes;
    ImageView imgViewImage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        edtViewUser =itemView.findViewById(R.id.edtViewuser);
        edtViewDes = itemView.findViewById(R.id.edtViewDes);
        imgViewImage = itemView.findViewById(R.id.imgViewImage);

    }
}
