package org.androidtown.toast_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingLockScreen extends AppCompatActivity {

    Switch useLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_lock_screen);

        useLS = (Switch) findViewById(R.id.useLS);

        // '잠금화면 사용'버튼 클릭시
        useLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startService(new Intent(getApplicationContext(),LockScreenService.class));

            }
        });

    }//end onCreate

}
