package io.github.tlaabs.toast_sy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingPW extends AppCompatActivity {

    ImageView c1,c2,c3,c4;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, enter;
    ImageButton backspace;
    int n1,n2,n3,n4;
    String pw = "";

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pw);

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
                    //비밀번호 4자리 치고 확인버튼 누르면, '비밀번호 재확인' 액티비티 띄우기.
                    Intent intent = new Intent(getApplicationContext(),SettingPW2.class);
                    intent.putExtra("pw",pw);    // 비밀번호 보내주기.

                    startActivityForResult(intent,222);
                }
                else
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
                        pw = "";
                        count--;
                        c1.setImageResource(R.drawable.empty_circle);
                        break;

                    case 2:
                        pw = n1+ "";
                        count--;
                        c2.setImageResource(R.drawable.empty_circle);
                        break;

                    case 3:
                        pw = n1+n2+ "";
                        count--;
                        c3.setImageResource(R.drawable.empty_circle);
                        break;

                    case 4:
                        pw = n1+n2+n3+ "";
                        count--;
                        c4.setImageResource(R.drawable.empty_circle);
                        break;
                }
            }
        });
    }

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
                pw = n1 + "";
                count++;
                c1.setImageResource(R.drawable.filled_circle);
                break;

            case 1:
                n2=n;
                pw=pw+n2;
                count++;
                c2.setImageResource(R.drawable.filled_circle);
                break;

            case 2:
                n3=n;
                pw=pw+n3;
                count++;
                c3.setImageResource(R.drawable.filled_circle);
                break;

            case 3:
                n4=n;
                pw=pw+n4;
                count++;
                c4.setImageResource(R.drawable.filled_circle);
                break;
        }

        // Toast.makeText(getApplicationContext(), "" + pw, Toast.LENGTH_SHORT).show();
    }

    //비밀번호 재확인 액티비티 종료 후 자동으로 호출.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //비밀번호 재확인이 올바르게 끝난 경우
        if(requestCode==222 && resultCode==333)
        {
            Intent resultIntent = new Intent();
            setResult(444,resultIntent);
            finish();
        }
    }
}
