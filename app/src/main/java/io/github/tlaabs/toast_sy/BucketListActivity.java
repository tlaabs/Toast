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
    private ArrayList<BucketItem> arrList;

    BucketFragment frAll;
    BucketFragment frTrip;
    BucketFragment frRest;
    BucketFragment frMovie;
    BucketFragment frActivity;
    BucketFragment frEtc;
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
        arrList = new ArrayList<BucketItem>();
        frAll = new BucketFragment();
        frTrip = new BucketFragment();
        frRest = new BucketFragment();
        frMovie = new BucketFragment();
        frActivity = new BucketFragment();
        frEtc = new BucketFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrList.clear();
        loadDB();
        frAll.setTheme("ALL");
        frTrip.setTheme("여행");
        frRest.setTheme("식당");
        frMovie.setTheme("영화");
        frActivity.setTheme("활동");
        frEtc.setTheme("기타");
        frAll.addData(arrList);
        frTrip.addData(arrList);
        frRest.addData(arrList);
        frMovie.addData(arrList);
        frActivity.addData(arrList);
        frEtc.addData(arrList);

    }

    public void loadDB(){
        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        String sql = "SELECT * FROM simDB;";
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();

        if(cursor != null && count != 0){
            cursor.moveToFirst();
            for(int i = 0 ; i < cursor.getCount() ; i++){
                BucketItem item = new BucketItem();
                String title = cursor.getString(1);
                String usageTime = cursor.getString(3);
                String regTime = cursor.getString(4);
                String category = cursor.getString(2);
                item.setTitle(title);
                item.setUsageTime(usageTime);
                item.setRegTime(regTime);
                item.setCategory(category);
                arrList.add(item);

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
                    return "ALL";
                case 1:
                    return "여행";
                case 2:
                    return "식당";
                case 3:
                    return "영화";
                case 4:
                    return "활동";
                case 5:
                    return "기타";

                default:
                    return null;
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return frAll;
                case 1:

                    return frTrip;
                case 2:

                    return frRest;
                case 3:

                    return frMovie;
                case 4:

                    return frActivity;
                case 5:
                    return frEtc;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 6;
        }
    }


}
