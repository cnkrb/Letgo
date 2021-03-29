package com.cenkkaraboa.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.myapplication.R;
import com.cenkkaraboa.myapplication.models.UsersModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.cenkkaraboa.myapplication.Utils.Utils.apiService;

public class AddAdActivity extends AppCompatActivity {


    Button images, send;
    Bitmap mBitmap, mBitmap2, mBitmap3, mBitmap4, mBitmap5, mBitmap6, mBitmap7, mBitmap8, mBitmap9, mBitmap10 = null;
    MultipartBody.Part pBitmap, pBitmap2, pBitmap3,
            pBitmap4, pBitmap5, pBitmap6, pBitmap7, pBitmap8, pBitmap9, pBitmap10 = null;
    Uri picUri;
    Boolean value = false;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    ProgressDialog progressDialog;
    private final static int ALL_PERMISSIONS_RESULT = 107;

    private static final int PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int PICK_IMAGE_MULTIPLE = 200;
    Button location;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    String loca = "ce";
    ImageView exit;
    TextView title, price, statement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        images = findViewById(R.id.images);
        send = findViewById(R.id.send);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        statement = findViewById(R.id.statement);
        location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });
        askPermissions();
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().length() == 0 || price.getText().length() == 0 || statement.getText().length() == 0 || loca.length() == 0 || !value) {
                    Toast.makeText(getApplicationContext(), "Gerekli yerleri doldurgunuzdan emin olun", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(AddAdActivity.this,
                            R.style.Progress);
                    progressDialog.setCancelable(false);
                    progressDialog = ProgressDialog.show(AddAdActivity.this, null, "Resim yükleniyor...", true);
                    Handler hndler = new Handler();

                    hndler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Süre sonunda burada yer alan kodlar çalışır.
                            multipartImageUpload();
                        }
                        // Kodların ne kadar süre sonra çalışacağını belirttik. Burada 1000 değeri ms (milisaniye)
                    }, 400);
                }


            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_TO_SELECT_IMAGE_FROM_GALLERY);
                } else {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(gallery, PICK_IMAGE_MULTIPLE);

                }
            }
        });

    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Bitmap decodeFile(String filePath) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        final int REQUIRED_SIZE = 1024;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap image = BitmapFactory.decodeFile(filePath, o2);

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = image.getWidth();
                int h = image.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false);

            }
        } catch (IOException e) {
            return null;
        }
        return image.copy(Bitmap.Config.ARGB_8888, true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String city = preferences.getString("city", "null");
        String town = preferences.getString("town", "null");
        String district = preferences.getString("district", "null");
        if (!city.equals("null")) {
            if (!town.equals("null")) {
                if (!district.equals("null")) {
                    loca = city + " " + town + " " + district;
                    location.setText(loca);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
        /*    CarmenFeature selectedCarmenFeature=PlaceAutocomplete.getPlace(data);
            loca=(selectedCarmenFeature.placeName());
            location.setText(loca);*/
        }
        try {
            // When an Image is picked

            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                if (data.getData() != null) {
                    System.out.println("burada");
                    Uri uri = data.getData();
                    String path = getRealPathFromURI(getApplicationContext(), uri);
                    mBitmap = BitmapFactory.decodeFile(path);
                }
                else {


                    //on multiple image selected
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        System.out.println(mClipData.getItemCount());
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String path = getRealPathFromURI(getApplicationContext(), uri);
                            images.setText(mClipData.getItemCount() + " Adet resim seçildi");
                            value = true;
                            if (i == 0) {
                                System.out.println(i + "sayrrro");
                                mBitmap = decodeFile(path);
                                //  mBitmap = BitmapFactory.decodeFile(path);
                            }
                            if (i == 1) {
                                System.out.println(i + "sassyo");
                                mBitmap2 = decodeFile(path);
                            }
                            if (i == 2) {
                                mBitmap3 = decodeFile(path);
                            }
                            if (i == 3) {
                                mBitmap4 = decodeFile(path);
                            }
                            if (i == 4) {
                                mBitmap5 = decodeFile(path);
                            }
                            if (i == 5) {
                                mBitmap6 = decodeFile(path);
                            }
                            if (i == 6) {
                                mBitmap7 = decodeFile(path);
                            }
                            if (i == 7) {
                                mBitmap8 = decodeFile(path);
                            }
                            if (i == 8) {
                                mBitmap9 = decodeFile(path);
                            }
                            if (i == 9) {
                                mBitmap10 = decodeFile(path);
                            }
                        }


                        //   System.out.println(path+"dada");
                        // item = mClipData.getItemAt(1);
                        // uri = item.getUri();
                        // String path2= getRealPathFromURI(getApplicationContext(), uri);
                        //    System.out.println(path2);

                        //     mBitmap2 = BitmapFactory.decodeFile(path2);
                        System.out.println("takta");
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI((data.getData()));

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }


    public static Bitmap reduceBitmapSize(Bitmap bitmap, int MAX_SIZE) {
        double ratioSquare;
        int bitmapHeight, bitmapWidth;
        bitmapHeight = bitmap.getHeight();
        bitmapWidth = bitmap.getWidth();
        ratioSquare = (bitmapHeight * bitmapWidth) / MAX_SIZE;
        if (ratioSquare <= 1)
            return bitmap;
        double ratio = Math.sqrt(ratioSquare);
        Log.d("mylog", "Ratio: " + ratio);
        int requiredHeight = (int) Math.round(bitmapHeight / ratio);
        int requiredWidth = (int) Math.round(bitmapWidth / ratio);
        return Bitmap.createScaledBitmap(bitmap, requiredWidth, requiredHeight, true);
    }


    public MultipartBody.Part dada(Bitmap bitmap, int i) {
        MultipartBody.Part body = null;

        if (bitmap != null) {
            try {
                File filesDir = getApplicationContext().getFilesDir();
                File file = null;
                if (i == 0) {
                    file = new File(filesDir, "image" + ".png");
                }
                if (i == 1) {
                    file = new File(filesDir, "image2" + ".png");
                }
                if (i == 2) {
                    file = new File(filesDir, "imagee3" + ".png");
                }
                if (i == 3) {
                    file = new File(filesDir, "imagee4" + ".png");
                }
                if (i == 4) {
                    file = new File(filesDir, "imagee5" + ".png");
                }
                if (i == 5) {
                    file = new File(filesDir, "imagee6" + ".png");
                }
                if (i == 6) {
                    file = new File(filesDir, "imagee7" + ".png");
                }
                if (i == 7) {
                    file = new File(filesDir, "imagee8" + ".png");
                }
                if (i == 8) {
                    file = new File(filesDir, "imagee9" + ".png");
                }
                if (i == 9) {
                    file = new File(filesDir, "imagee10" + ".png");
                }
                //    bitmap = reduceBitmapSize(bitmap, 150000);

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                if (i == 0) {
                    body = MultipartBody.Part.createFormData("patch", file.getName(), reqFile);
                    System.out.println(file.getName());
                }
                if (i == 1) {
                    body = MultipartBody.Part.createFormData("patch_two", file.getName(), reqFile);
                    System.out.println(file.getName());
                }
                if (i == 2) {
                    body = MultipartBody.Part.createFormData("patch_three", file.getName(), reqFile);
                }
                if (i == 3) {
                    body = MultipartBody.Part.createFormData("patch_four", file.getName(), reqFile);
                }
                if (i == 4) {
                    body = MultipartBody.Part.createFormData("patch_five", file.getName(), reqFile);
                }
                if (i == 5) {
                    body = MultipartBody.Part.createFormData("patch_six", file.getName(), reqFile);
                }
                if (i == 6) {
                    body = MultipartBody.Part.createFormData("patch_seven", file.getName(), reqFile);
                }
                if (i == 7) {
                    body = MultipartBody.Part.createFormData("patch_eight", file.getName(), reqFile);
                }
                if (i == 8) {
                    body = MultipartBody.Part.createFormData("patch_nine", file.getName(), reqFile);
                }
                if (i == 9) {
                    body = MultipartBody.Part.createFormData("patch_ten", file.getName(), reqFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    private void multipartImageUpload() {
        pBitmap = dada(mBitmap, 0);
        pBitmap2 = dada(mBitmap2, 1);
        pBitmap3 = dada(mBitmap3, 2);
        pBitmap4 = dada(mBitmap4, 3);
        pBitmap5 = dada(mBitmap5, 4);
        pBitmap6 = dada(mBitmap6, 5);
        pBitmap7 = dada(mBitmap7, 6);
        pBitmap8 = dada(mBitmap8, 7);
        pBitmap9 = dada(mBitmap9, 8);
        pBitmap10 = dada(mBitmap10, 9);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String product = preferences.getString("product", "null");
        Intent getResults = getIntent();
        String category = getResults.getStringExtra("category");
        System.out.println(category);

        Callback<UsersModel> listCallBack = new Callback<UsersModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Yüklendi", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Hata oluştu.Daha sonra tekrar deneyiniz", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Bir hata oluştu", Toast.LENGTH_SHORT).show();
            }
        };
        apiService.addProduct(pBitmap, pBitmap2, pBitmap3, pBitmap4, pBitmap5, pBitmap6, pBitmap7, pBitmap8, pBitmap9, pBitmap10, product
                , title.getText().toString(), price.getText().toString(), statement.getText().toString(), loca, category).enqueue(listCallBack);

        //  pBitmap=dada(mBitmap,0);
        //                        pBitmap2=dada(mBitmap2,1);
        //                        pBitmap3=dada(mBitmap3,2);
        //                        pBitmap4 =dada(mBitmap4,3);
        //                        pBitmap5=dada(mBitmap5,4);
        //                        pBitmap6 =     dada(mBitmap6,5);
        //                        pBitmap7 =      dada(mBitmap7,6);
        //                        pBitmap8 =      dada(mBitmap8,7);
        //                        pBitmap9 =      dada(mBitmap9,8);
        //                        pBitmap10

    }

    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Tamam", okListener)
                .setNegativeButton("İptal", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("Gerekli izinleri vermezseniz uygulamanın bazı özellikleri çalışmayacaktır.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }
}
