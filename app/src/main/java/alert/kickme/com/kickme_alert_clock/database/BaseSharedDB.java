package alert.kickme.com.kickme_alert_clock.database;

import android.content.Context;
import android.content.SharedPreferences;


public abstract class BaseSharedDB {

    public static class SharedDB extends BaseSharedDB{



        private static final String TAG_TIME_SNOOZE = "TAG_TIME_SNOOZE";
        private static final int DEF_TIME_SNOOZE = 5000;


        private static final String TAG_MAX_NUMBER_OF_TIME_SNOOZE = "TAG_MAX_NUMBER_OF_TIME_SNOOZE";
        private static final int DEF_MAX_NUMBER_OF_TIME_SNOOZE = 5;


        private static final String TAG_GSM_TOKEN = "TAG_GSM_TOKEN";
        private static final String DEF_GSM_TOKEN = null;

        private static final String TAG_TYPE_CLOCK = "TAG_TYPE_CLOCK";
        private static final String DEF_TYPE_CLOCK = "ALL";


        public static long getTimeSnooze(Context context) {
            return getLong(context,TAG_TIME_SNOOZE, DEF_TIME_SNOOZE);
        }

        public static void setTimeSnooze(Context context, long timeSnooze) {
            putLong(context,TAG_TIME_SNOOZE,timeSnooze);
        }


        public static int getMaxNumberOfTimeSnooze(Context context) {
            return getInt(context,TAG_MAX_NUMBER_OF_TIME_SNOOZE,DEF_MAX_NUMBER_OF_TIME_SNOOZE);
        }

        public static void setMaxNumberOfTimeSnooze(Context context, int maxNumberOfTimeSnooze) {
            putInt(context,TAG_MAX_NUMBER_OF_TIME_SNOOZE,maxNumberOfTimeSnooze);
        }


        public static String getGsmToken(Context context) {
            return getString(context,TAG_GSM_TOKEN,DEF_GSM_TOKEN);
        }

        public static void setGsmToken(Context context, String gsmToken) {
            putString(context,TAG_GSM_TOKEN,gsmToken);
        }

        public static String getTypeClock(Context context) {
            return getString(context,TAG_TYPE_CLOCK,DEF_TYPE_CLOCK);
        }

        public static void setTypeClock(Context context, String typeClock) {
            putString(context,TAG_TYPE_CLOCK, typeClock);
        }



    }


    private static final String TAG_NAME = SharedDB.class.getCanonicalName();


    protected static void putString(Context context,String tag, String value){
        getSharedPreferencesEditor(context).putString(tag, value).commit();
    }
    protected static String getString(Context context,String tag, String defaultValue){
       return getSharedPreferences(context).getString(tag, defaultValue);
    }

    protected static void putLong(Context context,String tag, long value){
        getSharedPreferencesEditor(context).putLong(tag, value).commit();
    }
    protected static long getLong(Context context,String tag, long defaultValue){
        return getSharedPreferences(context).getLong(tag, defaultValue);
    }

    protected static void putInt(Context context,String tag, int value){
        getSharedPreferencesEditor(context).putInt(tag, value).commit();
    }
    protected static int getInt(Context context,String tag, int defaultValue){
        return getSharedPreferences(context).getInt(tag, defaultValue);
    }

    protected static void putBolean(Context context,String tag, boolean value){
        getSharedPreferencesEditor(context).putBoolean(tag, value).commit();
    }
    protected static boolean getBoolean(Context context,String tag, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(tag, defaultValue);
    }

   
    protected static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(TAG_NAME, Context.MODE_PRIVATE);
    }
    protected static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return getSharedPreferences(context).edit();
    }


}
