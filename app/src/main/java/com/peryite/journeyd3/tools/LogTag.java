package com.peryite.journeyd3.tools;


import android.database.Cursor;
import android.util.Log;

public class LogTag {
    public final static String CLICK = "CLICK";
    public final static String ERROR = "error";
    public final static String RESULT = "result";
    public final static String CHAPTER_TABLE = "chapter_table";
    public final static String CHAPTER_TASK_TABLE = "chapter_task_table";
    public final static String CURSOR = "cursor";

    public static void printLogArray(String tag, String[] array) {
        for (int i = 0; i < array.length; i++) {
            Log.d(tag, array[i] + "\n" + "---------------------------");
        }
    }

    public static void logCursor(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String object;
                do {
                    object = "";
                    for (String string : cursor.getColumnNames()) {
                        object = object.concat(string + ": " + cursor.getString(cursor.getColumnIndex(string)) + "; ");
                    }
                    Log.d(LogTag.CURSOR, object);
                } while (cursor.moveToNext());
            }
        } else {
            Log.d(LogTag.CURSOR, "Cursor is null!");
        }
    }
}
