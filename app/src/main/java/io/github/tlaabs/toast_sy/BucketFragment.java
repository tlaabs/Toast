package io.github.tlaabs.toast_sy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class BucketFragment extends Fragment{
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<BucketItem> arrList;
    private Context activityContext;

    String theme;
    MyAdapter adapter;

    public void setContext(Context context){
        activityContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bucket, container, false);

        recyclerView = v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddBucketActivity.class);
                startActivity(i);
            }
        });


        return v;
    }

    public void addData(ArrayList<BucketItem> arr){
        //THEME 추출
        ArrayList<BucketItem> newArr = new ArrayList();
        for(int i = 0 ; i < arr.size(); i++){
            BucketItem item = arr.get(i);
            if(theme.equals(item.getCategory()) || theme.equals("ALL")){
                newArr.add(item);
            }
        }
        arrList = newArr;
        if(adapter != null && recyclerView != null){
            adapter = new MyAdapter();
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }

    }

    public void setTheme(String theme){
        this.theme = theme;
    }


    class MyAdapter extends RecyclerView.Adapter<ListHolder>
    {

        public MyAdapter(){

        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_item,parent,false);
            ListHolder holder = new ListHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            final BucketItem item = arrList.get(position);
            String title = item.getTitle();
            holder.title.setText(title);

            String usageTime = item.getUsageTime();
            holder.usageTime.setText(usageTime);

            String regTime = item.getRegTime();
            holder.regTime.setText(regTime);

            holder.onGoingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = getContext().openOrCreateDatabase("sim.db", MODE_PRIVATE, null);

                    if(!item.getUsageTime().equals("-")) {

                        //-----------------초기 설정
                        int howlong = Integer.parseInt(item.getUsageTime());
                        Intent notiAlarm = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출

                        notiAlarm.putExtra("item", item); //item
                        notiAlarm.putExtra("type", 1); // 타입 0은 bukcet, 1은 inpro



                        int notiId = item.getId(); //서로 다른 id 부여
                        PendingIntent notiIntent = PendingIntent.getBroadcast(
                                activityContext.getApplicationContext(), notiId, notiAlarm, PendingIntent.FLAG_IMMUTABLE);
                        //------------------------------
                        Log.v("tt","초기설정 완료 : "+item.getTitle());
                        ContentValues recordValues = new ContentValues();

                        recordValues.put("STATE", 1);
                        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        recordValues.put("START_TIME", now);

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());


                        cal.add(Calendar.HOUR, +howlong); //toDo : 테스트 용으로 chfmf eksdnl
                        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
                        recordValues.put("END_TIME", end);


                        db.update("simDB", recordValues, "ID=" + item.getId(), null);
                        ((BucketListActivity) getActivity()).onResume();
                        Toast.makeText(getContext(), "진행중!", Toast.LENGTH_SHORT).show();

                        //알람 설정하는 부분-----------------------------------------------------------


                        AlarmManager notiManager = (AlarmManager) activityContext.getSystemService(Context.ALARM_SERVICE);
                        notiManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notiIntent);
                        //----------------------------------------------------------------------------
                        Log.v("tt","알람 설정 완료 : "+item.getTitle());
                    }else{
                        Intent i = new Intent(getContext(), AddHistoryBucketActivity.class);
                        i.putExtra("item",item);
                        startActivityForResult(i,1);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), ModifyBucketActivity.class);
                    i.putExtra("item",item);
                    startActivityForResult(i,1);
                }
            });


        }



        @Override
        public int getItemCount() {
            return arrList.size();
        }

    }

}
