package io.github.tlaabs.toast_sy;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class OneDayAlarmSettingActivity extends AppCompatActivity {
    SQLiteDatabase db;

    Spinner hourSpinner;
    Spinner minuteSpinner;

    Button submitBtn;
    Button backBtn;

    String[] hours = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12","13","14","15","16","17","18","19","20","21","22","23"};
    String[] minutes = {"0","5","10","15","20","25","30","35","40","45","50","55"};

    String selectedHour = "-";
    String selectedMinute = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneday_alarm);

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
        hourSpinner.setSelection(Integer.parseInt(selectedHour));

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(adapter2);
        minuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMinute = minutes[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        minuteSpinner.setSelection(Integer.parseInt(selectedMinute)/5);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("alarm", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("H",Integer.parseInt(selectedHour));
                editor.putInt("M",Integer.parseInt(selectedMinute));
                editor.commit();

                Toast.makeText(getApplicationContext(),"알람 설정 완료!",Toast.LENGTH_SHORT).show();
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

        hourSpinner = findViewById(R.id.hour_spinner);
        minuteSpinner = findViewById(R.id.minute_spinner);
        submitBtn = findViewById(R.id.submitBtn);
        backBtn = findViewById(R.id.backBtn);

        SharedPreferences pref = getSharedPreferences("alarm", MODE_PRIVATE);
        int h = pref.getInt("H",9);
        int m = pref.getInt("M",0);
        Log.i("tt2",h + "|"+m);
        selectedHour = h+"";
        selectedMinute = m+"";

    }
}
