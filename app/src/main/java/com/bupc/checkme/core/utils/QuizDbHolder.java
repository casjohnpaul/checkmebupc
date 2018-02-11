package com.bupc.checkme.core.utils;

import com.bupc.checkme.core.database.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by casjohnpaul on 1/11/2018.
 */

public class QuizDbHolder {

    private static final QuizDbHolder instance = new QuizDbHolder();

    private List<Question> mQuestions = new ArrayList<>();
    private List<Question> allWords = new ArrayList<>();
    private List<Question> answerWords = new ArrayList<>();

    private int numberOfWordsAnswer = 0;

    public static synchronized QuizDbHolder getInstance() {
        return instance;
    }

    /**
     *
     * Words adder
     */

    public void addQuestions(List<Question> questions) {
        mQuestions.addAll(questions);
    }

    public void addAllWords(List<Question> words) {
        allWords.addAll(words);
    }

    public void addAnswerWords(List<Question> words) {
        answerWords.addAll(words);
    }


    /**
     *
     * Getter of words by index
     */

    public Question getQuestion(int index) {
        return mQuestions.get(index);
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    /**
     *
     * Setter of questions
     */

    public void setQuestions(List<Question> questions) {
        mQuestions.clear();
        addQuestions(questions);
    }

    public void setAllWords(List<Question> words) {
        allWords.clear();
        addAllWords(words);
    }

    public void setAnswerWords(List<Question> words) {
        answerWords.clear();
        addAnswerWords(words);
    }

    public void setNumberOfWordsAnswerToZero() {
        numberOfWordsAnswer = 0;
    }

    public void setNumberOfWordsAnswer(int wordsAnswer) {
        numberOfWordsAnswer = getAllAnwerWords() + wordsAnswer;
    }

    /**
     *
     * Size for each words store here
     */

    public int getTotalItems() {
        return mQuestions.size();
    }

    public int getAllWordSize() {
        return allWords.size();
    }

    public int getAllAnwerWords() {
        return answerWords.size();
    }

    public int getNumberOfWordsAnswer() {
        return answerWords.size() + numberOfWordsAnswer;
    }
}
