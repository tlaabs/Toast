package io.github.tlaabs.toast_sy.Alarm;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.github.tlaabs.toast_sy.BucketItem;
import io.github.tlaabs.toast_sy.ExtendBucketActivity;
import io.github.tlaabs.toast_sy.R;
import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class StopOnGoingActivity extends AppCompatActivity {
    Button cancel;
    Button keep;
    BucketItem item;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_on_going);

        cancel=(Button)findViewById(R.id.cancel);
        keep=(Button)findViewById(R.id.keepgoing);
        message=(TextView)findViewById(R.id.message);

        Intent i = getIntent();
        item=(BucketItem)i.getSerializableExtra("item");
        message.setText(item.getTitle().toString() + " 정말로 그만 할래요?");

        //취소
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(item.getId());

        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ExtendBucketActivity.class);
                i.putExtra("item",item);
                startActivity(i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getApplicationContext().openOrCreateDatabase(DBmanager.TABLE_ITEM, MODE_PRIVATE, null);
                ContentValues recordValues = new ContentValues();

                recordValues.put("STATE", 0);

                db.update(DBmanager.TABLE_ITEM,recordValues,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"중지",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
