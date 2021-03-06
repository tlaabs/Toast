package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    ImageView imagePickBtn;
    ImageView showImg;
    EditText editBox;

    TextView submitBtn;
    TextView backBtn;


    BucketItem item;

    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);
//        getSupportActionBar().setTitle("추억 남기기");

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

                recordValues.put("STATE", 2);
                recordValues.put("COMPLETE_TIME", now);
                //toDO : 츄라이 츄라이~~
                if (mImageCaptureUri == null) {
                    Toast.makeText(getApplicationContext(), "포스팅 정보가 부족해요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String uriStr = mImageCaptureUri.toString();
                String editStr = editBox.getText().toString();
                recordValues.put("IMG_SRC", uriStr);
                recordValues.put("REVIEW", editStr);


                db.update("simDB", recordValues, "ID=" + item.getId(), null);
                Toast.makeText(getApplicationContext(), "후기 완료!", Toast.LENGTH_SHORT).show();
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
        if (requestCode == PICK_FROM_ALBUM) {
            if (resultCode == RESULT_OK) {
                mImageCaptureUri = data.getData();
                Log.d("mmo", mImageCaptureUri.getPath().toString());
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
        item = (BucketItem) i.getSerializableExtra("item");

        titleText.setText(item.getTitle());
        showImg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);

        Glide.with(this)
                .load(R.drawable.addd_img)
                .into(imagePickBtn);
        imagePickBtn.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.MULTIPLY);

    }
}
