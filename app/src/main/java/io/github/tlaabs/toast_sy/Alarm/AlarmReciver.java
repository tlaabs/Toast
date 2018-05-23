package io.github.tlaabs.toast_sy.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.github.tlaabs.toast_sy.BucketItem;

public class AlarmReciver extends BroadcastReceiver {
    private static final String LOG ="ALARM";
    private BucketItem item;
    private int type;
    @Override
    public void onReceive(Context context, Intent intent){

        item = (BucketItem)intent.getSerializableExtra("item");
        type = intent.getIntExtra("type",-1);

        Log.v(LOG,"noti reciver start");
        Log.v(LOG,"Type is "+type);
        Log.v(LOG,"Title is "+item.getTitle());
        Intent mServiceIntent = new Intent(context, AlarmNotiService.class);

        mServiceIntent.putExtra("item",item);
        mServiceIntent.putExtra("type",type);

        context.startService(mServiceIntent);
    }
}
