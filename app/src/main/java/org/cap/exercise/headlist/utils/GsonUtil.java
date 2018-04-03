package org.cap.exercise.headlist.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStream;

/**
 * 封装的是使用Gson解析json的方法
 */
public class GsonUtil {

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }




}
