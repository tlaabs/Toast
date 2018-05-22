package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    boolean securityCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_bucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OnGoingActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_recommend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RecommendActivity.class);
                startActivity(i);
            }
        });

        DBThread th = new DBThread();
        th.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("toastPWD", MODE_PRIVATE);
        int set = pref.getInt("enable", -1);
        String pwd = pref.getString("pwd","0000");

        if(securityCheck == false && set == 1){
            Intent i = new Intent(this,CheckPWDActivity.class);
            i.putExtra("pwd",pwd);
            startActivityForResult(i,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                securityCheck = true;
            }else{
                finish();
            }
        }
    }

    /**
     *  main_menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_alarm:
                return true;

            case R.id.menu_setting:
                Intent menu_intent2 = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(menu_intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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