package mobiledev.unb.ca.lab3intents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

public class ExternalActivityCalls extends AppCompatActivity {

    public Button cameraButton;
    public Button emailButton;
    public Button backButton;

    File mCurrentPhoto;
    String mCurrentPhotoPath;

    //static final String TAG = "Lab 3 - ExternalActivityCalls";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private YearMonth Glide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.external_activity_calls);

        cameraButton = findViewById(R.id.cmButton);
        emailButton = findViewById(R.id.emButton);
        backButton = findViewById(R.id.bkButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            // Trigger the intent
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myemail = "pk167h@gmail.com";
                //String subject = "CS2063 Lab 3";
                //String body = "This is a test email!";
                Intent email_intent = new Intent(Intent.ACTION_SENDTO);
                email_intent.setData(Uri.parse("mailto: "));
                //email_intent.setType("*/*");
                //email_intent.putExtra(Intent.EXTRA_EMAIL, myemail);
                email_intent.putExtra(Intent.EXTRA_EMAIL, "zhuang5@unb.ca");
                //email_intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                email_intent.putExtra(Intent.EXTRA_SUBJECT, "CS2063 Lab 3");
                //email_intent.putExtra(Intent.EXTRA_TEXT, body);
                email_intent.putExtra(Intent.EXTRA_TEXT, "This is a test email!");
                if (email_intent.resolveActivity(getPackageManager()) != null){
                    //startActivity(Intent.createChooser(email_intent, "Chooser Title"));
                    startActivity(email_intent);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent back_intent = new Intent(ExternalActivityCalls.this, MainActivity.class);
                startActivity(back_intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("mylog", "Exception while creating file: " + ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("mylog", "Photofile not null");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "mobiledev.unb.ca.lab3intents.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhoto = image;
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.d("mylog", "Path: " + mCurrentPhotoPath);
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == ExternalActivityCalls.RESULT_OK) {
                Log.d("mylog", "Add to gallery");
                galleryAddPic();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
