package alert.kickme.com.kickme_alert_clock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IntRange;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import alert.kickme.com.kickme_alert_clock.global.AppContext;


public abstract class BaseSqlite<T extends BaseSqlite.SaverObject>  extends SQLiteOpenHelper {


    private final BaseSqliteInfo info;
    private final Context context;
    private Cursor cursor;

    public BaseSqlite(Context context, BaseSqliteInfo info) {
        super(context, info.DATABASE_NAME, null, info.VERSION);
        this.info = info;
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + info.TABLE_NAME + ";");
        onCreate(db);
    }

    public int getMaxId() {
        int id = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + info.TABLE_NAME + " ORDER BY " + info.COLUMN_ID + " DESC LIMIT 1", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(cursor.getColumnIndex(info.COLUMN_ID));
            }
        } catch (Exception e) {
            catchException(e);
        } finally {
            if (db != null){
                db.close();
            }
            if (cursor != null){
                cursor.close();
            }
        }
        return id;
    }

    public T getItemById(int id){
        T item = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + info.TABLE_NAME + " WHERE " + info.COLUMN_ID + " = " + id, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                this.cursor = cursor;
                item = getItemFromCursor(cursor);
            }
        } catch (Exception e) {
            catchException(e);
        } finally {
            if (db != null){
                db.close();
            }
            if (cursor != null){
                cursor.close();
            }
        }
        return item;
    }

    public final boolean delete(int[] ids) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            for (int id : ids) {
                String query = "DELETE FROM " + info.TABLE_NAME + " WHERE " + info.COLUMN_ID + " =\"" + id + "\";";
                db.execSQL(query);
            }
            return true;
        } catch (SQLException e) {
            catchException(e);
            return false;
        } finally {
            if (db != null){
                db.close();
            }
        }
    }

    public final boolean delete(T... items){
        return delete(getAllIds(items));
    }

    public final void insertItem(T... items) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            for (T item : items) {
                if (item != null){
                    ContentValues contentValues = insertItemToContentValues(item) ;
                    db.insert(info.TABLE_NAME, null,contentValues);
                }
            }
        } catch (Exception e) {
            catchException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public final void updateItem(int id ,T item) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues contentValues = insertItemToContentValues(item) ;
            db.update(info.TABLE_NAME,contentValues,info.COLUMN_ID + "=" + id,null);
        } catch (Exception e) {
            catchException(e);
        } finally {
            if (db != null){
                db.close();
            }
        }
    }

    public final int getItemsCount() {
        String countQuery = "SELECT  * FROM " + info.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    protected abstract ContentValues insertItemToContentValues(T item);

    protected abstract T getItemFromCursor(Cursor cursor);

    protected final  String getString(String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    protected final int getInt(String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    protected final long getLong(String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    protected final boolean intToBoolean(@IntRange(from = 0, to = 1) int number){
        return number == 1;
    }

    protected final int booleanToInt(boolean b){
        return b ? 1 : 0;
    }


    private int[] getAllIds(T... ts){
        List<T> list = Arrays.asList(ts);
        list.removeAll(Collections.singleton(null));
        int[] ids = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getPrimaryId();
        }
       return ids;
    }

    protected void catchException(Exception e){
        AppContext.CatchException(context,e,"");
    }

    public final List<T> getAllItems() {
        List<T> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM " + info.TABLE_NAME;
            cursor = db.rawQuery(query, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                this.cursor = cursor;
                list.add(getItemFromCursor(cursor));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            catchException(e);
        } finally {
            if (db != null){
                db.close();
            }
            if (cursor != null){
                cursor.close();
            }
        }
        return list;
    }

    public interface SaverObject{
        int getPrimaryId();
    }

    public static class BaseSqliteInfo {
        private final int VERSION;
        private final String DATABASE_NAME;
        private final String TABLE_NAME;
        private final String COLUMN_ID;

        public BaseSqliteInfo(int VERSION, String DATABASE_NAME, String TABLE_NAME, String COLUMN_ID) {
            this.VERSION = VERSION;
            this.DATABASE_NAME = DATABASE_NAME;
            this.TABLE_NAME = TABLE_NAME;
            this.COLUMN_ID = COLUMN_ID;
        }
    }


    public static class BaseDao<T extends BaseSqlite.SaverObject, E extends BaseSqlite<T> >{

        private E sqlite;

        public BaseDao(E sqlite) {
            this.sqlite = sqlite;
        }

        public E getSqlite() {
            return sqlite;
        }

        public void insert(T... items){
            sqlite.insertItem(items);
        }

        public int getMaxId(){
            return sqlite.getMaxId();
        }

        public void delete(int... ids){
            sqlite.delete(ids);
        }

        public T getItemById(int id){
            return sqlite.getItemById(id);
        }

        public void delete(T... items){
            sqlite.delete(items);
        }

        public void update(int id,T item){
            sqlite.updateItem(id,item);
        }

        public int getItemsCount(){
            return sqlite.getItemsCount();
        }

        public List<T> getAllItems(){
            return sqlite.getAllItems();
        }

    }


}
