package com.bupc.checkme.core.interfacez;

import android.support.v7.widget.CardView;

/**
 * Created by casjohnpaul on 1/6/2018.
 */

public interface CardAdapter {

    public final int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}