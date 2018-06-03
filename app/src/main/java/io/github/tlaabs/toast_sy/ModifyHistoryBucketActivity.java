package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class ModifyHistoryBucketActivity extends AppCompatActivity {
    final static int PICK_FROM_ALBUM = 0;

    SQLiteDatabase db;
    TextView titleText;
    ImageView imagePickBtn;
    ImageView showImg;
    EditText editBox;

    TextView deleteBtn;
    //    TextView backBtn;
    TextView shareBtn;
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



//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kakaoShare();
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
//        backBtn = findViewById(R.id.backBtn);
        shareBtn = findViewById(R.id.shareBtn);
        modifyBtn = findViewById(R.id.modifyBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        //getIntent

        Intent i = getIntent();
        item = (BucketItem) i.getSerializableExtra("item");

        titleText.setText(item.getTitle());
        mImageCaptureUri = Uri.parse(item.getImgSrc());
        editBox.setText(item.getReview());

        Glide.with(this)
                .load(mImageCaptureUri)
                .into(showImg);
        showImg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);

        Glide.with(this)
                .load(R.drawable.addd_img)
                .into(imagePickBtn);
        imagePickBtn.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.MULTIPLY);

    }

    private void kakaoShare() {
        String ImageUploadURL = getString(R.string.server_kakao);
        String imagePath = item.getImgSrc();
        ImageUploadTask imageUploadTask = new ImageUploadTask();
        imageUploadTask.execute(ImageUploadURL, changeUri(imagePath));
    }

    public String changeUri(String uri) {

        Cursor c =

                getContentResolver().query(Uri.parse(uri), null, null, null, null);

        c.moveToNext();

        String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));

        return path;


    }

    class ImageUploadTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String jsonObject = JSONParser.uploadImage(params[0], params[1]);
            if (jsonObject != null)
                return jsonObject;


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("ppz",result);
            Log.i("share","in");
            String templateId = getResources().getString(R.string.kakao_template);

            Map<String, String> templateArgs = new HashMap<String, String>();
        templateArgs.put("${event_img}", result);
            templateArgs.put("${title}", item.getTitle());
//        templateArgs.put("${link}", "링크 : " + "adf");

            KakaoLinkService.getInstance().sendScrap(getApplicationContext(), "https://developers.kakao.com", templateId, templateArgs, new ResponseCallback<KakaoLinkResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("error",errorResult.toString());
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                }
            });
        }

    }


}
