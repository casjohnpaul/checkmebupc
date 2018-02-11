package com.bupc.checkme.ui.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupc.checkme.R;
import com.bupc.checkme.core.constant.SharePref;
import com.bupc.checkme.core.database.entity.Question;
import com.bupc.checkme.core.database.handler.DatabaseHandler;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.core.utils.QuizDbHolder;
import com.bupc.checkme.core.widget.FontedTextView;
import com.bupc.checkme.ui.base.BaseFragment;
import com.bupc.checkme.ui.category.CategoryFragment;
import com.bupc.checkme.ui.result.ResultFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by casjohnpaul on 1/6/2018.
 */

public class QuizFragment extends BaseFragment {


    private DatabaseHandler mHandler;

    public static final int VOICE_RECOGNIZER = 101;

    private ImageView btnBack;

    private FontedTextView ftvLevel,  ftvWord;
    private ImageButton ibSpeak;

    // profile manage
    private FontedTextView ftvLifePoints, ftvScoreInformation;
    private ImageView ivAvatar;
    private ProgressBar progress;

    // parent layout source
    private RelativeLayout rlQuiz, rlSpeakTheWord, rlSpeakResult, rlCompleterd;

    // result view
    private ImageView ivSpeakWord, ivCorrectWrong;
    private Button btnClose, btnHome;
    private TextView ftvSpeakWord, ftvWordDescription;

    // question data holder
    private int mQuestionCtr = 0, mScore = 0;
    private List<Question> questions;

    private TextToSpeech ttsWord;
    private MediaPlayer mCorrectSound, mIncorrectSound;

    private String wordToSpeak = "";
    Question question;

    OnMainNavigationObserver onMainNavigationObserver;

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    onMainNavigationObserver.displayFragmentView(CategoryFragment.newInstance());
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        }
    };


    public static QuizFragment newInstance() {

        Bundle args = new Bundle();

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getParentLayoutId() {
        return R.layout.fragment_quiz;
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
        mHandler = new DatabaseHandler(context);
        initializeView(view);
    }

    private void initializeView(View view) {
        mCorrectSound = MediaPlayer.create(context, R.raw.correct_sound);
        mIncorrectSound = MediaPlayer.create(context, R.raw.incorrect);

        ftvLifePoints = view.findViewById(R.id.ftvLifePoints);
        btnBack = view.findViewById(R.id.btnBack);

        rlQuiz = view.findViewById(R.id.rlQuiz);
        rlSpeakTheWord = view.findViewById(R.id.rlSpeakTheWord);
        rlSpeakResult = view.findViewById(R.id.rlSpeakResult);
        rlCompleterd = view.findViewById(R.id.rlCompleterd);
        ftvScoreInformation = view.findViewById(R.id.ftvScoreInformation);

        ftvLevel =  view.findViewById(R.id.ftvTitle);
        ftvWord =  view.findViewById(R.id.ftvWord);
        ibSpeak =  view.findViewById(R.id.ibSpeak);
        ivAvatar =  view.findViewById(R.id.ivAvatar);
        progress = view.findViewById(R.id.progress);

        // for result
        ivSpeakWord = view.findViewById(R.id.ivSpeakWord);
        ivCorrectWrong = view.findViewById(R.id.ivCorrectWrong);
        btnClose = view.findViewById(R.id.btnClose);
        btnHome = view.findViewById(R.id.btnHome);
        ftvSpeakWord = view.findViewById(R.id.ftvSpeakWord);
        ftvWordDescription = view.findViewById(R.id.ftvWordDescription);

        progress.setMax(QuizDbHolder.getInstance().getTotalItems() - 1);

        updateLifePoints();
        updateWord();
        updateCategoryScore();
        initializeClickListener();

        int allWordsAnswer = QuizDbHolder.getInstance().getNumberOfWordsAnswer();
        Timber.e("number of words answer %s", allWordsAnswer);
        if (allWordsAnswer >= 3) {
            showCompletedLayout();
        }

    }


    private void updateLifePoints() {
        Timber.e("life points %s", SharePref.getLifePoints());
        ftvLifePoints.setText(String.valueOf(SharePref.getLifePoints()));
    }

    private void updateWord() {
        mQuestionCtr = SharePref.getQuestionIndex();
        question = QuizDbHolder.getInstance().getQuestion(mQuestionCtr);

        ftvWord.setText(question.getWord());
        wordToSpeak = question.getWord();

        SharePref.setQuestionIndex(mQuestionCtr + 1);
    }

    private void updateCategoryScore() {
        int allAnwerWords = QuizDbHolder.getInstance().getNumberOfWordsAnswer();
        ftvScoreInformation.setText(new StringBuilder().append(allAnwerWords).append("/").append("3").toString());
        updateProgress(mQuestionCtr);
    }

    public void updateProgress(int i) {
        progress.setProgress(i);
    }

    private void initializeClickListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setMessage("Are you sure you want to exit quiz?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMainNavigationObserver.displayFragmentView(CategoryFragment.newInstance());
            }
        });

        ibSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechRecognition();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionDone();
            }
        });

        ivSpeakWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakTheWord();
            }
        });


    }

    private void speakTheWord() {
        ttsWord = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttsWord.speak(question.getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    ttsWord.speak(question.getWord(), TextToSpeech.QUEUE_FLUSH, null);;
                }
            }
        });

    }

    private void promptSpeechRecognition() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the word " + wordToSpeak);

        try {
            startActivityForResult(i, VOICE_RECOGNIZER);
        } catch (Exception e) {
            Snackbar.make(rlQuiz, getString(R.string.device_not_supported), Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VOICE_RECOGNIZER:
                setResult(resultCode == RESULT_OK, data);
                break;
        }
    }


    public void setResult(boolean isResultOk, Intent data) {
        if (isResultOk) {
            showHideResultLayout(true);

            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (question.getWord().equalsIgnoreCase(result.get(0))) {
                ivCorrectWrong.setImageDrawable(context.getDrawable(R.drawable.ic_correct));
                SharePref.setLifePoints(SharePref.getLifePoints() + 5);

                question.setAnswerd(true);

                boolean isUpdated = mHandler.update(question);
                mCorrectSound.start();
                if (isUpdated) {
                    QuizDbHolder.getInstance().setNumberOfWordsAnswer(QuizDbHolder.getInstance().getNumberOfWordsAnswer() + 1);
                } else {
                    Toast.makeText(context, "Update database fail", Toast.LENGTH_SHORT).show();
                }
            } else {
                mIncorrectSound.start();
                ivCorrectWrong.setImageDrawable(context.getDrawable(R.drawable.ic_wrong));
                SharePref.setLifePoints(SharePref.getLifePoints() - 1);
            }

            updateLifePoints();
            updateProgress(progress.getProgress() + 1);
            initializeWordInformation();
        }
    }

    public void initializeWordInformation() {
        ftvSpeakWord.setText(question.getWord());
        ftvWordDescription.setText(question.getDescription());
    }

    public void questionDone() {
        if (mQuestionCtr == QuizDbHolder.getInstance().getTotalItems() - 1 || QuizDbHolder.getInstance().getNumberOfWordsAnswer() == 3) {
            onMainNavigationObserver.displayFragmentView(ResultFragment.newInstance());
        } else {
            onMainNavigationObserver.displayFragmentView(QuizFragment.newInstance());
        }
    }

    public void showHideResultLayout(boolean show) {
        rlSpeakResult.setVisibility(show ? View.VISIBLE : View.GONE);
        rlSpeakTheWord.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showCompletedLayout() {
        rlCompleterd.setVisibility(View.VISIBLE);
        rlSpeakResult.setVisibility(View.GONE);
        rlSpeakTheWord.setVisibility(View.GONE);
    }

}
