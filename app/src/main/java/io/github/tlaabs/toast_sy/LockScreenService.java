package io.github.tlaabs.toast_sy;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                Intent i = new Intent(context, LockScreenActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }

        }

    };

    ////////////////////////////////////////////////////////// 하드웨어 back 버튼 누르기 금지//
    public void onBackPressed(){
     //
    }

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }



    @Override

    public void onCreate() {

        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

        registerReceiver(receiver, filter);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        unregisterReceiver(receiver);

    }
}
