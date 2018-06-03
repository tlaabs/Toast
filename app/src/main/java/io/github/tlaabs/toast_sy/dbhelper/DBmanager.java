package io.github.tlaabs.toast_sy.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.tlaabs.toast_sy.BucketItem;

public class DBmanager extends SQLiteOpenHelper {
    //log, dbname, version
    private static final String LOG="DatabaseManager";
    public static final String DATABASE_NAME = "ItemDB";
    private static final int DATABASE_VERSION = 1;


    //table names
    public static final String TABLE_ITEM = "Items";
    public static final String TABLE_TODAY = "today";

    //columns table
    public static final String KEY_ID="ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_CATEGORY="CATEGORY";
    public static final String KEY_USAGE_TIME="USAGE_TIME";
    public static final String KEY_REGISTER_TIME="REG_TIME";
    public static final String KEY_START_TIME="START_TIME";
    public static final String KEY_STATE="STATE";
    public static final String KEY_END_TIME="END_TIME";
    public static final String KEY_COMPLETE_TIME="COMPLETE_TIME";
    public static final String KEY_IMG_SOURCE="IMG_SRC";
    public static final String KEY_REVIEW="REVIEW";


    //table create statemnet
    public static final String CREATE_TABLE_ITEM=
            "CREATE TABLE "+TABLE_ITEM+"("
                    +KEY_ID+" INTEGER primary key, "
                    +KEY_TITLE+" TEXT not null, "
                    +KEY_CATEGORY+" TEXT, "
                    +KEY_USAGE_TIME+" STRING, "
                    +KEY_REGISTER_TIME+" DATE, "
                    +KEY_START_TIME+" DATE, "
                    +KEY_STATE+" INTEGER, "
                    +KEY_END_TIME+" DATE, "
                    +KEY_COMPLETE_TIME+" DATE, "
                    +KEY_IMG_SOURCE+" STRING, "
                    +KEY_REVIEW+" STRING)";

    public static final String CREATE_TABLE_TODAY=
            "create table "+TABLE_TODAY+" ( "
                    +KEY_ID+" INTEGER primary key, "
                    +KEY_TITLE+" TEXT, "
                    +KEY_IMG_SOURCE+ " STRING)";

    public DBmanager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_ITEM);
        //db.execSQL(CREATE_TABLE_TODAY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ITEM);
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODAY);
        onCreate(db);
    }

    public void addBucket(BucketItem item){ //addbucket , from recommend
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues recordValues = new ContentValues();

        recordValues.put(KEY_TITLE,item.getTitle());
        recordValues.put(KEY_USAGE_TIME, item.getUsageTime());
        recordValues.put(KEY_STATE,0);
        recordValues.put(KEY_CATEGORY,item.getCategory());

        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Log.i("date","date : " + today);
        recordValues.put("REG_TIME", today);

        db.insert(TABLE_ITEM, null, recordValues);
    }

    public void addHistory(BucketItem item){ //add history
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues recordValues = new ContentValues();

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        recordValues.put("STATE",2);
        recordValues.put("COMPLETE_TIME",now);
        //toDO : 츄라이 츄라이~~
        recordValues.put("IMG_SRC",item.getImgSrc());
        recordValues.put("REVIEW", item.getReview());

        db.update(TABLE_ITEM,recordValues,KEY_ID +"="+ item.getId(),null);
    }

    public void upDateState(BucketItem item, int state){ //bucketfragment
        SQLiteDatabase db = this.getWritableDatabase();
        int howlong =  Integer.parseInt(item.getUsageTime());
        ContentValues recordValues = new ContentValues();

        recordValues.put(KEY_STATE, state);
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        recordValues.put(KEY_START_TIME, now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());


        cal.add(Calendar.HOUR, +howlong); //toDo : 테스트 용으로 chfmf eksdnl
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        recordValues.put(KEY_END_TIME, end);

        db.update(TABLE_ITEM, recordValues, KEY_ID+"="+ item.getId(), null);
    }

    public void upDateToday(){
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SQLiteDatabase db = this.getWDB();
        ContentValues recordValues = new ContentValues();

        db.execSQL("delete from "+DBmanager.TABLE_TODAY);
        String selectQuery="SELECT * FROM "+DBmanager.TABLE_ITEM
                +" WHERE STATE = 2 AND SUBSTR("
                +DBmanager.KEY_COMPLETE_TIME+",6,5) = "+today.substring(5,10)+ " ORDER BY RANDOM() LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();

        while(cursor!=null){
            recordValues.put(DBmanager.KEY_ID,cursor.getColumnIndex(DBmanager.KEY_ID));
            recordValues.put(DBmanager.KEY_TITLE,cursor.getColumnIndex(DBmanager.KEY_TITLE));
            recordValues.put(DBmanager.KEY_IMG_SOURCE,cursor.getColumnIndex(DBmanager.KEY_IMG_SOURCE));

            db.insert(DBmanager.TABLE_TODAY,null,recordValues);
            cursor.moveToNext();
        }
        db.close();
    }

    public SQLiteDatabase getWDB(){
        return this.getWritableDatabase();
    }
    public SQLiteDatabase getRDB(){
        return this.getReadableDatabase();
    }
}
