package alert.kickme.com.kickme_alert_clock.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;

import alert.kickme.com.kickme_alert_clock.brodcast.AlertReceiver;
import alert.kickme.com.kickme_alert_clock.data.AlertData;
import alert.kickme.com.kickme_alert_clock.database.BaseSharedDB;
import alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase;
import alert.kickme.com.kickme_alert_clock.global.Helper;


public class AlertClockManger {

    private Context context;
    private DaoAlertDatabase daoAlertDatabase;
    private AlarmManager alarmManager;

    public AlertClockManger(Context context) {
        this.context = context;
        daoAlertDatabase = new DaoAlertDatabase(context);
        alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    public List<AlertData> getAllAlert() {
        return daoAlertDatabase.getAllItems();
    }

    //--------------------------------
    //----------Public method---------
    //--------------------------------

    /**
     * @param alertData אto save and start to reviver
     * @return id for alertData
     */
    public int saveAlert(AlertData alertData) {
        alertData.setId(daoAlertDatabase.getMaxId() + 1);
        daoAlertDatabase.insert(alertData);
        enableAlerts(alertData);
        return alertData.getPrimaryId();
    }

    public long replaceAlert(AlertData oldAlertData, AlertData newAlertData) {
        int idAlertData = oldAlertData.getPrimaryId();
        daoAlertDatabase.update(idAlertData, newAlertData);
        handleLongMills(newAlertData,oldAlertData.getLongTimeDateDifferent(newAlertData),false);
        handleLongMills(newAlertData,oldAlertData.getLongTimeDayDifferent(newAlertData),true);
        return idAlertData;
    }


    public void deleteAlert(AlertData alertData) {
        cancelAlerts(alertData);
        daoAlertDatabase.delete(alertData);
    }

    public void snoozeAlert(AlertData alertData) {
        long timeSnooze = BaseSharedDB.SharedDB.getTimeSnooze(context);
        long millsTime = System.currentTimeMillis();
        setAlarm(millsTime + timeSnooze,getPendingIntent(millsTime,alertData.getPrimaryId()));
    }

    public void setEnable(AlertData alertData,boolean isEnable) {
        alertData.setEnableAlert(isEnable);
        if (isEnable){
            enableAlerts(alertData);
        }else{
            cancelAlerts(alertData);
        }
    }

    //---------------------------------
    //----------Private method---------
    //---------------------------------

    private void handleLongMills(AlertData alertData, List<Long>[] lists, boolean isDay){
        int idAlertData = alertData.getPrimaryId();
        if (lists[0].size() != 0 || lists[1].size() != 0){
            cancelAlerts(lists[0], idAlertData);
            enableAlerts(lists[1], idAlertData, isDay);
            if (isDay) {
                daoAlertDatabase.saveArrayDay(alertData);
            }else{
                daoAlertDatabase.saveArrayDate(alertData);
            }
        }
    }

    private void cancelAlerts(List<Long> millsLongList, int idAlertData) {
        for (Long millsLong : millsLongList) {
            if (millsLong != null) {
                cancelAlarm(getPendingIntent(millsLong, idAlertData));
            }
        }
    }

    private void cancelAlerts(Long[] millsLongList, int idAlertData) {
        cancelAlerts(Arrays.asList(millsLongList), idAlertData);
    }

    private void cancelAlerts(AlertData alertData) {
        int idAlertData = alertData.getPrimaryId();
        cancelAlerts(alertData.getDaysOfWeek(), idAlertData);
        cancelAlerts(alertData.getDateToWake(), idAlertData);
    }

    private void enableAlerts(AlertData alertData) {
        int idAlertData = alertData.getPrimaryId();
        enableAlerts(alertData.getDateToWake(), idAlertData, false);
        enableAlerts(alertData.getDaysOfWeek(), idAlertData, true);
    }

    private void enableAlerts(List<Long> millsLongList, int idAlertData, boolean repeat) {
        long spaceTimeWeek = Helper.Time.getSpaceOfWeek();
        PendingIntent pendingIntentAlert;
        for (Long millsLong : millsLongList) {
            if (millsLong != null) {
                pendingIntentAlert = getPendingIntent(millsLong, idAlertData);
                if (repeat) {
                    setRepeatingAlarm(millsLong, spaceTimeWeek, pendingIntentAlert);
                } else {
                    setAlarm(millsLong, pendingIntentAlert);
                }
            }
        }
    }

    private void enableAlerts(Long[] millsLongList, int idAlertData, boolean repeat) {
        enableAlerts(Arrays.asList(millsLongList), idAlertData, repeat);
    }

    private void setAlarm(long millsTime, PendingIntent pendingIntent) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, millsTime, pendingIntent);
    }

    private void setRepeatingAlarm(long millsTime, long millsTimeSpace, PendingIntent pendingIntent) {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millsTime, millsTimeSpace, pendingIntent);
    }

    private void cancelAlarm(PendingIntent pendingIntent) {
        alarmManager.cancel(pendingIntent);
    }

    /**
     * @param timeAlert   long mills to make alert
     * @param idAlertData id for the alert data to start
     * @return pending intent with the intent contains #idAlertData
     */
    private PendingIntent getPendingIntent(long timeAlert, int idAlertData) {
        int requestCode = (int) (timeAlert + idAlertData);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra(AlertReceiver.TAG_ALERT_DATA_ID, idAlertData);
        intent.putExtra(AlertReceiver.TAG_REQUEST_CODE_PENDING_INTENT, requestCode);
        return PendingIntent.getBroadcast(context, requestCode, intent, 0);
    }

}
