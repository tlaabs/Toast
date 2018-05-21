package com.jihu.toast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciver extends BroadcastReceiver {
    private static final String LOG ="NOTI_ALARM";
    @Override
    public void onReceive(Context context, Intent intent){
        Log.v(LOG,"noti reciver start");
        Intent mServiceIntent = new Intent(context, AlarmNotiService.class);
        mServiceIntent.putExtra("title",intent.getStringExtra("title"));
        mServiceIntent.putExtra("type",intent.getIntExtra("type",-1));
        context.startService(mServiceIntent);
        Log.v(LOG,"noti reciver end");
    }
}
