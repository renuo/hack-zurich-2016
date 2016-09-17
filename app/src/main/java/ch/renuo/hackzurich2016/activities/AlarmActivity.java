package ch.renuo.hackzurich2016.activities;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.alarms.AlarmController;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRingtone().play();
        setContentView(R.layout.activity_alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String alarmUUID = getIntent().getStringExtra(AlarmController.ALARM_UUID_TAG);
        ((TextView)findViewById(R.id.alarmUUIDTextView)).setText(alarmUUID);
    }

    private Ringtone getRingtone() {
        Uri alarmNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return RingtoneManager.getRingtone(this, alarmNotification);
    }

    public void onTurnOffButtonClick(View view) {
        Log.d("AlarmActivity", "Ringtone is playing: " + getRingtone().isPlaying());
        getRingtone().stop();
        finish();
    }
}
