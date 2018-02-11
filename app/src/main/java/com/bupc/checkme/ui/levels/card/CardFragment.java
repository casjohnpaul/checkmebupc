package com.bupc.checkme.ui.levels.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bupc.checkme.R;
import com.bupc.checkme.app.CheckMeApplication;
import com.bupc.checkme.core.constant.AppConstant;
import com.bupc.checkme.core.constant.Difficulty;
import com.bupc.checkme.core.constant.SharePref;
import com.bupc.checkme.core.database.handler.DatabaseHandler;
import com.bupc.checkme.core.interfacez.CardAdapter;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.core.widget.FontedTextView;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.category.CategoryFragment;
import com.bupc.checkme.ui.main.MainActivity;

import java.io.File;

import timber.log.Timber;

/**
 * Created by casjohnpaul on 1/6/2018.
 */

public class CardFragment extends BaseFragment {

    CardView cardView;
    FontedTextView ftvDifficulty;
    ImageButton btnPlay;

    int difficulty = -1;

    OnMainNavigationObserver onNavigationFragmentObservable;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (difficulty) {
                case 0:
                    SharePref.setDifficulty(Difficulty.EASY);
                    startMainCategoryActivity();
//                    onNavigationFragmentObservable.displayFragmentView(CategoryFragment.newInstance());
                    break;
                case 1:
                    SharePref.setDifficulty(Difficulty.MEDIUM);
                    int levelCompletedCount = SharePref.getLevelCompletedCount(Difficulty.EASY);
                    if (levelCompletedCount >= AppConstant.TOTAL_LETTER_TO_BE_COMPLETED) {
                        startMainCategoryActivity();
                    } else {
                        Toast.makeText(context, "Sorry this is currently lock", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    SharePref.setDifficulty(Difficulty.HARD);
                    int levelCompletedInMedium = SharePref.getLevelCompletedCount(Difficulty.MEDIUM);
                    if (levelCompletedInMedium == AppConstant.TOTAL_LETTER_TO_BE_COMPLETED) {
                        startMainCategoryActivity();
                    } else {
                        Toast.makeText(context, "Sorry this is currently lock", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(context, "sorry difficulty level is currently initializing", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public static Fragment getInstance(int position) {
        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);

        return f;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.item_card;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainNavigationObserver) {
            onNavigationFragmentObservable = (OnMainNavigationObserver) context;
        }
    }

    public void startMainCategoryActivity() {
        startActivity(new Intent(context, MainActivity.class));
    }

    private void initializeView(View view) {

        cardView = (CardView) view.findViewById(R.id.cardView);
        ftvDifficulty = (FontedTextView) view.findViewById(R.id.ftvDifficulty);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);

        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        difficulty = getArguments().getInt("position", 0);

        btnPlay.setOnClickListener(onClickListener);

        switch (difficulty) {
            case 0:
                ftvDifficulty.setText("Easy");
                btnPlay.setImageDrawable(getContext().getDrawable(R.drawable.ic_start));
                break;
            case  1:
                ftvDifficulty.setText("Medium");
                int levelCompletedCount = SharePref.getLevelCompletedCount(SharePref.PREF_EASY);
                if (levelCompletedCount >= AppConstant.TOTAL_LETTER_TO_BE_COMPLETED) {
                    btnPlay.setImageDrawable(getContext().getDrawable(R.drawable.ic_start));
                } else {
                    btnPlay.setImageDrawable(getContext().getDrawable(R.drawable.ic_lock));
                }
                break;
            case 2:
                ftvDifficulty.setText("Hard");
                int levelCompletedInMedium = SharePref.getLevelCompletedCount(SharePref.PREF_MEDIUM);
                if (levelCompletedInMedium == AppConstant.TOTAL_LETTER_TO_BE_COMPLETED) {
                    btnPlay.setImageDrawable(getContext().getDrawable(R.drawable.ic_start));
                } else {
                    btnPlay.setImageDrawable(getContext().getDrawable(R.drawable.ic_lock));
                }
                break;
        }
    }

    public CardView getCardView() {
        return cardView;
    }

}
