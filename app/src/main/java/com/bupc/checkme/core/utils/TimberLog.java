package com.bupc.checkme.core.utils;

import com.bupc.checkme.BuildConfig;

import timber.log.Timber;

/**
 * Created by casjohnpaul on 8/12/2017.
 */

public class TimberLog {

    public static final void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element)
                            + "/"
                            + element.getMethodName()
                            + ":"
                            + element.getLineNumber();
                }
            });
        }
    }
}
