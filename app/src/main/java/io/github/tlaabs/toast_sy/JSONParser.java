package io.github.tlaabs.toast_sy;

import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by devsimMe on 2018-06-03.
 */

public class JSONParser {
    public static String uploadImage(String imageUploadUrl, String sourceImageFile) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

        try {
//            URI sourceURI = new URI(sourceImageFile);
            File sourceFile = new File(sourceImageFile);
            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());
            String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/") + 1);

            // OKHTTP3
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("myfile", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .build();

            Request request = new Request.Builder()
                    .url(imageUploadUrl)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response != null) {
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    return res;
                }
            }
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("TAG", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }


}
