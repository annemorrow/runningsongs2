package projects.morrow.runningsongs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projects.morrow.runningsongs.SongDbSchema.SongTable;

/**
 * Created by anne on 9/29/15.
 */
public class SongBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "songBase.db";

    public SongBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SongTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SongTable.Cols.UUID + ", " +
                SongTable.Cols.PATH + ", " +
                SongTable.Cols.TITLE + ", " +
                SongTable.Cols.ARTIST + ", " +
                SongTable.Cols.ALBUM + ", " +
                SongTable.Cols.DURATION + ", " +
                SongTable.Cols.BPM + ", " +
                SongTable.Cols.USE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
