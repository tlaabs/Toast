package com.jihu.toast;

import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


@TargetApi(Build.VERSION_CODES.M)
public class FingeerPrintLogin extends AppCompatActivity {

    private static final String TAG = FingerPrintLogin.class.getSimpleName();
    private FingerprintManager manager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    ActivityFingerprintBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingeer_print_login);


    }
}
