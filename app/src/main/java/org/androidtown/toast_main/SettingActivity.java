package org.androidtown.toast_main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class SettingActivity extends AppCompatActivity {

    Button menu_security, menu_lockscreen, menu_backup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();

        //1. 설정 메뉴 - 보안
        menu_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),settingSecurity.class);
                startActivity(intent1);

            }
        });

        //2. 설정 메뉴 - 잠금화면

        //3. 설정 메뉴 - 백업


    }

    public void init(){
        menu_security = (Button)findViewById(R.id.menu_security);
        menu_lockscreen=(Button)findViewById(R.id.menu_lockscreen);
        menu_backup=(Button)findViewById(R.id.menu_backup);
    }
}
