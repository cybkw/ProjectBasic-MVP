package com.bkw.mvp2.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

public class GlobalApplication extends Application {

    private static Context context;
    /** 当前主线程目标handler*/
    private static Handler handler;
    /**当前主线程id*/
    private static int mainThreadId;

    public static Context getContext() {
        return context;

    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler=new Handler();
        mainThreadId= Process.myTid();
    }
}
