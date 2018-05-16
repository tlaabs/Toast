package com.jihu.toast.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jihu.toast.dbmodel.Bucket;
import com.jihu.toast.dbmodel.Item;
import com.jihu.toast.dbmodel.iCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by JiHu on 2018-05-15.
 * create & manage database
 *
 * toDO: 인풋값이 범위를 넘어서거나 자료형이 틀릴때?
 * toDo: db update 시?
 * toDo: backup?
 *

 */

public class DBManager extends SQLiteOpenHelper{
    //log, dbname, version, else category id
    private static final String LOG="DatabaseManager";
    private static final String DATABASE_NAME = "ItemDB";
    private static final int DATABASE_VERSION = 1;
    private static long ELSE_CATE;

    //table names
    public static final String TABLE_HISTORY = "history";
    public static final String TABLE_INPRO = "inprogress";
    public static final String TABLE_BUCKET="bucket";
    public static final String TABLE_CATE="category";
    public static final String TABLE_CATE_ITEM="cate_item";

    //columns common
    public static final String KEY_ID="id";

    //columns item table
    public static final String KEY_NAME = "name";
    public static final String KEY_REQUIERTIME="requierTime";
    public static final String KEY_REGISTERTIME="registerTime";
    public static final String KEY_STARTTIME="startTime";
    public static final String KEY_COMPLETETIME="completeTime";
    public static final String KEY_MEMORIES="memories";
    public static final String KEY_PICTURE="picture";

    //columns category table
    public static final String KEY_CATE_NAME="cateName";

    //columns cate_item table
    public static final String KEY_ITEM_ID="itemId";
    public static final String KEY_CATE_ID="cateId";

    //table create statemnet
    public static final String CREATE_TABLE_HISTORY=
            "create table "+TABLE_HISTORY+"("
                    +KEY_ID+"integer primary key, "
                    +KEY_NAME+" text not null, "
                    +KEY_REQUIERTIME+" datetime not null, "
                    +KEY_REGISTERTIME+" datetime not null, "
                    +KEY_STARTTIME+" datetime not null, "
                    +KEY_COMPLETETIME+" datetime not null, "
                    +KEY_MEMORIES+" text, "
                    +KEY_PICTURE+" text );";

    public static final String CREATE_TABLE_INPRO=
            "create table "+TABLE_INPRO+"("
                    +KEY_ID+"integer primary key, "
                    +KEY_NAME+" text not null, "
                    +KEY_REQUIERTIME+" datetime , "
                    +KEY_REGISTERTIME+" datetime not null, "
                    +KEY_STARTTIME+" datetime not null);";

    public static final String CREATE_TABLE_BUCKET=
            "create table "+TABLE_BUCKET+"("
                    +KEY_ID+"integer primary key, "
                    +KEY_NAME+" text not null, "
                    +KEY_REQUIERTIME+" datetime , "
                    +KEY_REGISTERTIME+" datetime not null);";

    public static final String CREATE_TABLE_CATE=
            "create table "+TABLE_CATE+"("
                    +KEY_ID+"integer primary key "
                    +KEY_CATE_NAME+"text not null);";

    public static final String CREATE_TABLE_CATE_ITEM=
            "create table "+TABLE_CATE_ITEM+"("
                    +KEY_ID+"integer primary key. "
                    +KEY_ITEM_ID+" integer not null, "
                    +KEY_CATE_ID+" integer not null);";

    //consturctor
    public DBManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_BUCKET);
        db.execSQL(CREATE_TABLE_INPRO);
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_CATE);
        db.execSQL(CREATE_TABLE_CATE_ITEM);

        ContentValues values = new ContentValues();
        values.put(KEY_CATE_NAME,"기타");
        ELSE_CATE=db.insert(TABLE_CATE,null,values);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BUCKET);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INPRO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATE_ITEM);

        onCreate(db);
    }

    /*
     * get all items under table
     */
    public List<Item> getAllItems(String tableName){
        List<Item> items= new ArrayList<Item>();

        String selectQuery = "select * from "+tableName;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                item.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                item.setRegisterTime(c.getString((c.getColumnIndex(KEY_REGISTERTIME))));
                item.setRequierTime(c.getString(c.getColumnIndex(KEY_REQUIERTIME)));

                // columns 이 존재하지 않을 경우 -1
                item.setStartTime(c.getString((c.getColumnIndex(KEY_STARTTIME))));
                item.setStartTime(c.getString((c.getColumnIndex(KEY_STARTTIME))));
                item.setCompleteTime(c.getString((c.getColumnIndex(KEY_COMPLETETIME))));
                item.setMemories(c.getString((c.getColumnIndex(KEY_MEMORIES))));
                item.setPicture(c.getString((c.getColumnIndex(KEY_PICTURE))));

                items.add(item);
            }while (c.moveToNext());
        }

        return items;
    }
    /*
     * get all items under table and category
     */
    public  List<Item> getAllItemsByCate(String tableName, String cateName){
        List<Item> items = new ArrayList<Item>();

        String selectQuery = "select * from "+tableName+" ti, "
                +TABLE_CATE+" tc, "+TABLE_CATE_ITEM+" tci where tc. "
                +KEY_CATE_NAME+" = '"+cateName+"'"+" and tc."+KEY_ID
                +" = " + "tci. "+ KEY_CATE_ID+ " and ti."+KEY_ID+ " = "
                +"tci. "+KEY_ITEM_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c =db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                item.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                item.setRegisterTime(c.getString((c.getColumnIndex(KEY_REGISTERTIME))));
                item.setRequierTime(c.getString(c.getColumnIndex(KEY_REQUIERTIME)));

                // columns 이 존재하지 않을 경우 -1
                item.setStartTime(c.getString((c.getColumnIndex(KEY_STARTTIME))));
                item.setStartTime(c.getString((c.getColumnIndex(KEY_STARTTIME))));
                item.setCompleteTime(c.getString((c.getColumnIndex(KEY_COMPLETETIME))));
                item.setMemories(c.getString((c.getColumnIndex(KEY_MEMORIES))));
                item.setPicture(c.getString((c.getColumnIndex(KEY_PICTURE))));

                items.add(item);
            }while (c.moveToNext());
        }

        return items;
    }
    /*
     * delete a item from table
     */

    public  void deleteItem(String tableName, long itemId){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(tableName,KEY_ID+" = ?",new String[]{String.valueOf(itemId)});
    }

    /*
     * create category
     */

    public long createCategory(iCategory cate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATE_NAME, cate.getCateName());

        long cateId= db.insert(TABLE_CATE,null,values);

        return cateId;
    }

    /*
     * get all category
     */
    public List<iCategory> getAllCategory(){
        List<iCategory> cates= new ArrayList<iCategory>();
        String selectQuery = "select * from "+TABLE_CATE;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c =db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                iCategory cate = new iCategory();
                cate.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                cate.setCateName(c.getString((c.getColumnIndex(KEY_CATE_NAME))));

                cates.add(cate);
            }while (c.moveToNext());
        }
        return cates;
    }


    /*
     * delete a category and under item goes into else category
     */
    public void deleteCate(iCategory cate) {
        SQLiteDatabase db = this.getWritableDatabase();

        //bucket
        List<Item> allCateItems = getAllItemsByCate(TABLE_BUCKET,cate.getCateName());

        // move all bucket
        for (Item item : allCateItems) {
            //
            // tododeleteToDo(todo.getId());
            }
        }

        // now delete the tag
        db.delete(TABLE_CATE, KEY_ID + " = ?",
                new String[] { String.valueOf(cate.getId()) });
    }
    /*
     * create a bucket
     */
    public long createBucket(Bucket bukcet, long[] cate_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME, bukcet.getName());
        values.put(KEY_REQUIERTIME, bukcet.getRequierTime());
        values.put(KEY_REGISTERTIME, getDateTime());

        //insert tuple
        long bucket_id = db.insert(TABLE_BUCKET,null,values);

        //assigning category to bucket
        for(long bucket_id : cate_id){
            createItemCate(bucket_id,cate_id);
        }
        return bucket_id;
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat= new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date= new Date();
        return dateFormat.format(date);
    }

}
