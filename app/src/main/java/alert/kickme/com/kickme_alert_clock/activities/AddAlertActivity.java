package alert.kickme.com.kickme_alert_clock.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import alert.kickme.com.kickme_alert_clock.fragments.TimePickerFragment;
import alertData.kickme.com.kickme_alert_clock.R;


public class AddAlertActivity extends AppCompatActivity {

    /**
     * If you want edit the AlertData object add the object eith this tag in intent
     */
    public static final String TAG_ALERT_ID = "TAG_ALERT_DATA_ID";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        getSupportFragmentManager().beginTransaction().add(R.id.timer, new TimePickerFragment()).commit();
    }
}
