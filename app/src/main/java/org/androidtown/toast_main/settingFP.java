package org.androidtown.toast_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class settingFP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_fp);

        Intent resultIntent = new Intent();
        setResult(666,resultIntent);
    }
}
