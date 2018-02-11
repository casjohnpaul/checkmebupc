package com.bupc.checkme.core.database;

import android.database.Cursor;

/**
 * Created by casjohnpaul on 8/12/2017.
 */

public class Db {

    public static int getIntValue(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static String getStringValue(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }
}
