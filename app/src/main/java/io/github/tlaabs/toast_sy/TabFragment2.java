package io.github.tlaabs.toast_sy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class TabFragment2 extends Fragment{
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList arrList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bucket2, container, false);

        recyclerView = v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter();

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

    public void addData(ArrayList arr){
        arrList = arr;
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
            String str = (String)arrList.get(position);
            holder.title.setText(str);
        }

        @Override
        public int getItemCount() {
            return arrList.size();
        }

    }

}
