package com.bupc.checkme.ui.levels;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bupc.checkme.R;
import com.bupc.checkme.core.widget.ShadowTransformer;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.levels.adapter.CardFragmentPagerAdapter;

/**
 * Created by casjohnpaul on 1/6/2018.
 */

public class LevelFragment extends BaseFragment {


    ViewPager vpLevels;

    public static LevelFragment newInstance() {

        Bundle args = new Bundle();

        LevelFragment fragment = new LevelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_level;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        vpLevels = (ViewPager) view.findViewById(R.id.vpLevels);

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getChildFragmentManager(), dpToPixels(2, getActivity()));
        ShadowTransformer transformer = new ShadowTransformer(vpLevels, pagerAdapter);
        transformer.enableScaling(true);

        vpLevels.setAdapter(pagerAdapter);
        vpLevels.setPageTransformer(false, transformer);
        vpLevels.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
