package com.martinsing.imagesaveandretrieve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main Activity";
    public static final String MYPROFILE = "profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String picturePath = preferences.getString(MYPROFILE, null);
        if (MYPROFILE != null) {
            Log.i(TAG, "MYPROFILE != null");
            Glide.with(this)
                    .load(picturePath)
                    .placeholder(R.drawable.ic_account_box_black_24dp)
//                    .error(R.drawable.ic_error_outline_black_24dp)
                    .into(imageView);
        } else {
            Log.i(TAG, "MYPROFILE == null");
        }
    }

    public void gotoPhotos(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        /*Original
//        Returns the Picture into ImageView
//        */
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                Log.d(TAG, String.valueOf(bitmap));
//                ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);
//                imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        /* Returns the Picture into ImageView and saves picture path into DefaultSharedPreferences
        */
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Log.d(TAG, String.valueOf(bitmap));
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove(MYPROFILE);
                edit.commit();
                Log.i(TAG, "removed MYPROFILE");

                edit.putString(MYPROFILE, String.valueOf(uri));
                edit.commit();
                Log.i(TAG, "added path " + String.valueOf(uri) );

                ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);

//                Glide.with(this)
//                        .load(bitmap)
//                        .into(imageView);
                imageView.setImageBitmap(bitmap);
                Log.i(TAG, "set Image bitmap");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
Toast Notification telling user that the button is not yet set.
*/
    public void clearData(View v) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "Cache Cleared, Maybe", Toast.LENGTH_SHORT).show();
    }
}
