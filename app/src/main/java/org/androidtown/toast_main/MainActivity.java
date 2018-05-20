package org.androidtown.toast_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fingerprints.service.FingerprintManager;

public class MainActivity extends AppCompatActivity {

    // 4 buttons at Main
    Button btn_bucket, btn_ongoing, btn_complete, btn_recommend;
    FingerprintManager fingerprintManager = FingerprintManager.open();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btn_bucket=(Button)findViewById(R.id.btn_bucket);
       btn_ongoing=(Button)findViewById(R.id.btn_ongoing);
       btn_complete=(Button)findViewById(R.id.btn_complete);
       btn_recommend=(Button)findViewById(R.id.btn_recommend);

        if(fingerprintManager != null){
            fingerprintManager.startIdentify(new FingerprintManager.IdentifyCallback(){
                @Override
                public void onIdentified(int i, boolean b) {
                    //fillInput();
                    Toast.makeText(getApplicationContext(),"인증성공",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNoMatch() {
                   // initInput();
                    Toast.makeText(getApplicationContext(),"지문인증 실패, 다시 시도하세요",Toast.LENGTH_SHORT).show();
                    //scanFingerprint();
                }
            },fingerprintManager.getIds());
        }

    }

    private void cancelFingerprint(){
        if(fingerprintManager!=null)fingerprintManager.release();
    }

    /**
     *  main_menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_alarm:
                return true;

            case R.id.menu_setting:
                Intent menu_intent2 = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(menu_intent2);
                return true;

            default:
               return super.onOptionsItemSelected(item);
        }
    }
}
