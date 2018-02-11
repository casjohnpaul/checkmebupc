package com.bupc.checkme.core.interfacez;

import android.support.v4.app.Fragment;

/**
 * Created by casjohnpaul on 1/5/2018.
 */

public interface OnMainNavigationObserver {

    public void displayFragmentView(Fragment fragment);

    void finishActivity();
}
