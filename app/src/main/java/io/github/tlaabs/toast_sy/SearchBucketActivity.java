package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class SearchBucketActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText titleEdit;
    Spinner hourSpinner;

    Button searchBtn;
    Button backBtn;

    String[] hours = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String selectedHour = "-";

    BucketItem item;
    ArrayList arrList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bucket);

        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
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



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql;
                String searchTitle = titleEdit.getText().toString();
                if(selectedHour.equals("-"))sql = "SELECT * FROM simDB WHERE TITLE LIKE '%" + searchTitle + "%';";
                else sql = "SELECT * FROM simDB WHERE STATE = 0 and USAGE_TIME = "
                        + selectedHour + " and " + "TITLE like " + "'%" + searchTitle + "%';";
                Cursor cursor = db.rawQuery(sql, null);
                int count = cursor.getCount();
                Log.i("counttttt",count + " ");

                if(cursor != null && count != 0){
                    cursor.moveToFirst();
                    for(int i = 0 ; i < cursor.getCount() ; i++){
                        BucketItem item = new BucketItem();
                        int id = cursor.getInt(0);
                        String title = cursor.getString(1);
                        String usageTime = cursor.getString(3);
                        String regTime = cursor.getString(4);
                        String category = cursor.getString(2);
                        String startTime = cursor.getString(5);
                        item.setId(id);
                        item.setTitle(title);
                        item.setUsageTime(usageTime);
                        item.setRegTime(regTime);
                        item.setCategory(category);
                        item.setStartTime(startTime);
                        arrList.add(item);


                        cursor.moveToNext();
                    }
                }
                Intent ia = new Intent();
                ia.putParcelableArrayListExtra("aa",arrList);
                ia.putExtra("searchKey",titleEdit.getText().toString());
                ia.putExtra("usageTime",selectedHour);
                setResult(RESULT_OK,ia);
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

    public void init() {
        titleEdit = findViewById(R.id.titleEdit);
        hourSpinner = findViewById(R.id.hour_spinner);
        searchBtn = findViewById(R.id.searchBtn);
        backBtn = findViewById(R.id.backBtn);


    }


}
