package projects.morrow.runningsongs;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by anne on 9/23/15.
 */
public class SongLibrary {
    private static SongLibrary sSongLibrary;

    public static final String TAG = "SongLibrary";

    private List<Song> mSongs; // ones the user has chosen to be part of the running app
    private List<Song> mAllSongs; // songs found on the device (that the user may then choose to be in the running app)
    private MediaPlayer mMediaPlayer;


    public static SongLibrary get(Context context) {
        if (sSongLibrary == null) {
            sSongLibrary = new SongLibrary(context);
        }
        sSongLibrary.mAllSongs = new ArrayList<>();
        return sSongLibrary;
    }

    private SongLibrary(Context context) {
        mSongs = new ArrayList<>();
    }

    public List<Song> getSongs() {
        return mAllSongs;
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
            if (songInList(foundSong)) {
                foundSong.setUseInApp(true);
            }
            mAllSongs.add(foundSong);
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

    public Song getSong(List<Song> list, UUID uuid) {
        for (Song s : list) {
            if (s.getID().equals(uuid)) {
                return s;
            }
        }
        return null;
    }
}
