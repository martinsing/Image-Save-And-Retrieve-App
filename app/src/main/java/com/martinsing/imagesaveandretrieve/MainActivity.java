package com.martinsing.imagesaveandretrieve;

import android.content.Context;
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

import java.io.IOException;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main Activity";
    Bitmap btmap;
    public static final String MYPROFILE = "profile";//file name
    SharedPreferences sharedpreferences;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String picturePath = preferences.getString(MYPROFILE, null);
        if (MYPROFILE != null) {
            ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 3;

//            Bitmap bm = BitmapFactory.decodeStream(
//                    getContentResolver().openInputStream(picturePath));
//            imageView.setImageBitmap(bm);

//  getContentResolver().openInputStream(uriFromPath))
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

      /*  ImageButton imgbtn=(ImageButton) findViewById(R.id.lbl_user_photo));
        imgbtn.setOnClickListener(this);*/
        iv = (ImageView) findViewById(R.id.lbl_user_photo);
    }

    public void gotoPhotos(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*Original
        Returns the Profile picture selected into the tab
        */
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        4
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            String[] proj = {MediaStore.Images.Media.DATA};
//
//            //This method was deprecated in API level 11
//            //Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//
//            CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
//            Cursor cursor = cursorLoader.loadInBackground();
//
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            String picturePath = cursor.getString(column_index);
//            cursor.close();
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor edit = preferences.edit();
//            edit.putString(MYPROFILE, picturePath);
//            edit.commit();
//
//        }

//        3
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                Log.d(TAG, valueOf(bitmap));
//                ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);
//                imageView.setImageBitmap(bitmap);
//
//                String path = (valueOf(bitmap));
//
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putString(MYPROFILE, path);
//                edit.commit();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        2
//        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
//            Uri orgURI = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            String convertedPath = getRealPathFromURI(orgUri);
//            Uri uriFromPath = Uri.fromFile(new File(convertedPath));
//
//            Cursor cursor = getContentResolver().query(orgURI,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//            SharedPreferences preferences = getDefaultSharedPreferences(this);
//            SharedPreferences.Editor edit = preferences.edit();
//            edit.putString(MYPROFILE, picturePath);
//            edit.commit();
//        }

//            1
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                Log.d(TAG, String.valueOf(bitmap));
//                FileOutputStream fos = this.openFileOutput("desiredFilename.png", Context.MODE_PRIVATE);
//
//                SharedPreferences preferences = getDefaultSharedPreferences(this);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(String.valueOf(bitmap), null);
//                Log.i(TAG, String.valueOf(bitmap));
//                editor.commit();
//
//                ImageView imageView = (ImageView) findViewById(R.id.lbl_user_photo);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }

    private void clearData(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "Cleared Default Shared Preferences", Toast.LENGTH_SHORT);
    }
}