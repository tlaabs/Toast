package org.androidtown.toast_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class settingSecurity extends AppCompatActivity {

    Switch usePW,useFP;
    Button changePW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_security);

        init();

        //1. '비밀번호 사용'버튼 클릭시
        usePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1-1.비밀번호 사용 버튼을 눌러서 직접 on 시키면
                if(usePW.isChecked())
                {
                    //비밀번호 설정을 위한 액티비티 띄우기.
                    Intent intent1 = new Intent(getApplicationContext(),SettingPW.class);
                    startActivityForResult(intent1,111);
              }
              //1-2. 비밀번호 사용 버튼을 눌러서 직접 off 시키면
                else
                {
                    changePW.setEnabled(false);         //비밀번호 변경 버튼도 off
                }

            }
        });

        //2. '비밀번호 변경'버튼 클릭시
        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //비밀번호 변경을 위한 액티비티 띄우기.
                    Intent intent1 = new Intent(getApplicationContext(),SettingPW.class);
                    startActivityForResult(intent1,777);
                }
        });

        //3. '지문인식 사용'버튼 클릭시
        useFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //3-1.'지문인식 사용' 버튼을 눌러서 직접 on 시키면
                if(useFP.isChecked())
                {
                    useFP.setChecked(true);     //지문인식사용 버튼 on
                    usePW.setChecked(false);    //비밀번호 사용 버튼 off
                    changePW.setEnabled(false);  //비밀번호 변경 버튼 off
                    /*
                    //지문인식 설정을 위한 액티비티 띄우기.
                    Intent intent2 = new Intent(getApplicationContext(),settingFP.class);
                    startActivityForResult(intent2,555);*/
                }
            }
        });

    }

    public void init(){
        usePW = (Switch)findViewById(R.id.usePW);
        changePW=(Button)findViewById(R.id.changePW);
        useFP = (Switch)findViewById(R.id.useFP);
    }

    //비밀번호 설정과 재확인까지 마친 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //1-1.비밀번호 설정 - 비밀번호 재확인이 일치한 경우
        if(requestCode==111 && resultCode==444)
        {
            Toast.makeText(getApplicationContext(),"비밀번호 설정 성공!",Toast.LENGTH_LONG).show();
            usePW.setChecked(true);     //비밀번호 사용 버튼 on
            changePW.setEnabled(true);  //비밀번호 변경 버튼 on
            useFP.setChecked(false);    //지문인식 사용 버튼 off
        }


        //1-2. 비밀번호 설정 - 비밀번호가 올바르게 설정되지 않으면 '비밀번호 사용 버튼 off'
        else if(requestCode==111 && resultCode !=444)
        {
            usePW.setChecked(false);
            Toast.makeText(getApplicationContext(),"비밀번호 설정 실패!",Toast.LENGTH_LONG).show();
        }


        //2-1. 비밀번호 변경- 비밀번호 재확인이 일치한 경우
        if(requestCode==777 && resultCode==444)
        {
            Toast.makeText(getApplicationContext(),"비밀번호 변경 성공!",Toast.LENGTH_LONG).show();
        }

        //2-2. 비밀번호 변경- 비밀번호가 올바르게 설정되지 않은 경우
        // 그러나 비밀번호는 '비밀번호 설정'을 통해 이미 올바르게 설정되어 있으므로 버튼들 상태 변화는 없음.
        else if(requestCode==777 && resultCode !=444)
        {
            Toast.makeText(getApplicationContext(),"비밀번호 변경 실패!",Toast.LENGTH_LONG).show();
        }



        /*
        //3-1. 지문인식이 설정된 경우
        else if(requestCode==555 && resultCode==666)
        {
            useFP.setChecked(true);     //지문인식사용 버튼 on
            usePW.setChecked(false);    //비밀번호 사용 버튼 off
            changePW.setEnabled(false);  //비밀번호 변경 버튼 off

            Toast.makeText(getApplicationContext(),"지문인식 설정 성공!",Toast.LENGTH_LONG).show();
        }
        //3-2. 지문인식이 올바르게 설정되지 않은 경우
        else if(requestCode==555 && resultCode!=666)
        {
            useFP.setChecked(false);
            Toast.makeText(getApplicationContext(),"지문인식 설정 실패!",Toast.LENGTH_LONG).show();
        }*/
    }
}
