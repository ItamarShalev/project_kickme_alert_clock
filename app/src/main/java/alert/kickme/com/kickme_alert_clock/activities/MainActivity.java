package alert.kickme.com.kickme_alert_clock.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import alert.kickme.com.kickme_alert_clock.database.DaoCharacterDatabase;
import alertData.kickme.com.kickme_alert_clock.R;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addAlertFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addAlertFloatingButton = findViewById(R.id.add_alert_floating_button);
        addAlertFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddAlertActivity.class));
            }
        });

    }
}
