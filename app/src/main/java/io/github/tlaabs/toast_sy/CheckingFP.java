package io.github.tlaabs.toast_sy;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


@TargetApi(Build.VERSION_CODES.M)
public class CheckingFP extends AppCompatActivity {

    private static final String TAG = CheckingFP.class.getSimpleName();
    private FingerprintManager manager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private ImageView imageView;


    FingerPrintHandler helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_fp);
        imageView=(ImageView)findViewById(R.id.fingerPrint);

        manager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);

        // KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        generateKey();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cipherInit() ) {
            cryptoObject =
                    new FingerprintManager.CryptoObject(cipher);
            helper = new FingerPrintHandler(this,this,imageView);
            helper.startAuth(manager, cryptoObject);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    //////////////// 하드웨어 back 버튼 누르면 종료.
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        finish();
    }

    //키 이벤트 무시///
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(   (keyCode==KeyEvent.KEYCODE_MENU)
                ||(keyCode==KeyEvent.KEYCODE_DPAD_LEFT)
                ||(keyCode==KeyEvent.KEYCODE_CALL)
                ||(keyCode==KeyEvent.KEYCODE_ENDCALL))
        {
            return true;
        }
        if((keyCode==KeyEvent.KEYCODE_BACK) ||(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT)||(keyCode==KeyEvent.KEYCODE_HOME)||(keyCode==KeyEvent.KEYCODE_DPAD_CENTER))
        {
            setResult(RESULT_CANCELED);
            finish();
        }
        // return super.onKeyDown(keyCode, event);
        setResult(RESULT_CANCELED);
        finish();
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {

            SharedPreferences settings = getSharedPreferences("security", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("securityType","FREE");
            editor.commit();
            Toast.makeText(getApplicationContext(),"지문 인식을 지원하지 않는 디바이스에요.",Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


}