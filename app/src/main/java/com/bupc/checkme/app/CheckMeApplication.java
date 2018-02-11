package com.bupc.checkme.app;

import android.app.Application;
import android.content.Context;

import com.bupc.checkme.BuildConfig;
import com.bupc.checkme.core.utils.TimberLog;

import timber.log.Timber;

/**
 * Created by casjohnpaul on 8/12/2017.
 */

public class CheckMeApplication extends Application {

    private static CheckMeApplication instance;
    private static Context context;


    public static synchronized CheckMeApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        TimberLog.init();
    }

    public static Context getAppContext() {
        return context;
    }

}
