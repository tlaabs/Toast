package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
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

public class AddBucketActivity extends AppCompatActivity {
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

//    ArrayList categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket);

        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        init();
//        getCategoryList();


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


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                recordValues.put("TITLE", titleEdit.getText().toString());
                recordValues.put("USAGE_TIME", selectedHour);
                recordValues.put("STATE",0);
                recordValues.put("CATEGORY",selectedCategory);

                String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Log.i("date","date : " + today);
                recordValues.put("REG_TIME", today);

                db.insert("simDB", null, recordValues);
                Toast.makeText(getApplicationContext(),"등록 완료!",Toast.LENGTH_SHORT).show();
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

//    public void getCategoryList() {
//        String sql = "SELECT * FROM CATEGORY;";
//        Cursor cursor = db.rawQuery(sql, null);
//        int count = cursor.getCount();
//
//        if (cursor != null && count != 0) {
//            cursor.moveToFirst();
//            for (int i = 0; i < cursor.getCount(); i++) {
//                String category = cursor.getString(1);
//                categoryList.add(category);
//
//                cursor.moveToNext();
//            }
//        }

//        Log.i("lolo", "count" + count);
//    }

    public void init() {
        titleEdit = findViewById(R.id.titleEdit);
        hourSpinner = findViewById(R.id.hour_spinner);
        categorySpinner = findViewById(R.id.category_spinner);
        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);
    }
}
