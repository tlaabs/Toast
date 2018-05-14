package io.github.tlaabs.toast_sy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by devsimMe on 2018-05-14.
 */

public class ListHolder extends RecyclerView.ViewHolder{
    TextView title;

    public ListHolder(View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }
}
