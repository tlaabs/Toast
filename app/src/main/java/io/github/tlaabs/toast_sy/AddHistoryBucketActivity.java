package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHistoryBucketActivity extends AppCompatActivity {
    final static int PICK_FROM_ALBUM = 0;

    SQLiteDatabase db;
    TextView titleText;
    Button imagePickBtn;
    ImageView showImg;
    EditText editBox;

    Button submitBtn;
    Button backBtn;


    BucketItem item;

    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);

        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        init();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                recordValues.put("STATE",2);
                recordValues.put("COMPLETE_TIME",now);
                recordValues.put("IMG_SRC",mImageCaptureUri.toString());
                recordValues.put("REVIEW", editBox.getText().toString());

                db.update("simDB",recordValues,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"후기 완료!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imagePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(i, PICK_FROM_ALBUM);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FROM_ALBUM){
            if(resultCode == RESULT_OK){
                mImageCaptureUri = data.getData();
                Log.d("mmo",mImageCaptureUri.getPath().toString());
                Glide.with(this)
                        .load(mImageCaptureUri)
                        .into(showImg);
            }
        }
    }

    public void init() {
        titleText = findViewById(R.id.title);
        imagePickBtn = findViewById(R.id.imagePickBtn);
        showImg = findViewById(R.id.showImg);
        editBox = findViewById(R.id.editBox);
        backBtn = findViewById(R.id.backBtn);
        submitBtn = findViewById(R.id.submitBtn);

        //getIntent

        Intent i = getIntent();
        item = (BucketItem)i.getSerializableExtra("item");

        titleText.setText(item.getTitle());
    }
}
