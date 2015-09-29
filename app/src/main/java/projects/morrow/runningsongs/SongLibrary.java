package projects.morrow.runningsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import projects.morrow.runningsongs.SongDbSchema.SongTable;

/**
 * Created by anne on 9/23/15.
 */
public class SongLibrary {
    private static SongLibrary sSongLibrary;

    public static final String TAG = "SongLibrary";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private MediaPlayer mMediaPlayer;


    public static SongLibrary get(Context context) {
        if (sSongLibrary == null) {
            sSongLibrary = new SongLibrary(context);
        }
        return sSongLibrary;
    }

    private SongLibrary(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new SongBaseHelper(mContext).getWritableDatabase();
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();

        SongCursorWrapper cursor = querySongs(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                songs.add(cursor.getSong());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return songs;
    }

    public Song getSong(UUID uuid) {
        SongCursorWrapper cursor = querySongs(
                SongTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSong();
        } finally {
            cursor.close();
        }
    }

    public Song getSong(String title, String artist, String album) {
        SongCursorWrapper cursor = querySongs(
                SongTable.Cols.TITLE + " = ? AND " +
                        SongTable.Cols.ARTIST + " = ? AND " +
                        SongTable.Cols.ALBUM + " = ?",
                new String[] { title, artist, album }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSong();
        } finally {
            cursor.close();
        }
    }

    public void addSong(Song s) {
        ContentValues values = getContentValues(s);
        mDatabase.insert(SongTable.NAME, null, values);
    }

    public void updateSong(Song song) {
        String uuidString = song.getID().toString();
        ContentValues values = getContentValues(song);

        mDatabase.update(SongTable.NAME, values,
                SongTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Song song) {
        ContentValues values = new ContentValues();
        values.put(SongTable.Cols.UUID, song.getID().toString());
        values.put(SongTable.Cols.PATH, song.getPath());
        values.put(SongTable.Cols.TITLE, song.getTitle());
        values.put(SongTable.Cols.ARTIST, song.getArtist());
        values.put(SongTable.Cols.ALBUM, song.getAlbum());
        values.put(SongTable.Cols.DURATION, song.getDuration());
        values.put(SongTable.Cols.BPM, song.getBPM());
        values.put(SongTable.Cols.USE, song.isUsedInApp() ? 1 : 0);

        return values;
    }

    private SongCursorWrapper querySongs(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SongTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, //having
                null // orderBy
        );

        return new SongCursorWrapper(cursor);
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void switchTo(Song song){
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(song.getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer == null) {
            return false;
        } else {
            return mMediaPlayer.isPlaying();
        }
    }



    public void findSongs(Context context) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);


        while(cursor.moveToNext()) {
            Song foundSong = new Song();
            foundSong.setTitle(cursor.getString(2));
            foundSong.setAlbum(cursor.getString(4));
            foundSong.setArtist(cursor.getString(1));
            foundSong.setPath(cursor.getString(3));
            foundSong.setDuration(cursor.getInt(5));
            Log.d(TAG, foundSong.getTitle());
            Log.d(TAG, foundSong.getPath());

            Song oldSong = getSong(foundSong.getTitle(), foundSong.getArtist(), foundSong.getAlbum());
            if (oldSong != null) {
                oldSong.setPath(foundSong.getPath());
                updateSong(oldSong);
            } else {
                addSong(foundSong);
            }

        }


    }

}
