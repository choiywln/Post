package com.example.post;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.post.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    Uri picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonPicture.setOnClickListener(v -> getPicture());
        binding.buttonSave.setOnClickListener(v -> postActivity());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();
                        Log.i("TEST", "data: " + data);

                        if (result.getResultCode() == RESULT_OK && null != data) {
                            Uri selectedImage = data.getData();
                            picture = selectedImage;
                            Bitmap bitmap = loadBitmap(selectedImage);
                            binding.image.setImageBitmap(bitmap);
//                            this.bitmap1 = bitmap;
                        }
                    });

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "권한이 설정되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "권한이 설정되지 않았습니다. 권한이 없으므로 앱을 종료합니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    private void postActivity() {
        Intent intent = new Intent(this, PostActivity.class);

        String content = binding.editTextContent.getText().toString();
        intent.putExtra("content", content);

        String name = binding.editTextName.getText().toString();
        intent.putExtra("name", name);

        intent.putExtra("picture", picture);
//        Uri bitmap = intent.getData();
//        Bitmap picture = loadBitmap(bitmap);
////        Bitmap picture = ((BitmapDrawable) binding.image.getDrawable()).getBitmap();
//        binding.image.setImageBitmap(picture);
//        intent.putExtra("picture", bitmap);
//        Log.i("pic", "passed");

//        Bitmap picture = ((BitmapDrawable) binding.image.getDrawable()).getBitmap();
//        float scale = (float)(1024/(float)picture.getWidth());
//        int image_w = (int)(picture.getWidth() * scale);
//        int image_h = (int)(picture.getHeight() * scale);
//        Bitmap resize = Bitmap.createScaledBitmap(picture, image_w, image_h, true);
//        intent.putExtra("picture", resize);


//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        Bitmap bitmap = ((BitmapDrawable)binding.image.getDrawable()).getBitmap();
//        float scale = (float)(1024/(float)bitmap.getWidth());
//        int image_w = (int)(bitmap.getWidth() * scale);
//        int image_h = (int)(bitmap.getHeight() * scale);
//        Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
//        resize.compress(Bitmap.CompressFormat.JPEG,100,stream);
//        byte[] byteArray = stream.toByteArray();
//        intent.putExtra("image", byteArray);

        startActivity(intent);
    }

    private void getPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    private Bitmap loadBitmap(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(picturePath);
    }
}