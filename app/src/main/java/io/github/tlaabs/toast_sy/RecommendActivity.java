package io.github.tlaabs.toast_sy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class RecommendActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TabPagerAdapter mTabPagerAdapter;
    private SQLiteDatabase db;
    private ArrayList<BucketItem> arrList;

    RecommendBucketFragment frAll;
    RecommendBucketFragment frTrip;
    RecommendBucketFragment frRest;
    RecommendBucketFragment frMovie;
    RecommendBucketFragment frActivity;
    RecommendBucketFragment frEtc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("추천 리스트");

        init();
//        loadDB();

        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mTabPagerAdapter);

        tabLayout.setTabTextColors(getResources().getColor(R.color.tabColorDefault), getResources().getColor(R.color.tabColorMark));
        tabLayout.setupWithViewPager(viewPager);

        GetRecItemsTask git = new GetRecItemsTask();
        git.execute();

    }

    public void init(){
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        arrList = new ArrayList<BucketItem>();
        frAll = new RecommendBucketFragment();
        frTrip = new RecommendBucketFragment();
        frRest = new RecommendBucketFragment();
        frMovie = new RecommendBucketFragment();
        frActivity = new RecommendBucketFragment();
        frEtc = new RecommendBucketFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        arrList.clear();
//        loadDB();
//        frAll.setTheme("ALL");
//        frTrip.setTheme("여행");
//        frRest.setTheme("식당");
//        frMovie.setTheme("영화");
//        frActivity.setTheme("활동");
//        frEtc.setTheme("기타");
//        frAll.addData(arrList);
//        frTrip.addData(arrList);
//        frRest.addData(arrList);
//        frMovie.addData(arrList);
//        frActivity.addData(arrList);
//        frEtc.addData(arrList);


    }

    public void loadDB(){
        db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        String sql = "SELECT * FROM simDB WHERE STATE = 1;";
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
                item.setId(id);
                item.setTitle(title);
                item.setCategory(category);
                item.setUsageTime(usageTime);
                item.setRegTime(regTime);
                item.setStartTime(startTime);
                item.setEndTime(endTime);
                arrList.add(item);

                cursor.moveToNext();
            }
        }

        Log.i("lolo", "count" + count);
    }
    public void loadFromServer(){

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

    private class GetRecItemsTask extends AsyncTask<String, Integer, String> {

        public GetRecItemsTask() {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i("bbba","bbba1");
                URL url = new URL(getString(R.string.server));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setDefaultUseCaches(false);

                POSTParams postParams = new POSTParams();
                postParams.addParam("method", "all");
//                postParams.addParam("content_id", store.getCotent_id() + "");

                String param = postParams.toString();

                OutputStream os = conn.getOutputStream();
                os.write(param.getBytes("UTF-8"));
                os.flush();
                os.close();

                InputStream is = null;
                BufferedReader in = null;
                String data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 1024);

                String line = null;
                StringBuffer buff = new StringBuffer();
                Log.i("bbba","bbba");
                while ((line = in.readLine()) != null) {
                    buff.append(line);
                }
                data = buff.toString().trim();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("message", "zazazf" + s);
            if (s == null) {
                Toast.makeText(getApplicationContext(), "서버와 연결할 수 없어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    BucketItem item = new BucketItem();
                    item.setId(Integer.parseInt(jo.getString("id")));
                    item.setTitle(jo.getString("title"));
                    item.setCategory(jo.getString("category"));
                    item.setImgSrc(jo.getString("img_src"));
                    arrList.add(item);
                    Log.i("ld",item.getCategory());
                }
                Log.i("reviewList", "zla" + arrList.size());

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
