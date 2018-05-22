package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ModifyBucketActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText titleEdit;
    Spinner hourSpinner;
    Spinner categorySpinner;

    Button modifyBtn;
    Button deleteBtn;
    Button backBtn;

    String[] hours = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] category = {"여행", "식당", "영화", "활동", "기타"};

    String selectedHour = "-";
    String selectedCategory = "여행";

    BucketItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bucket);

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
        hourSpinner.setSelection(getHoursIdxFromItem(item));

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


        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                recordValues.put("TITLE", titleEdit.getText().toString());
                recordValues.put("USAGE_TIME", selectedHour);
                recordValues.put("STATE",0);
                recordValues.put("CATEGORY",selectedCategory);

//                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                Log.i("date","date : " + today);
//                recordValues.put("REG_TIME", today);

                db.update("simDB",recordValues,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"수정 완료!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete("simDB","ID=" + item.getId(),null);
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

    public int getHoursIdxFromItem(BucketItem item){
        String h = item.getUsageTime();

        for(int i = 0 ; i < hours.length; i++)
            if(h.equals(hours[i])) return i;

        return -1;
    }
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
        modifyBtn = findViewById(R.id.modifyBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        backBtn = findViewById(R.id.backBtn);

        //getIntent

        Intent i = getIntent();
        item = (BucketItem)i.getSerializableExtra("item");

        titleEdit.setText(item.getTitle());
    }
}
