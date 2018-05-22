package io.github.tlaabs.toast_sy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class RecommendListHolder extends RecyclerView.ViewHolder{
    ImageView body;
    TextView title;

    public RecommendListHolder(View itemView){
        super(itemView);
        body = itemView.findViewById(R.id.body);
        title = itemView.findViewById(R.id.title);
    }
}
