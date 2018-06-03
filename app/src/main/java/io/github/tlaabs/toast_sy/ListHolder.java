package io.github.tlaabs.toast_sy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class ListHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView usageTime;
    TextView regTime;
    ImageView onGoingBtn;

    public ListHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        usageTime = itemView.findViewById(R.id.usage_time);
        regTime = itemView.findViewById(R.id.reg_time);
        onGoingBtn = itemView.findViewById(R.id.on_going_btn);
    }
}
