package projects.morrow.runningsongs;

/**
 * Created by anne on 9/29/15.
 */
public class SongDbSchema {
    public static final class SongTable {
        public static final String NAME = "songs";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PATH = "path";
            public static final String TITLE = "title";
            public static final String ARTIST = "artist";
            public static final String ALBUM = "album";
            public static final String DURATION = "duration";
            public static final String BPM = "bpm";
            public static final String USE = "use";  // boolean
        }
    }
}