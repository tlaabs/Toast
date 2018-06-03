package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class AddBucketFromRecommendActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText titleEdit;
    Spinner hourSpinner;
    Spinner categorySpinner;

    Button addBtn;
    Button backBtn;

    String[] hours = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] category = {"여행", "식당", "영화", "활동", "기타"};

    String selectedHour = "-";
    String selectedCategory = "여행";

    BucketItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recommend_bucket);

        db =  new DBmanager(getApplicationContext()).getWDB();
        init();


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hours);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter1);
        hourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedHour = hours[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        hourSpinner.setSelection(getHoursIdxFromItem(item));

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = category[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categorySpinner.setSelection(getCategoryIdxFromItem(item));


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                recordValues.put(DBmanager.KEY_TITLE, titleEdit.getText().toString());
                recordValues.put(DBmanager.KEY_USAGE_TIME, selectedHour);
                recordValues.put(DBmanager.KEY_STATE,0);
                recordValues.put(DBmanager.KEY_CATEGORY,selectedCategory);

                String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Log.i("date","date : " + today);
                recordValues.put(DBmanager.KEY_REGISTER_TIME, today);

                db.insert(DBmanager.TABLE_ITEM, null, recordValues);
                Toast.makeText(getApplicationContext(),"등록 완료!",Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

//    public int getHoursIdxFromItem(BucketItem item){
//        String h = item.getUsageTime();
//
//        for(int i = 0 ; i < hours.length; i++)
//            if(h.equals(hours[i])) return i;
//
//        return -1;
//    }
    public int getCategoryIdxFromItem(BucketItem item){
        String cat = item.getCategory();
        for(int i = 0 ; i < category.length; i++)
            if(cat.equals(category[i])) return i;

        return -1;
    }


    public void init() {
        titleEdit = findViewById(R.id.titleEdit);
        hourSpinner = findViewById(R.id.hour_spinner);
        categorySpinner = findViewById(R.id.category_spinner);
        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);

        //getIntent

        Intent i = getIntent();
        item = (BucketItem)i.getSerializableExtra("item");

        titleEdit.setText(item.getTitle());
    }
}
