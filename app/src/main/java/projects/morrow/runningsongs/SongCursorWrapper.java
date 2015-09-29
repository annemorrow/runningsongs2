package projects.morrow.runningsongs;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

/**
 * Created by anne on 9/29/15.
 */
public class SongCursorWrapper extends CursorWrapper {
    public SongCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Song getSong() {
        String uuidString = getString(getColumnIndex(SongDbSchema.SongTable.Cols.UUID));
        String path = getString(getColumnIndex(SongDbSchema.SongTable.Cols.PATH));
        String title = getString(getColumnIndex(SongDbSchema.SongTable.Cols.TITLE));
        String artist = getString(getColumnIndex(SongDbSchema.SongTable.Cols.ARTIST));
        String album = getString(getColumnIndex(SongDbSchema.SongTable.Cols.ALBUM));
        Double duration = getDouble(getColumnIndex(SongDbSchema.SongTable.Cols.DURATION));
        int bpm = getInt(getColumnIndex(SongDbSchema.SongTable.Cols.BPM));
        int use = getInt(getColumnIndex(SongDbSchema.SongTable.Cols.USE));

        Song song = new Song(UUID.fromString(uuidString));
        song.setPath(path);
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setDuration(duration);
        song.setBPM(bpm);
        song.setUseInApp(use != 0);

        return song;
    }
}
