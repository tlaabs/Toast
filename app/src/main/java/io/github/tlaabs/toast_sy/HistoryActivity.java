package io.github.tlaabs.toast_sy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {
    final static int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 0;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteDatabase db;
    private ArrayList<BucketItem> arrList;

    HistoryBucketFragment frAll;
    HistoryBucketFragment frTrip;
    HistoryBucketFragment frRest;
    HistoryBucketFragment frMovie;
    HistoryBucketFragment frActivity;
    HistoryBucketFragment frEtc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("완료 리스트");

        init();
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
        frAll = new HistoryBucketFragment();
        frTrip = new HistoryBucketFragment();
        frRest = new HistoryBucketFragment();
        frMovie = new HistoryBucketFragment();
        frActivity = new HistoryBucketFragment();
        frEtc = new HistoryBucketFragment();
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
        Log.i("BucketListActivity","OnrRESUME");
        requestReadExternalStoragePermission();

    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void loadDB(){
        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        String sql = "SELECT * FROM simDB WHERE STATE = 2;";
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
                String endTime = cursor.getString(7);
                String completeTime = cursor.getString(8);
                String imgSrc = cursor.getString(9);
                String review = cursor.getString(10);

                item.setId(id);
                item.setTitle(title);
                item.setCategory(category);
                item.setUsageTime(usageTime);
                item.setRegTime(regTime);
                item.setStartTime(startTime);
                item.setEndTime(endTime);
                item.setCompleteTime(completeTime);
                item.setImgSrc(imgSrc);
                item.setReview(review);
                arrList.add(item);

                cursor.moveToNext();
            }
        }

        Log.i("lolo", "count" + count);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

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
