package alert.kickme.com.kickme_alert_clock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import alert.kickme.com.kickme_alert_clock.data.AlertData;


public class DaoAlertDatabase extends BaseSqlite.BaseDao<AlertData, DaoAlertDatabase.AlertSqlite> {
    public static final String COLUMN_CHARACTER_ID = "characterId";
    public static final String COLUMN_LEVEL_VOLUME = "levelVolume";
    public static final String COLUMN_IS_ENABLE_VIBRATION = "isEnableVibration";
    public static final String COLUMN_IS_SNOOZE = "isSnooze";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alerts.db";
    private static final String TABLE_NAME = "alerts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    private static final String COLUMN_IS_ENABLE_ALERT = "isEnableAlert";
    private static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TYPE_CLOCK = "type";



    private final FileDB fileDB;

    public DaoAlertDatabase(Context context) {
        super(new AlertSqlite(context));
        fileDB = new FileDB(context);
    }

    @Override
    public void insert(AlertData... items) {
        super.insert(items);
        for (AlertData item : items) {
            saveArrayDay(item);
            saveArrayDate(item);
        }

    }


    public void saveArrayDate(AlertData alertData) {
        saveArrayLong(alertData.getPrimaryId(), FileDB.Type.ARRAY_DAY_MILLS, alertData.getDaysOfWeek());
    }

    public void saveArrayDay(AlertData alertData) {
        saveArrayLong(alertData.getPrimaryId(), FileDB.Type.ARRAY_DATE_MILLS, alertData.getDateToWake().toArray(new Long[]{}));
    }

    private void saveArrayLong(int idAlertData, FileDB.Type type, Long[] longs) {
        fileDB.saveArrayLong(idAlertData, type, longs);
    }


    static class AlertSqlite extends BaseSqlite<AlertData> {

        private final FileDB fileDB;

        AlertSqlite(Context context) {
            super(context, new BaseSqliteInfo(VERSION, DATABASE_NAME, TABLE_NAME, COLUMN_ID));
            fileDB = new FileDB(context);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CHARACTER_ID + " INTEGER, " +
                    COLUMN_LEVEL_VOLUME + " INTEGER, " +
                    COLUMN_HOUR + " INTEGER, " +
                    COLUMN_MINUTE + " INTEGER, " +
                    COLUMN_IS_ENABLE_ALERT + " INTEGER, " +
                    COLUMN_IS_ENABLE_VIBRATION + " INTEGER, " +
                    COLUMN_IS_SNOOZE + " INTEGER, " +
                    COLUMN_NOTE + " TEXT, " +
                    COLUMN_TYPE_CLOCK + " TEXT);");

        }

        @Override
        protected ContentValues insertItemToContentValues(AlertData item) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, item.getPrimaryId());
            contentValues.put(COLUMN_CHARACTER_ID, item.getCharacterId());
            contentValues.put(COLUMN_LEVEL_VOLUME, item.getLevelVolume());
            contentValues.put(COLUMN_HOUR, item.getHour());
            contentValues.put(COLUMN_MINUTE, item.getMinute());
            contentValues.put(COLUMN_IS_ENABLE_ALERT, booleanToInt(item.isEnableAlert()));
            contentValues.put(COLUMN_IS_ENABLE_VIBRATION, booleanToInt(item.isEnableVibration()));
            contentValues.put(COLUMN_IS_SNOOZE, booleanToInt(item.isSnooze()));
            contentValues.put(COLUMN_NOTE, item.getNote());
            contentValues.put(COLUMN_TYPE_CLOCK, item.getTypeClock().name());
            return contentValues;
        }

        @Override
        protected AlertData getItemFromCursor(Cursor cursor) {
            int id, characterId, levelVolume, hour, minute;
            boolean isEnableAlert, isEnableVibration, isSnooze;
            String note, typeClock;

            id = getInt(COLUMN_ID);
            characterId = getInt(COLUMN_CHARACTER_ID);
            levelVolume = getInt(COLUMN_LEVEL_VOLUME);
            hour = getInt(COLUMN_HOUR);
            minute = getInt(COLUMN_MINUTE);
            isEnableAlert = intToBoolean(getInt(COLUMN_IS_ENABLE_ALERT));
            isEnableVibration = intToBoolean(getInt(COLUMN_IS_ENABLE_VIBRATION));
            isSnooze = intToBoolean(getInt(COLUMN_IS_SNOOZE));
            note = getString(COLUMN_NOTE);
            typeClock = getString(COLUMN_TYPE_CLOCK);


            List<Long> dateToWake = fileDB.getArrayLong(id, FileDB.Type.ARRAY_DATE_MILLS);
            Long[] daysOfWeek = fileDB.getArrayLong(id, FileDB.Type.ARRAY_DAY_MILLS).toArray(new Long[]{});

            return new AlertData(id, characterId, levelVolume, hour, minute, isEnableAlert, isEnableVibration, isSnooze, note,typeClock, daysOfWeek, dateToWake);
        }
    }


}
