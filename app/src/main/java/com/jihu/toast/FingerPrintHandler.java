package com.jihu.toast;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private Context appContext;
    private ImageView imgView;
    private AppCompatActivity baseActivity;

    public FingerPrintHandler(Context context) {
        this.appContext = context;

    }


    public FingerPrintHandler(Context context, AppCompatActivity activity,ImageView imgView) {
        this.appContext = context;
        this.baseActivity = activity;
        this.imgView = imgView;
    }



    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "등록되지 않은 지문입니다." ,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(appContext,
                "반갑습니다!",
                Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    imgView.setImageResource(R.drawable.ic_launcher_background);
                    Thread.sleep(300);
                    baseActivity.startActivity(new Intent(baseActivity, MainActivity.class));
                    baseActivity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 300);
    }
}


