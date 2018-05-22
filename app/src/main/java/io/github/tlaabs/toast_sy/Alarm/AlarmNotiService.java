package io.github.tlaabs.toast_sy.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import io.github.tlaabs.toast_sy.AddHistoryBucketActivity;
import io.github.tlaabs.toast_sy.BucketFragment;
import io.github.tlaabs.toast_sy.BucketItem;
import io.github.tlaabs.toast_sy.BucketListActivity;
import io.github.tlaabs.toast_sy.ExtendBucketActivity;
import io.github.tlaabs.toast_sy.OnGoingActivity;
import io.github.tlaabs.toast_sy.OnGoingListHolder;
import io.github.tlaabs.toast_sy.R;

public class AlarmNotiService extends Service {
    private static final String LOG ="ALARM";
    BucketItem item;

    public AlarmNotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId ){

        //intent로 받은값
        item = (BucketItem)intent.getSerializableExtra("item");
        int type = intent.getIntExtra("type",-1);


        Log.v(LOG,"noti service start");
        Log.v(LOG,"type id is : "+type);

        //large icon 설정
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.notiicon);
        NotificationCompat.Builder NotiBuilder;
        PendingIntent mPendingIntent;

        switch (type){
            case 0 : // 버킷 리스트에서

                //notification 눌렀을때 실행할 activity
                mPendingIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0,
                        new Intent(getApplicationContext(), BucketListActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

                //toDO : 시작 눌렀을때 실행할 activity 맹들기
                PendingIntent startPendingIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0,
                        new Intent(getApplicationContext(), BucketListActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action startAction = new NotificationCompat.Action.Builder(R.drawable.notiicon, "지금 할래요!", startPendingIntent).build();


                //notification channel 설정
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel channelMessage = new NotificationChannel("Bucket", "하고싶어여", NotificationManager.IMPORTANCE_HIGH);
                    channelMessage.setDescription("이거 한번 해볼래요?");
                    channelMessage.enableLights(true);
                    channelMessage.setLightColor(Color.BLUE);
                    channelMessage.enableVibration(true);
                    channelMessage.setVibrationPattern(new long[]{100, 500, 100, 200});
                    channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);//잠금화면에 전부다 보여주는
                    nManager.createNotificationChannel(channelMessage);

                }

              NotiBuilder = new NotificationCompat.Builder(getApplicationContext(), "Bucket")
                        .setSmallIcon(R.drawable.play_button)
                        .setContentTitle(item.getTitle() + " 지금 해볼래요?")
                        .setContentText("새로운 추억을 만들어 봐요!")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setLargeIcon(largeIcon)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(false)
                        .setTicker("이거 한번 해볼래요?")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("오늘은 이거 해보는게 어때요?"))
                        .addAction(startAction) //시작 버튼
                        .setContentIntent(mPendingIntent);

                break;
            case 1: //진행중 버킷
            //notification 눌렀을때 실행할 activity
            mPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1,
                    new Intent(getApplicationContext(), OnGoingActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);

            //취소 눌렀을때 실행할 activity
            PendingIntent cancelPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1,
                    new Intent(getApplicationContext(), StopOnGoingActivity.class).putExtra("item",item),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action cancelAction = new NotificationCompat.Action.Builder(R.drawable.notiicon, "안할래", cancelPendingIntent).build();

            //완료 눌렀을때 실행할 activity
            PendingIntent confirmPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1,
                    new Intent(getApplicationContext(), AddHistoryBucketActivity.class).putExtra("item",item),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action confirmAction = new NotificationCompat.Action.Builder(R.drawable.complete, "완료했어요", confirmPendingIntent).build();

            //연장 눌렀을때 실행할 activity
            PendingIntent extendPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1,
                    new Intent(getApplicationContext(), ExtendBucketActivity.class).putExtra("item",item),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action extendAction = new NotificationCompat.Action.Builder(R.drawable.extend, "시간을 조금만 더..", extendPendingIntent).build();

            //notification channel 설정
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channelMessage = new NotificationChannel("Ongoing", "진행중인거시여", NotificationManager.IMPORTANCE_HIGH);
                channelMessage.setDescription("진행중");
                channelMessage.enableLights(true);
                channelMessage.setLightColor(Color.BLUE);
                channelMessage.enableVibration(true);
                channelMessage.setVibrationPattern(new long[]{100, 500, 100, 200});
                channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);//잠금화면에 전부다 보여주는
                nManager.createNotificationChannel(channelMessage);

            }

                NotiBuilder = new NotificationCompat.Builder(getApplicationContext(), "Ongoing")
                    .setSmallIcon(R.drawable.extend)
                    .setContentTitle(item.getTitle() + " 시간이 만료되었습니다")
                    .setContentText("어떻게 할래요?")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setLargeIcon(largeIcon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(false)
                    .setTicker("시간이 만료 되었어요!")
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText("시간이 만료되었어요"))
                    .addAction(cancelAction) //취소 버튼
                    .addAction(confirmAction) // 완료 버튼
                    .addAction(extendAction) //연장하자
                    .setContentIntent(mPendingIntent);

            break;
            default:
                NotiBuilder = new NotificationCompat.Builder(getApplicationContext(),"default");
                break;
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(item.getId(), NotiBuilder.build());

        Log.v(LOG,"noti service onstartcommand end");
        return START_STICKY;
    }
}
