package io.github.tlaabs.toast_sy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckPWDActivity extends AppCompatActivity {
    EditText checkPWD;
    Button cancleBtn;
    Button submitBtn;

    String pwdFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pwd);

        init();
        pwdFrom = getIntent().getStringExtra("pwd");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chk;
                chk = checkPWD.getText().toString();
                if(chk.equals(pwdFrom)){
                    Toast.makeText(getApplicationContext(), "보안 해제", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }else{ //fail
                    Toast.makeText(getApplicationContext(), "비밀번호 다시 확인!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void init() {
        checkPWD = findViewById(R.id.checkPWD);
        cancleBtn = findViewById(R.id.cancleBtn);
        submitBtn = findViewById(R.id.submitBtn);
    }


}
