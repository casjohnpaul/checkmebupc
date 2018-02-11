package com.bupc.checkme.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bupc.checkme.R;
import com.bupc.checkme.core.constant.SharePref;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.core.utils.QuizDbHolder;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.levels.LevelFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    OnMainNavigationObserver navigationObserver;


    public HomeFragment() {

    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainNavigationObserver) {
            navigationObserver = (OnMainNavigationObserver) context;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeComponents();
    }

    private void initializeComponents() {
        SharePref.setQuestionIndex(0);
        if (QuizDbHolder.getInstance() != null) {
            QuizDbHolder.getInstance().setNumberOfWordsAnswer(0);
        }
    }

    private void initializeView(View view) {
        view.findViewById(R.id.btnPlayGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationObserver.displayFragmentView(LevelFragment.newInstance());
            }
        });
    }

}
