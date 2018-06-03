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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class ModifyHistoryBucketActivity extends AppCompatActivity {
    final static int PICK_FROM_ALBUM = 0;

    SQLiteDatabase db;
    TextView titleText;
    ImageView imagePickBtn;
    ImageView showImg;
    EditText editBox;

    TextView deleteBtn;
    Button backBtn;
    TextView modifyBtn;


    BucketItem item;

    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_history);
        //getSupportActionBar().setTitle("추억 수정"); //todo Null 포인터 에러?

        db = new DBmanager(getApplicationContext()).getWDB();
        init();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

//                recordValues.put("STATE",2);
                recordValues.put("COMPLETE_TIME",now);
                recordValues.put("IMG_SRC",mImageCaptureUri.toString());
                recordValues.put("REVIEW", editBox.getText().toString());

                db.update(DBmanager.TABLE_ITEM,recordValues,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"수정 완료!",Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete(DBmanager.TABLE_ITEM,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"삭제 완료!",Toast.LENGTH_SHORT).show();
                db.close();
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
        modifyBtn = findViewById(R.id.modifyBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        //getIntent

        Intent i = getIntent();
        item = (BucketItem)i.getSerializableExtra("item");

        titleText.setText(item.getTitle());
        mImageCaptureUri = Uri.parse(item.getImgSrc());
        editBox.setText(item.getReview());
        Glide.with(this)
                .load(mImageCaptureUri)
                .into(showImg);

    }
}
