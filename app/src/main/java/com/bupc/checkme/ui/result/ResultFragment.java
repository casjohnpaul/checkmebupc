package com.bupc.checkme.ui.result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bupc.checkme.R;
import com.bupc.checkme.core.constant.SharePref;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.core.utils.QuizDbHolder;
import com.bupc.checkme.core.widget.FontedTextView;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.base.BaseWithLifeStatusFragment;
import com.bupc.checkme.ui.home.HomeFragment;

/**
 * Created by casjohnpaul on 1/28/2018.
 */

public class ResultFragment extends BaseWithLifeStatusFragment {


    OnMainNavigationObserver observer;

    FontedTextView ftvScore, ftvStatus;
    Button btnHome;
    ImageView btnBack;

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_result;
    }


    public static ResultFragment newInstance() {

        Bundle args = new Bundle();

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewComponents(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        observer = (OnMainNavigationObserver) context;
    }

    private void initializeViewComponents(View view) {
        ftvScore = view.findViewById(R.id.ftvScore);
        ftvStatus = view.findViewById(R.id.ftvStatus);

        btnHome = view.findViewById(R.id.btnHome);
        btnBack = view.findViewById(R.id.btnBack);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer.finishActivity();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.performClick();
            }
        });

        updateScreAndStatus();
    }

    private void updateScreAndStatus() {
        int numberOfWordsAnswer = QuizDbHolder.getInstance().getNumberOfWordsAnswer();
        if (numberOfWordsAnswer >= 3) {
            ftvScore.setText("3/3");
            ftvStatus.setText("Completed");
            String key = SharePref.getDifficultyLevel();
            SharePref.setLevelCompleted(key, SharePref.getLevelCompletedCount(key) + 1);
        } else {
            ftvStatus.setText("Try again");
        }
    }

}
