package io.github.tlaabs.toast_sy;

import android.content.ContentValues;
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
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

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

            SimpleDateFormat transFormatnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String now = transFormatnew.format(new Date());
            String endTimeP = endTime.substring(11,endTime.length());
            String nowP = now.substring(11,now.length());
            Log.i("pop",endTimeP + " | " + nowP);
            int M = Integer.parseInt(endTimeP.substring(3,5)) - Integer.parseInt(nowP.substring(3,5));
            int dayFlag = 0;
            if(M < 0) {
                M = Integer.parseInt(endTimeP.substring(3,5)) + 60 - Integer.parseInt(nowP.substring(3,5));
                dayFlag = 1;
            }

            int H = Integer.parseInt(endTimeP.substring(0,2))-dayFlag - Integer.parseInt(nowP.substring(0,2));
            if(H < 0) H = Integer.parseInt(endTimeP.substring(0,2)) + 24 - Integer.parseInt(nowP.substring(0,2));
            String HS = H + "";
            if(HS.length() == 1) HS = "0" + H;
            String MS = M + "";
            if(MS.length() == 1) MS = "0" + M;


            Log.i("OHH","H : " + H);
            holder.deadLineTime.setText(HS+":"+MS);

            holder.stopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = getContext().openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
                    ContentValues recordValues = new ContentValues();

                    recordValues.put("STATE", 0);

                    db.update("simDB",recordValues,"ID=" + item.getId(),null);
                    ((OnGoingActivity)getActivity()).onResume();
                    Toast.makeText(getContext(),"중지",Toast.LENGTH_SHORT).show();
                }
            });

            holder.extendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), ExtendBucketActivity.class);
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
