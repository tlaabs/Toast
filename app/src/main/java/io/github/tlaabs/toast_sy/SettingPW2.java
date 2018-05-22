package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

//비밀번호 재확인 액티비티
public class SettingPW2 extends AppCompatActivity {

    ImageView c1,c2,c3,c4;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, enter;
    ImageButton backspace;
    int n1,n2,n3,n4;
    String pw2 = "";

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pw2);

        init();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(4);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(5);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(6);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(7);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(8);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNumberIn(9);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==4)
                {
                    // 앞에서 설정한 비밀번호 받기.
                    Intent passedIntent = getIntent();
                    String pw1 = passedIntent.getStringExtra("pw");

                    //(1) 비밀 번호가 일치할 때
                    if(pw1.equals(pw2))
                    {
                        SharedPreferences pref = getSharedPreferences("security", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("pw", pw1);
                        editor.commit();

                        setResult(333,passedIntent);
                        finish();
                    }

                    // (2) 비밀 번호가 일치하지 않을 때
                    else
                    {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.\n 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        pw2 = "";
                        count=0;
                        c1.setImageResource(R.drawable.empty_circle);
                        c2.setImageResource(R.drawable.empty_circle);
                        c3.setImageResource(R.drawable.empty_circle);
                        c4.setImageResource(R.drawable.empty_circle);
                    }


                }
                else if(count<4)
                {
                    Toast.makeText(getApplicationContext(),"비밀번호 4자리를 모두 입력하세요.",Toast.LENGTH_LONG).show();
                }

            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count)
                {
                    case 1:
                        pw2 = "";
                        count--;
                        c1.setImageResource(R.drawable.empty_circle);
                        break;

                    case 2:
                        pw2 = n1+ "";
                        count--;
                        c2.setImageResource(R.drawable.empty_circle);
                        break;

                    case 3:
                        pw2 = n1+n2+ "";
                        count--;
                        c3.setImageResource(R.drawable.empty_circle);
                        break;

                    case 4:
                        pw2 = n1+n2+n3+ "";
                        count--;
                        c4.setImageResource(R.drawable.empty_circle);
                        break;
                }
            }
        });

    }//end onCreate

    public void init()
    {
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);

        enter=(Button)findViewById(R.id.enter);
        backspace=(ImageButton)findViewById(R.id.backspace);
    }

    public void putNumberIn ( int n){
        switch (count)
        {
            case 0:
                n1=n;
                pw2 = n1 + "";
                count++;
                c1.setImageResource(R.drawable.filled_circle);
                break;

            case 1:
                n2=n;
                pw2=pw2+n2;
                count++;
                c2.setImageResource(R.drawable.filled_circle);
                break;

            case 2:
                n3=n;
                pw2=pw2+n3;
                count++;
                c3.setImageResource(R.drawable.filled_circle);
                break;

            case 3:
                n4=n;
                pw2=pw2+n4;
                count++;
                c4.setImageResource(R.drawable.filled_circle);
                break;
        }

        // Toast.makeText(getApplicationContext(), "" + pw2, Toast.LENGTH_SHORT).show();
    }

}

