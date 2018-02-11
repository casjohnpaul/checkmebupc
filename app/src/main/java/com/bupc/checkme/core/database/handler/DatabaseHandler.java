package com.bupc.checkme.core.database.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.bupc.checkme.app.CheckMeApplication;
import com.bupc.checkme.core.database.Db;
import com.bupc.checkme.core.database.entity.Question;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.content.ContentValues.TAG;

/**
 * Created by casjohnpaul on 1/4/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private final String TAG = DatabaseHandler.class.getSimpleName();

    private static DatabaseHandler instance = new DatabaseHandler(CheckMeApplication.getAppContext().getApplicationContext());

    public static final String DATABASE_NAME = "checkme.sqlite";
    public static final String DATABASE_LOCATION = "/data/data/com.bupc.checkme/databases/";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private boolean mNewDatabase = false;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static synchronized DatabaseHandler getHandler(Context context) {
        return instance;
    }

    public void initializeDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            getWritableDatabase();

            if (mNewDatabase) {
                try {
                    copyDatabase();
                } catch (IOException e) {
                    throw new Error("Error copying preloaded database");
                }
            }
        }
    }

    public void copyDatabase() throws IOException {
        try {
            InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
            String outFilename = DATABASE_LOCATION + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFilename);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.i(TAG, "DB Copied");
            Toast.makeText(mContext, "INITIALIZATION SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(mContext, "INITIALIZATION FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
//        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void openDatabaseWritableDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }

        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.i("new database is true");
        mNewDatabase = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            Timber.i("new and old version %s = %s", newVersion, oldVersion);
            mNewDatabase = true;
        }
    }


    /**
     *
     * @return
     * Get all the questions
     */
    public List<Question> getQuestions() {
        Timber.e("getting all questions");

        Question quiz = null;
        List<Question> questions = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + Question.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                quiz = new Question.Builder()
                        .id(Db.getIntValue(cursor, Question.ID))
                        .word(Db.getStringValue(cursor, Question.WORD))
                        .description(Db.getStringValue(cursor, Question.DESCRIPTION))
                        .lvl(Db.getStringValue(cursor, Question.LEVEL))
                        .group(Db.getStringValue(cursor, Question.GROUP))
                        .isAnswerd(Db.getIntValue(cursor, Question.ANSWER) == 1)
                        .build();

                questions.add(quiz);
            } while (cursor.moveToNext());
        }

        cursor.close();
        closeDatabase();

        return questions;
    }

    public int getTotalNumberOfAnswerInCategory(String category) {
        Timber.e("getting all questions");

        Question quiz = null;
        List<Question> questions = new ArrayList<>();
        openDatabase();

        String query = new StringBuilder("SELECT COUNT (*) FROM ")
                .append(Question.TABLE_NAME)
                .append(" WHERE ")
                .append(Question.GROUP)
                .append(" = '")
                .append(category)
                .append("' AND ")
                .append(Question.ANSWER)
                .append("=")
                .append(1)
                .toString();

        Cursor cursor = mDatabase.rawQuery(query, null);
        int count = 0;

        if (cursor != null) {
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        closeDatabase();

        return count;
    }

    /**
     * <p>
     *     Update the data from the list, if successfull return true otherwise false
     * </p>
     * @param question
     * The question to be updated
     * @return
     * True if successfully updated else false
     */
    public boolean update(Question question) {
        openDatabaseWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Question.ANSWER, question.getAnswer());

        int update = mDatabase.update(Question.TABLE_NAME, values, Question.ID + " = ?", new String[]{String.valueOf(question.getId())});
        return update > -1;
    }

}
