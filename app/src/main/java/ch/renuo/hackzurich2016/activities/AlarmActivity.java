package ch.renuo.hackzurich2016.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.alarms.AlarmController;
import ch.renuo.hackzurich2016.alarms.StartAlarmReceiver;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri alarmNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarmNotification);
        r.play();

        setContentView(R.layout.activity_alarm);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("AlarmActivity", "onResume");

        String alarmUUID = getIntent().getStringExtra(AlarmController.ALARM_UUID_TAG);
        ((TextView)findViewById(R.id.alarmUUIDTextView)).setText(alarmUUID);
    }
}
