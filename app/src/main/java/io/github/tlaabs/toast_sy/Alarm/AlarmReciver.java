package io.github.tlaabs.toast_sy.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import io.github.tlaabs.toast_sy.AddHistoryBucketActivity;
import io.github.tlaabs.toast_sy.BucketItem;
import io.github.tlaabs.toast_sy.BucketListActivity;
import io.github.tlaabs.toast_sy.ExtendBucketActivity;
import io.github.tlaabs.toast_sy.OnGoingActivity;
import io.github.tlaabs.toast_sy.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReciver extends BroadcastReceiver {
    private static final String LOG ="tt";
    private BucketItem item;
    private int type;

    @Override
    public void onReceive(Context context, Intent intent){

        type = intent.getIntExtra("type",-1);

        //large icon 설정
        Bitmap largeIcon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_stat_toast);
        NotificationCompat.Builder NotiBuilder;
        PendingIntent mPendingIntent;

        Log.v(LOG,"리시버 시작");
        Log.v(LOG,"Type is "+type);

        switch (type){
            case 0 : //bucket
                //notification 눌렀을때 실행할 activity
                item = new BucketItem();
                item.setTitle(intent.getStringExtra("Title"));
                item.setId(intent.getIntExtra("Id",-1));
                mPendingIntent = PendingIntent.getActivity(
                        context, 0,
                        new Intent(context, BucketListActivity.class).putExtra("nid",item.getId()),
                        PendingIntent.FLAG_UPDATE_CURRENT);

                //toDO : 시작 눌렀을때 실행할 activity 맹들기
                PendingIntent startPendingIntent = PendingIntent.getActivity(
                        context, 0,
                        new Intent(context, startNow.class).putExtra("nid",item.getId()),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action startAction = new NotificationCompat.Action.Builder(R.drawable.play_btn, "지금 할래요!", startPendingIntent).build();


                //notification channel 설정
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationManager nManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel channelMessage = new NotificationChannel("Bucket", "하고싶어여", NotificationManager.IMPORTANCE_HIGH);
                    channelMessage.setDescription("이거 한번 해볼래요?");
                    channelMessage.enableLights(true);
                    channelMessage.setLightColor(Color.BLUE);
                    channelMessage.enableVibration(true);
                    channelMessage.setVibrationPattern(new long[]{100, 500, 100, 200});
                    channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);//잠금화면에 전부다 보여주는
                    nManager.createNotificationChannel(channelMessage);

                }

                NotiBuilder = new NotificationCompat.Builder(context, "Bucket")
                        .setSmallIcon(R.drawable.ic_stat_toast)
                        .setContentTitle("'"+item.getTitle() + "' 지금 해볼래요?")
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
            case 1 : // ongoing
                //진행중 버킷
                //notification 눌렀을때 실행할 activity
                item = (BucketItem)intent.getSerializableExtra("item");

                Intent i = new Intent(context, OnGoingActivity.class);
                i.putExtra("nid",item.getId());
                mPendingIntent = PendingIntent.getActivity(
                        context, 1,
                        i,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                //취소 눌렀을때 실행할 activity
                PendingIntent cancelPendingIntent = PendingIntent.getActivity(
                        context, 1,
                        new Intent(context, StopOnGoingActivity.class).putExtra("item",item),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action cancelAction = new NotificationCompat.Action.Builder(R.drawable.d_icon, "취소", cancelPendingIntent).build();

                //완료 눌렀을때 실행할 activity
                PendingIntent confirmPendingIntent = PendingIntent.getActivity(
                        context, 1,
                        new Intent(context, AddHistoryBucketActivity.class).putExtra("item",item),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action confirmAction = new NotificationCompat.Action.Builder(R.drawable.c_icon, "완료", confirmPendingIntent).build();

                //연장 눌렀을때 실행할 activity
                PendingIntent extendPendingIntent = PendingIntent.getActivity(
                        context, 1,
                        new Intent(context, ExtendBucketActivity.class).putExtra("item",item),
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Action extendAction = new NotificationCompat.Action.Builder(R.drawable.t_icon, "시간 연장", extendPendingIntent).build();

                //notification channel 설정
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationManager nManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel channelMessage = new NotificationChannel("Ongoing", "진행중인거시여", NotificationManager.IMPORTANCE_HIGH);
                    channelMessage.setDescription("진행중");
                    channelMessage.enableLights(true);
                    channelMessage.setLightColor(Color.BLUE);
                    channelMessage.enableVibration(true);
                    channelMessage.setVibrationPattern(new long[]{100, 500, 100, 200});
                    channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);//잠금화면에 전부다 보여주는
                    nManager.createNotificationChannel(channelMessage);

                }

                NotiBuilder = new NotificationCompat.Builder(context, "Ongoing")
                        .setSmallIcon(R.drawable.ic_done)
                        .setContentTitle("'"+item.getTitle() + "' 시간 만료")
                        .setContentText("어떻게 하실래요?")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setLargeIcon(largeIcon)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(false)
                        .setTicker("시간이 만료 되었어요!")
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("시간이 만료 되었어요!"))
                        .addAction(cancelAction) //취소 버튼
                        .addAction(confirmAction) // 완료 버튼
                        .addAction(extendAction) //연장하자
                        .setContentIntent(mPendingIntent);


                break;
            default:
                NotiBuilder = new NotificationCompat.Builder(context,"fail");
                Log.v(LOG,"Fail call alarm service");
                break;
        }
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(item.getId(), NotiBuilder.build());
    }
}
