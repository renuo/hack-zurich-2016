package ch.renuo.hackzurich2016.activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.alarms.AlarmController;
import ch.renuo.hackzurich2016.alarms.AlarmScheduler;

public class AlarmActivity extends AppCompatActivity {

    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri alarmNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        this.ringtone = RingtoneManager.getRingtone(this, alarmNotification);
        this.ringtone.play();
        setContentView(R.layout.activity_alarm);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(AlarmController.STOP_ALARM_EVENT));
    }

    @Override
    protected void onResume() {
        super.onResume();

        String alarmUUID = getIntent().getStringExtra(AlarmScheduler.ALARM_UUID_TAG);
        ((TextView)findViewById(R.id.alarmUUIDTextView)).setText(alarmUUID);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
        Log.d("AlarmActivity", "Ringtone is playing while destorying activity: " + this.ringtone.isPlaying());
        this.ringtone.stop();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    public void onTurnOffButtonClick(View view) {
       AlarmController alarmController = new AlarmController(getApplicationContext());
       alarmController.stopRingingAlarm();
    }
}
