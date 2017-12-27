package app.codekiller.com.newsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lollipop on 2017/12/21.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getInstance(Context context){
        if (databaseHelper == null){
            return new DatabaseHelper(context, "news", null, 1);
        }

        return databaseHelper;
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists Zhihu("
                + "id integer primary key autoincrement,"
                + "zhihu_id integer not null,"
                + "zhihu_news text,"
                + "zhihu_time real,"
                + "zhihu_content text)");
        sqLiteDatabase.execSQL("alter table Zhihu add column favorite integer default 0");

        sqLiteDatabase.execSQL("create table if not exists Guokr("
                + "id integer primary key autoincrement,"
                + "guokr_id integer not null,"
                + "guokr_news text,"
                + "guokr_content text,"
                + "favorite integer default 0)");

        sqLiteDatabase.execSQL("create table if not exists Douban("
                + "id integer primary key autoincrement,"
                + "douban_id integer not null,"
                + "douban_news text,"
                + "douban_content text,"
                + "favorite integer default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
