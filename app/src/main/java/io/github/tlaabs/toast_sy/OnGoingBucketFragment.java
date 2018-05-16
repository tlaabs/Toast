package io.github.tlaabs.toast_sy;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class OnGoingBucketFragment extends Fragment{
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<BucketItem> arrList;

    String theme;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ongoing_bucket, container, false);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void setTheme(String theme){
        this.theme = theme;
    }


    class MyAdapter extends RecyclerView.Adapter<OnGoingListHolder>
    {

        public MyAdapter(){

        }

        @NonNull
        @Override
        public OnGoingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_bucket_item,parent,false);
            OnGoingListHolder holder = new OnGoingListHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OnGoingListHolder holder, int position) {
            final BucketItem item = arrList.get(position);
            String title = item.getTitle();
            holder.title.setText(title);

            String startTime = item.getStartTime();
            holder.startTime.setText(startTime);

            String endTime = item.getEndTime();


            //종료시간 - 현재시간
            //연장하게되면 종료시간을 늘리고 다시 계산
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat transFormatnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = null;
            try {
                d1 = transFormatnew.parse(endTime);
            }catch(Exception e){}
            c1.setTime(d1);
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Log.i("endTime|now",endTime + "|" + now);
//            Calendar c2 = Calendar.getInstance();
//            Date d2 = null;
//            try{
//                d2 = transFormatnew.parse(startTime);
//            }catch(Exception e){}
//            c2.setTime(d2);
            Calendar c2 = Calendar.getInstance();
            Date d2 = null;
            try {
                d2 = transFormatnew.parse(now);
            }catch(Exception e){}
            c2.setTime(d2);
            long dif = (c1.getTimeInMillis()-c2.getTimeInMillis());
            Log.i("dif",dif+"");
            Date n = new Date(dif);
            //24시간일떄 에러존재 가능성잇음
            String t = new SimpleDateFormat("HH:mm:ss").format(n.getTime());

            String deadlineTime = t;
            holder.deadLineTime.setText(t);

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
