package com.jihu.toast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmNotiService extends Service {
    public AlarmNotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
