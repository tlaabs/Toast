package io.github.tlaabs.toast_sy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class initPWDActivity extends AppCompatActivity {
    EditText initPWD;
    EditText checkPWD;
    Button cancleBtn;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_pwd);

        init();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chk1, chk2;
                chk1 = initPWD.getText().toString();
                chk2 = checkPWD.getText().toString();
                if(chk1.equals(chk2)){
                    Toast.makeText(getApplicationContext(), "비밀번호 설정 완료!", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getSharedPreferences("toastPWD", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("pwd", chk1);
                    editor.commit();
                    finish();
                }else{ //fail
                    Toast.makeText(getApplicationContext(), "비밀번호 다시 확인!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void init() {
        initPWD = findViewById(R.id.initPWD);
        checkPWD = findViewById(R.id.checkPWD);
        cancleBtn = findViewById(R.id.cancleBtn);
        submitBtn = findViewById(R.id.submitBtn);
    }


}
