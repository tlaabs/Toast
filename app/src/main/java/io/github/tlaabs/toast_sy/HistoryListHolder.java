package io.github.tlaabs.toast_sy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class HistoryListHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView completeTime;
    ImageView body;


    public HistoryListHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        completeTime = itemView.findViewById(R.id.complete_time);
        body = itemView.findViewById(R.id.body);
    }
}
