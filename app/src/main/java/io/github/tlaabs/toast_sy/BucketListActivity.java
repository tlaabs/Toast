package io.github.tlaabs.toast_sy;

import android.content.Intent;
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

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;


public class BucketListActivity extends AppCompatActivity {
    final static int SEARCH_BUCKET_ACTIVITY = 1;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteDatabase db;
    private ArrayList arrList;
    private ArrayList arrSearchList;

    BucketFragment frAll;
    BucketFragment frTrip;
    BucketFragment frRest;
    BucketFragment frMovie;
    BucketFragment frActivity;
    BucketFragment frEtc;

    int preSearchFlag = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("버킷 리스트");

        init();
        DBmanager database = new DBmanager(getApplicationContext());
        loadDB();

        TabPagerAdapter mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mTabPagerAdapter);

        tabLayout.setTabTextColors(getResources().getColor(R.color.tabColorDefault), getResources().getColor(R.color.tabColorMark));
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

        //-------------
        frAll.setContext(this);
        frTrip.setContext(this);
        frRest.setContext(this);
        frMovie.setContext(this);
        frActivity.setContext(this);
        frEtc.setContext(this);
        //----------------

        Log.i("BucketListActivity","OnrRESUME");
        if(preSearchFlag == 1) initDBFromArrList(arrSearchList);
    }

    public void initDBFromArrList(ArrayList<BucketItem> arr){
//        arrList.clear();
//        loadDB();
        frAll.setTheme("ALL");
        frTrip.setTheme("여행");
        frRest.setTheme("식당");
        frMovie.setTheme("영화");
        frActivity.setTheme("활동");
        frEtc.setTheme("기타");
        frAll.addData(arr);
        frTrip.addData(arr);
        frRest.addData(arr);
        frMovie.addData(arr);
        frActivity.addData(arr);
        frEtc.addData(arr);
//        Log.i("BucketListActivity","OnrRESUME");
    }

    public void loadDB(){
        db = new DBmanager(getApplicationContext()).getRDB();
        String sql = "SELECT * FROM "+ DBmanager.TABLE_ITEM+" WHERE STATE = 0;";
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();

        if(cursor != null && count != 0){
            cursor.moveToFirst();
            for(int i = 0 ; i < cursor.getCount() ; i++){
                BucketItem item = new BucketItem();
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String usageTime = cursor.getString(3);
                String regTime = cursor.getString(4);
                String category = cursor.getString(2);
                String startTime = cursor.getString(5);
                item.setId(id);
                item.setTitle(title);
                item.setUsageTime(usageTime);
                item.setRegTime(regTime);
                item.setCategory(category);
                item.setStartTime(startTime);
                arrList.add(item);

                cursor.moveToNext();
            }
        }
        db.close();
        Log.i("lolo", "count" + count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bucket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.search:
                Intent i = new Intent(this, SearchBucketActivity.class);
                startActivityForResult(i,SEARCH_BUCKET_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SEARCH_BUCKET_ACTIVITY){
            if(resultCode == RESULT_OK){
                arrSearchList = data.getParcelableArrayListExtra("aa");
                preSearchFlag = 1;
                Log.i("init","init");
//                BucketItem i = (BucketItem)arrList.get(0);
//                Log.i("init",i.getTitle() + " ");
                initDBFromArrList(arrList);
                getSupportActionBar().setTitle(
                        data.getStringExtra("searchKey") + ", " +
                                data.getStringExtra("usageTime") + " 시간");
            }
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
