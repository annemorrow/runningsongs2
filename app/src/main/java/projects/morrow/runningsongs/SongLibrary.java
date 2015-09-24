package projects.morrow.runningsongs;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 9/23/15.
 */
public class SongLibrary {
    private List<Song> mSongs;
    private MediaPlayer mMediaPlayer;



    public void play(Song song){
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(song.getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
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
            if (!songInList(foundSong)) {
                mSongs.add(foundSong);
            }
        }
    }

    private boolean songInList(Song song) {
        for (Song s : mSongs) {
            if (s.getTitle().equals(song.getTitle()) && s.getAlbum().equals(song.getAlbum())) {
                return true;
            }
        }
        return false;
    }
}
