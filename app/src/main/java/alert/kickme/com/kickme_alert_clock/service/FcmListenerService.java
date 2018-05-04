package alert.kickme.com.kickme_alert_clock.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import alert.kickme.com.kickme_alert_clock.networking.Networking;


public class FcmListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String referenceToken = FirebaseInstanceId.getInstance().getToken();
        Networking.sendTokenToServer(referenceToken);
    }
}
