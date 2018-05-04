package alert.kickme.com.kickme_alert_clock.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import alert.kickme.com.kickme_alert_clock.data.AlertData;
import alert.kickme.com.kickme_alert_clock.data.CharacterData;
import alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase;
import alert.kickme.com.kickme_alert_clock.database.DaoCharacterDatabase;
import alert.kickme.com.kickme_alert_clock.global.Helper;
import alert.kickme.com.kickme_alert_clock.utils.AlertClockManger;

public class AlertService extends Service{

    public static final String TAG_ALERT_DATA = "TAG_ALERT_DATA";
    private DaoAlertDatabase daoAlertDatabase;
    private DaoCharacterDatabase daoCharacterDatabase;
    private AlertClockManger alertClockManger;

    @Override
    public void onCreate() {
        super.onCreate();
        daoAlertDatabase = new DaoAlertDatabase(this);
        daoCharacterDatabase = new DaoCharacterDatabase(this);
        alertClockManger = new AlertClockManger(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
        /*AlertData alertData = (AlertData) intent.getSerializableExtra(TAG_ALERT_DATA);
        CharacterData characterData = daoCharacterDatabase.getItemById(alertData.getCharacterId());*/
        Helper.Global.makeNotification(this, "heyyy", "wakeUp");
        }
        return START_NOT_STICKY;
    }
}
