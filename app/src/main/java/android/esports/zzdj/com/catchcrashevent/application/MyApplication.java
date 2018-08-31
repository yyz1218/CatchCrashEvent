package android.esports.zzdj.com.catchcrashevent.application;

import android.app.Application;
import android.esports.zzdj.com.catchcrashevent.handler.CrashHandler;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //全局Crash
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }
}
