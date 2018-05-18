package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    TextView initPWD;
    TextView enablePWD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

        initPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),initPWDActivity.class);
                startActivity(i);
            }
        });
        enablePWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("toastPWD", MODE_PRIVATE);
                int set = pref.getInt("enable",-1);
                if(set == -1) Toast.makeText(getApplicationContext(), "보안 활성화 완료", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), "보안 비활성화 완료", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("enable", set * -1);
                editor.commit();

            }
        });
    }

    public void init(){
        initPWD = findViewById(R.id.initPWD);
        enablePWD = findViewById(R.id.enablePWD);
    }
}
