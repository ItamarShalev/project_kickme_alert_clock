package alert.kickme.com.kickme_alert_clock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import alert.kickme.com.kickme_alert_clock.data.CharacterData;


public class DaoCharacterDatabase extends BaseSqlite.BaseDao<CharacterData, DaoCharacterDatabase.CharacterDataSqlite> {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alerts.db";
    private static final String TABLE_NAME = "alerts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PATH_ICON = "icon";
    private static final String COLUMN_PATH_GIF = "gif";
    private static final String COLUMN_PATH_SOUND = "sound";

    public DaoCharacterDatabase(Context context) {
        super(new CharacterDataSqlite(context));
    }

    static class CharacterDataSqlite extends BaseSqlite<CharacterData> {

        CharacterDataSqlite(Context context) {
            super(context, new BaseSqliteInfo(VERSION, DATABASE_NAME, TABLE_NAME, COLUMN_ID));
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PATH_ICON + " TEXT, " +
                    COLUMN_PATH_GIF + " TEXT, " +
                    COLUMN_PATH_SOUND + " TEXT);");
        }

        @Override
        protected ContentValues insertItemToContentValues(CharacterData item) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, item.getPrimaryId());
            contentValues.put(COLUMN_NAME, item.getName());
            contentValues.put(COLUMN_PATH_ICON, item.getIconImage().getAbsolutePath());
            contentValues.put(COLUMN_PATH_GIF, item.getGifImage().getAbsolutePath());
            contentValues.put(COLUMN_PATH_SOUND, item.getSoundGif().getAbsolutePath());
            return contentValues;
        }

        @Override
        protected CharacterData getItemFromCursor(Cursor cursor) {
            int id = getInt(COLUMN_ID);
            String name = getString(COLUMN_NAME);
            File iconImage = new File(getString(COLUMN_PATH_ICON));
            String pathGifImage = getString(COLUMN_PATH_GIF);

            File gifImage = null, soundGif = null;
            if (pathGifImage != null) {
                gifImage = new File(pathGifImage);
            }
            String pathSoundGif = getString(COLUMN_PATH_SOUND);
            if (pathSoundGif != null) {
                soundGif = new File(pathSoundGif);
            }

            return new CharacterData(id, name, iconImage, gifImage, soundGif);
        }
    }

}
