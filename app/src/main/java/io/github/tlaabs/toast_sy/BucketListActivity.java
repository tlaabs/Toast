package io.github.tlaabs.toast_sy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class BucketListActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteDatabase db;
    private ArrayList arrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        loadDB();

        TabPagerAdapter mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mTabPagerAdapter);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setupWithViewPager(viewPager);






    }

    public void init(){
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        arrList = new ArrayList();
    }

    public void loadDB(){
        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        String sql = "SELECT TITLE FROM simDB;";
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();

        if(cursor != null && count != 0){
            cursor.moveToFirst();
            for(int i = 0 ; i < cursor.getCount() ; i++){
                String title = cursor.getString(0);
                Log.i("lolo","" + cursor.getColumnCount());
                arrList.add(title);

                cursor.moveToNext();
            }
        }

        Log.i("lolo", "count" + count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "About";
                case 1:
                    return "Menu";
                case 2:
                    return "Review";
                default:
                    return null;
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabFragment1 tabFragment1 = new TabFragment1();
                    return tabFragment1;
                case 1:
                    TabFragment2 tabFragment2 = new TabFragment2();
                    tabFragment2.addData(arrList);
                    return tabFragment2;
                case 2:
                    TabFragment1 tabFragment3 = new TabFragment1();
                    return tabFragment3;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
