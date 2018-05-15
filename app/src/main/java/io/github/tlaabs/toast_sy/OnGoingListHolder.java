package io.github.tlaabs.toast_sy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class OnGoingListHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView deadLineTime;
    TextView startTime;
    ImageView onGoingBtn;


    public OnGoingListHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        deadLineTime = itemView.findViewById(R.id.deadline_time);
        startTime = itemView.findViewById(R.id.start_time);
    }
}
