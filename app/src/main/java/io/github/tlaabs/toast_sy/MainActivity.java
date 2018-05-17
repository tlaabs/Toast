package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.goBucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.onGoingBucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OnGoingActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.goHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        DBThread th = new DBThread();
        th.start();

    }

    class DBThread extends Thread {
        @Override
        public void run() {
            SQLiteDatabase db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
//            db.execSQL("DROP TABLE simDB");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + "simDB" + "("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TITLE TEXT, "
                    + "CATEGORY TEXT, "
                    + "USAGE_TIME STRING, "
                    + "REG_TIME DATE, "
                    + "START_TIME DATE, "
                    + "STATE INTEGER, " //0 , 1 , 2
                    + "END_TIME DATE, "
                    + "COMPLETE_TIME DATE, " //8
                    + "IMG_SRC STRING, "
                    + "REVIEW STRING);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + "category" + "("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CATEGORY TEXT);");

//            db.execSQL("DROP TABLE category;");

            db.close();


        }
    }

}
