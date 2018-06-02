package io.github.tlaabs.toast_sy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences settings = getSharedPreferences("security", Context.MODE_PRIVATE);
        Boolean lockscreen = settings.getBoolean("lockscreen",false);

        if(lockscreen==true)
        {
            useLS.setChecked(true);

        }
        else
        {
            useLS.setChecked(false);
        }


        // '잠금화면 사용'버튼 클릭시
        useLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(useLS.isChecked()) {
                    SharedPreferences settings = getSharedPreferences("security", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putBoolean("lockscreen", true);
                    editor.commit();

                    startService(new Intent(getApplicationContext(), LockScreenService.class));
                }
                else{

                    SharedPreferences settings = getSharedPreferences("security", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putBoolean("lockscreen", false);
                    editor.commit();

                    stopService(new Intent(getApplicationContext(), LockScreenService.class));

                }
            }
        });

    }//end onCreate

}
