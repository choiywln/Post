package com.example.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.post.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonClose.setOnClickListener(v -> finish());

        Intent intent = getIntent();

        String contentSaved = intent.getStringExtra("content");
        binding.writeSaved.setText("" + contentSaved);

        String nameSaved = intent.getStringExtra("name");
        binding.nameSaved.setText("" + nameSaved);

//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        binding.pictureSaved.setImageBitmap(bitmap);

        Bitmap pictureSaved = (Bitmap) intent.getParcelableExtra("picture");
        binding.pictureSaved.setImageBitmap(pictureSaved);
        Log.i("picture", "Pic");

    }

//    private Bitmap loadBitmap(Uri uri) {
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(uri,
//                filePathColumn, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        String picturePath = cursor.getString(columnIndex);
//        cursor.close();
//
//        return BitmapFactory.decodeFile(picturePath);
//    }
}
