package io.github.tlaabs.toast_sy.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.github.tlaabs.toast_sy.BucketItem;

public class AlarmReciver extends BroadcastReceiver {
    private static final String LOG ="ALARM";
    private BucketItem item;
    @Override
    public void onReceive(Context context, Intent intent){
        Log.v(LOG,"noti reciver start");
        Intent mServiceIntent = new Intent(context, AlarmNotiService.class);
        item = (BucketItem)intent.getSerializableExtra("item");

        mServiceIntent.putExtra("item",item);
        mServiceIntent.putExtra("type",intent.getIntExtra("type",-1));
        context.startService(mServiceIntent);
        Log.v(LOG,"noti :"+item.getTitle()+" is end");
    }
}
