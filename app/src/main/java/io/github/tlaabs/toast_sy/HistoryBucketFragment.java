package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class HistoryBucketFragment extends Fragment{
    RecyclerView recyclerView;
    private ArrayList<BucketItem> arrList;

    String theme;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_bucket, container, false);

        recyclerView = v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


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


    class MyAdapter extends RecyclerView.Adapter<HistoryListHolder>
    {

        public MyAdapter(){

        }

        @NonNull
        @Override
        public HistoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_bucket_item,parent,false);
            HistoryListHolder holder = new HistoryListHolder(v);
            return holder;
        }



        @Override
        public void onBindViewHolder(@NonNull HistoryListHolder holder, int position) {
            final BucketItem item = arrList.get(position);
            String title = item.getTitle();
            holder.title.setText(title);

            String completeTime = item.getCompleteTime();
            holder.completeTime.setText(completeTime);
            Glide.with(getActivity())
                    .load(item.getImgSrc())
                    .into(holder.body);
            holder.body.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), ModifyHistoryBucketActivity.class);
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
