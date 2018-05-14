package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBucketActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText titleEdit;
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket);

        init();

        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();

                recordValues.put("TITLE", titleEdit.getText().toString());

                db.insert("simDB", null, recordValues);
            }
        });


    }

    public void init(){
        titleEdit = findViewById(R.id.titleEdit);
        addBtn = findViewById(R.id.addBtn);
    }
}
