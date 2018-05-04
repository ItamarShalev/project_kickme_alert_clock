package alert.kickme.com.kickme_alert_clock.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import alert.kickme.com.kickme_alert_clock.data.AlertData;
import alert.kickme.com.kickme_alert_clock.database.DaoAlertDatabase;
import alert.kickme.com.kickme_alert_clock.service.AlertService;

public class AlertReceiver extends BroadcastReceiver {

    public static final String TAG_ALERT_DATA_ID = "TAG_ALERT_DATA_ID";
    public static final String TAG_REQUEST_CODE_PENDING_INTENT = "TAG_REQUEST_CODE_PENDING_INTENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        int id = intent.getIntExtra(TAG_ALERT_DATA_ID, -1);
        if (id == -1 ||  !intent.hasExtra(TAG_REQUEST_CODE_PENDING_INTENT)) return;
        AlertData alertData = new DaoAlertDatabase(context).getItemById(id);
        if (alertData == null || !alertData.isEnableAlert()) return;
        intent.putExtra(AlertService.TAG_ALERT_DATA,alertData);
        intent.setClass(context,AlertService.class);
        context.startService(intent);
    }
}
