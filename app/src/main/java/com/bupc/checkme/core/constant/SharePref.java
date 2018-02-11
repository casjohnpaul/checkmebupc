package com.bupc.checkme.core.constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.bupc.checkme.app.CheckMeApplication;

/**
 * Created by casjohnpaul on 1/5/2018.
 */

public class SharePref {

    public static final String SHARED_PREF = "shared_pref";

    public static final String PREF_LIFE_POINTS = "life_points";
    public static final String PREF_SCORE = "score";
    public static final String PREF_SOUNDS_SETTINGS = "sounds_settings";
    public static final String PREF_DIFFICULTY = "diffeculty";

    public static final String PREF_EASY = "easy";
    public static final String PREF_MEDIUM = "medium";
    public static final String PREF_HARD = "hard";

    public static final String PREF_QUESTION_CTR = "question_counter";


    private static final SharedPreferences sharePrefInstance = CheckMeApplication.getInstance().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    private static final SharedPreferences.Editor editor = sharePrefInstance.edit();

    public static void setLifePoints(int lifePoints) {
        editor.putInt(PREF_LIFE_POINTS, lifePoints);
        editor.commit();
    }

    public static void setScore(int score) {
        editor.putInt(PREF_SCORE, score);
        editor.commit();
    }

    public static void setSoundStatus(boolean active) {
        editor.putBoolean(PREF_SOUNDS_SETTINGS, active);
        editor.commit();
    }

    public static void setDifficulty(String diffeculty) {
        editor.putString(PREF_DIFFICULTY, diffeculty);
        editor.commit();
    }

    public static void setQuestionIndex(int ctr) {
        editor.putInt(PREF_QUESTION_CTR, ctr);
        editor.commit();
    }

    public static void setLevelCompleted(String keyLevel, int completed) {
        editor.putInt(keyLevel, completed);
        editor.commit();
    }

    public static int getLifePoints() {
        return sharePrefInstance.getInt(PREF_LIFE_POINTS, 3);
    }

    public static int getScore() {
        return sharePrefInstance.getInt(PREF_SCORE, 0);
    }

    public static int getLevelCompletedCount(String key) {
        return sharePrefInstance.getInt(key, 0);
    }

    public static boolean isSoundActive() {
        return sharePrefInstance.getBoolean(PREF_SOUNDS_SETTINGS, false);
    }

    public static String getDifficultyLevel() {
        return sharePrefInstance.getString(PREF_DIFFICULTY, Difficulty.EASY);
    }

    public static int getQuestionIndex() {
        return sharePrefInstance.getInt(PREF_QUESTION_CTR, 0);
    }

}
