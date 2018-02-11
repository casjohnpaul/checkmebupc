package com.bupc.checkme.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bupc.checkme.R;
import com.bupc.checkme.ui.base.BaseFragment;

/**
 * Created by casjohnpaul on 2/9/2018.
 */

public class AboutFragment extends BaseFragment {


    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
