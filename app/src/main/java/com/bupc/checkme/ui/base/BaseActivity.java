package com.bupc.checkme.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by casjohnpaul on 12/6/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

}
