package com.yougen.anticafemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by thymomenosgata on 09.12.17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "posts";

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_H = "hours";
    public final static String COLUMN_M = "minutes";
    public final static String COLUMN_TABLES = "ttable";
    public final static String COLUMN_COUNT = "count";
    public final static String COLUMN_HOOKAH = "hookah";
    public final static String COLUMN_HOLL = "holl";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    //Имя файла базы данных
    public static final String DATABASE_NAME = "post.db";

    //Версия базы данных. При изменении схемы увеличить на единицу
    public static final int DATABASE_VERSION = 3;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
                db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_H + " INTEGER, "
                + COLUMN_M + " INTEGER, "
                + COLUMN_TABLES + " INTEGER, "
                + COLUMN_COUNT + " INTEGER, "
                + COLUMN_HOOKAH + " TEXT default null, "
                + COLUMN_HOLL + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем старую таблицу и создаём новую
        db.execSQL(SQL_DELETE_ENTRIES);
        // Создаём новую таблицу
        onCreate(db);
    }
}
