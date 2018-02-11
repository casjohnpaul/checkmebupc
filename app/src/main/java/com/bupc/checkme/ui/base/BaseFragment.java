package com.bupc.checkme.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by casjohnpaul on 12/6/2017.
 */

public abstract class BaseFragment extends Fragment {


    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(getParentLayoutId(), container, false);
    }


    protected abstract int getParentLayoutId();

}
