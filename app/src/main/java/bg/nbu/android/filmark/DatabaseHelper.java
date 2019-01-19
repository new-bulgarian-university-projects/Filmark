package bg.nbu.android.filmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Filmarks.db";
    public static final String TABLE_NAME = "film_bookmarks";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "imdbID";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "YEAR";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, imdbID TEXT unique, TITLE TEXT, YEAR TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, movie.getId());
        contentValues.put(COL_3, movie.getTitle());
        contentValues.put(COL_4, movie.getYear());

        long resultId = db.insert(TABLE_NAME, null, contentValues);

        if(resultId == -1)
            return false;
        else
            return true;
    }

    public Cursor getBookmarkedMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
