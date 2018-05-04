package alert.kickme.com.kickme_alert_clock.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import alert.kickme.com.kickme_alert_clock.database.BaseSqlite;

import static alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase.COLUMN_CHARACTER_ID;
import static alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase.COLUMN_IS_ENABLE_VIBRATION;
import static alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase.COLUMN_IS_SNOOZE;
import static alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase.COLUMN_LEVEL_VOLUME;
import static alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase.COLUMN_TYPE_CLOCK;

public class AlertData implements Serializable, Cloneable, BaseSqlite.SaverObject {


    private static final String TAG_ALERT_DATA = "TAG_ALERT_DATA";
    private int id;
    private int characterId;
    private int levelVolume;
    private int hour;
    private int minute;


    private boolean isEnableAlert;
    private boolean isEnableVibration;
    private boolean isSnooze;

    private String note;

    private Type typeClock;

    private Long[] daysOfWeek;
    private List<Long> dateToWake;





    //--------------------------
    //--------Constructor-------
    //--------------------------




    public AlertData() {
        id = -1;
        daysOfWeek = new Long[8];
        dateToWake = new LinkedList<>();
        typeClock = Type.ALL;
    }

    public AlertData(int id, int characterId, int levelVolume, int hour, int minute, boolean isEnableAlert, boolean isEnableVibration, boolean isSnooze,String note,String typeClock, Long[] daysOfWeek, List<Long> dateToWake) {
        this.id = id;
        this.characterId = characterId;
        this.levelVolume = levelVolume;
        this.hour = hour;
        this.minute = minute;
        this.isEnableAlert = isEnableAlert;
        this.isEnableVibration = isEnableVibration;
        this.isSnooze = isSnooze;
        this.note = note;
        this.typeClock = Type.valueOf(typeClock);
        this.daysOfWeek = daysOfWeek;
        this.dateToWake = dateToWake;
    }





    public AlertData(Context context){
        this();
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG_ALERT_DATA, Context.MODE_PRIVATE);
        characterId = sharedPreferences.getInt(COLUMN_CHARACTER_ID,0);
        levelVolume = sharedPreferences.getInt(COLUMN_LEVEL_VOLUME,75);
        isEnableVibration = sharedPreferences.getBoolean(COLUMN_IS_ENABLE_VIBRATION,true);
        isSnooze = sharedPreferences.getBoolean(COLUMN_IS_SNOOZE,false);
        typeClock = Type.valueOf(sharedPreferences.getString(COLUMN_TYPE_CLOCK,"ALL"));
    }

    public static void saveDefaultAlertData(Context context, AlertData alertData){
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG_ALERT_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLUMN_CHARACTER_ID,alertData.getCharacterId());
        editor.putInt(COLUMN_LEVEL_VOLUME,alertData.getLevelVolume());
        editor.putBoolean(COLUMN_IS_ENABLE_VIBRATION,alertData.isEnableVibration());
        editor.putBoolean(COLUMN_IS_SNOOZE,alertData.isSnooze());
        editor.putString(COLUMN_TYPE_CLOCK,alertData.getStringTypeClock());
        editor.apply();
    }

    public Type getTypeClock() {
        return typeClock;
    }


    //--------------------------
    //----------Function--------
    //--------------------------


    public static enum Type{
        PM,AM,ALL;
    }





    public boolean haveReturnDay(){
        for (Long aDaysOfWeek : daysOfWeek) {
            if (aDaysOfWeek != null) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param otherAlertData list to equals with currentList
     * @return 2 lists , in position 0 list to remove the user cancel,
     * list in position 1 list to add the user adding
     */
    public List<Long>[] getLongTimeDateDifferent(AlertData otherAlertData) {
        return getItemsDifferent(dateToWake, otherAlertData.getDateToWake());
    }

    /**
     * @param otherAlertData list to equals with currentList
     * @return 2 lists , in position 0 list to remove the user cancel,
     * list in position 1 list to add the user adding
     */
    public List<Long>[] getLongTimeDayDifferent(AlertData otherAlertData) {
        return getItemsDifferent(
                Arrays.asList(daysOfWeek),
                Arrays.asList(otherAlertData.getDaysOfWeek()));
    }


    /**
     * On the old AlertData !!!
     *
     * @param newList list to equals with oldList
     * @return 2 lists , in position 0 list to remove the user cancel,
     * list in position 1 list to add the user adding
     */
    private static <T> List[] getItemsDifferent(List<T> oldList, List<T> newList) {
        List[] longList = new List[2];
        longList[0] = getDifferentItem(oldList,newList); // The items to remove
        longList[1] = getDifferentItem(newList,oldList);// The items to add
        return longList;
    }

    /**
     * @param listChecking The list we want check if item in #listChecking in #fromList
     * @param <T> All Object
     * @return List (ArrayList) With the item are not at #fromList
     */
    private static <T> List<T> getDifferentItem(List<T> fromList, List<T> listChecking) {
        List<T> differentItemList = new ArrayList<>();
        Set<T> setChecking = new HashSet<>(listChecking);
        T timeLong;
        for (T aCurrentList : fromList) {
            timeLong = aCurrentList;
            if (timeLong != null) {
                if (!setChecking.contains(timeLong)) {
                    differentItemList.add(timeLong);
                }
            }
        }
        return differentItemList;
    }



    //--------------------------
    //----------Getter----------
    //--------------------------

    @Override
    public int getPrimaryId() {
        return id;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getLevelVolume() {
        return levelVolume;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isEnableAlert() {
        return isEnableAlert;
    }

    public boolean isEnableVibration() {
        return isEnableVibration;
    }

    public Long[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public List<Long> getDateToWake() {
        return dateToWake;
    }

    public boolean isSnooze() {
        return isSnooze;
    }

    public String getNote() {
        return note;
    }

    public String getStringTypeClock(){
        return typeClock.name();
    }

    public String getStringTime(){
        /*StringBuilder timeString = new StringBuilder();
        if (typeClock != Type.ALL){
            timeString.append(typeClock.name()).append(" ");
        }
        timeString.append(hour).append(":").append(minute);
        return timeString.toString();*/

        String timeString  = "";
        if (typeClock == Type.PM){
            timeString = String.valueOf(hour-12);
        }else{
            timeString = String.valueOf(hour);
        }
        timeString += ":" + minute;
        return timeString;
    }

    public String getTextPmAm(){
        String type = "";
        if (typeClock != Type.ALL){
            type = typeClock.name();
        }
        return type;
    }

    //--------------------------
    //----------Setter----------
    //--------------------------


    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public void setLevelVolume(int levelVolume) {
        this.levelVolume = levelVolume;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setEnableAlert(boolean enableAlert) {
        isEnableAlert = enableAlert;
    }

    public void setEnableVibration(boolean enableVibration) {
        isEnableVibration = enableVibration;
    }

    public void setDaysOfWeek(Long[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setDateToWake(List<Long> dateToWake) {
        this.dateToWake = dateToWake;
    }

    public void setSnooze(boolean snooze) {
        isSnooze = snooze;
    }

    //--------------------------
    //----------Override--------
    //--------------------------



    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        AlertData alertData = new AlertData();
        alertData.characterId = this.characterId;
        alertData.levelVolume = this.levelVolume;
        alertData.hour = this.hour;
        alertData.minute = this.minute;
        alertData.isEnableAlert = this.isEnableAlert;
        alertData.note = this.note;
        for (int i = 0; i < this.daysOfWeek.length; i++) {
            alertData.daysOfWeek[i] = this.daysOfWeek[i];
        }
        alertData.dateToWake.addAll(this.dateToWake);
        alertData.typeClock = this.typeClock;
        return alertData;
    }

}
