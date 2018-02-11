package com.bupc.checkme.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bupc.checkme.R;
import com.bupc.checkme.app.CheckMeApplication;
import com.bupc.checkme.core.adapter.LetterAdapter;
import com.bupc.checkme.core.constant.Difficulty;
import com.bupc.checkme.core.constant.SharePref;
import com.bupc.checkme.core.database.entity.Question;
import com.bupc.checkme.core.database.handler.DatabaseHandler;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.core.model.SimpleString;
import com.bupc.checkme.core.utils.QuizDbHolder;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.base.BaseWithLifeStatusFragment;
import com.bupc.checkme.ui.levels.LevelFragment;
import com.bupc.checkme.ui.quiz.QuizFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by casjohnpaul on 1/6/2018.
 */

public class CategoryFragment extends BaseWithLifeStatusFragment implements LetterAdapter.OnItemClickListener {


    public static final int numberOfColumns = 3;

    ImageView btnBack;

    OnMainNavigationObserver onMainNavigationObserver;
    private DatabaseHandler databaseHandler;

    RelativeLayout rlCategory;

    LetterAdapter adapter;
    RecyclerView rvAlphabet;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainNavigationObserver) {
            onMainNavigationObserver = (OnMainNavigationObserver) context;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewComponents(view);
    }

    private void initializeViewComponents(View view) {
        SharePref.setScore(0);

        btnBack = view.findViewById(R.id.btnBack);

        rlCategory = view.findViewById(R.id.rlCategory);

        databaseHandler = new DatabaseHandler(CheckMeApplication.getAppContext());
        databaseHandler.getReadableDatabase();

        File database = getContext().getDatabasePath(databaseHandler.DATABASE_NAME);
        if (!database.exists()) {
            databaseHandler.getReadableDatabase();
        }

        adapter = new LetterAdapter(getContext(), letters(), this);

        rvAlphabet = (RecyclerView) view.findViewById(R.id.rvAlphabet);
        rvAlphabet.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        rvAlphabet.setAdapter(adapter);

        initBackButton();
    }

    private void initBackButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMainNavigationObserver.finishActivity();
            }
        });
    }

    public List<SimpleString> letters() {
        List<SimpleString> letters = new ArrayList<>();
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for (int i = 0; i < alphabet.length; i++) {
            letters.add(new SimpleString(alphabet[i]));
        }

        return letters;
    }

    @Override
    public void onItemClick(int position) {
        setQuestion(letters().get(position).toString());
    }

    public void setQuestion(String group) {
        String difficultyLevel = SharePref.getDifficultyLevel();
        Timber.e("difficulty level %s", difficultyLevel);

        SharePref.setQuestionIndex(0);

        List<Question> questions = new ArrayList<>();
        List<Question> allQuestionByCategory = new ArrayList<>();
        List<Question> allAnswerQuestionsByCagegory = new ArrayList<>();

        QuizDbHolder.getInstance().setNumberOfWordsAnswerToZero();

        List<Question> allQuestions = databaseHandler.getQuestions();

        for (Question question : allQuestions) {
            if (group.equalsIgnoreCase(question.getGroup()) && difficultyLevel.equalsIgnoreCase(question.getLvl())) {

                allQuestionByCategory.add(question);

                if (question.isAnswerd()) {
                    allAnswerQuestionsByCagegory.add(question);
                } else {
                    questions.add(question);
                }
            }
        }

        if (questions.size() > 0) {
            Collections.shuffle(questions);
            QuizDbHolder.getInstance().setQuestions(questions);
            QuizDbHolder.getInstance().setAllWords(allQuestionByCategory);
            QuizDbHolder.getInstance().setAnswerWords(allAnswerQuestionsByCagegory);

            onMainNavigationObserver.displayFragmentView(QuizFragment.newInstance());
        } else {
            Snackbar.make(rlCategory, "Sorry no word in category " + group, Snackbar.LENGTH_SHORT).show();
        }
    }

}
