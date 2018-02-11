package com.bupc.checkme.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bupc.checkme.R;
import com.bupc.checkme.core.constant.SharePref;

/**
 * Created by casjohnpaul on 2/9/2018.
 */

public abstract class BaseWithLifeStatusFragment extends BaseFragment {

    TextView ftvLifePoints;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLifePoints(view);
    }

    protected void initLifePoints(View view) {
        ftvLifePoints = view.findViewById(R.id.ftvLifePoints);
        ftvLifePoints.setText(String.valueOf(SharePref.getLifePoints()));
    }

}
