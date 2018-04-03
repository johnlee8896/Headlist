package org.cap.exercise.headlist.utils;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by liweifeng on 29/03/2018.
 */

public class CommonMethod {
    public static String readLocalJson(Context context, String fileName) {
        String resultString = "";
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
