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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.UUID;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.alarms.AlarmScheduler;
import ch.renuo.hackzurich2016.alarms.SystemAlarmService;
import ch.renuo.hackzurich2016.data.HouseholdDatabase;
import ch.renuo.hackzurich2016.data.HouseholdDatabaseImpl;
import ch.renuo.hackzurich2016.data.HouseholdQuery;
import ch.renuo.hackzurich2016.data.SuccessValueEventListener;
import ch.renuo.hackzurich2016.helpers.PrefsHelper;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.Household;

public class AlarmActivity extends AppCompatActivity {

    private Ringtone ringtone;
    private HouseholdDatabase _db;
    private ClusterAlarm _currentAlarm;
    private String _currentAlarmId;
    private Household _household;
    public PrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._currentAlarmId = getIntent().getStringExtra(AlarmScheduler.ALARM_UUID_TAG);

        initializePrefs();
        initializeDatabase();
        initializeRingtone();
        initializeReceiver();

        setContentView(R.layout.activity_alarm);

        initializeWindow();
    }

    private void initializeWindow() {
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void initializeReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(SystemAlarmService.STOP_ALARM_EVENT));
    }

    private void initializeRingtone() {
        Uri alarmNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        this.ringtone = RingtoneManager.getRingtone(this, alarmNotification);
        this.ringtone.play();
    }

    private void initializePrefs() {
       this.prefs = new PrefsHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.alarmUUIDTextView)).setText(_currentAlarmId);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        this.ringtone.stop();
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopAlarm();
        }
    };

    public void onTurnOffButtonClick(View view) {
        stopAlarm();
    }

    private void stopAlarm() {
        if(_currentAlarm != null && _household != null) {
            _currentAlarm.setActive(false);
            this._currentAlarm = null;
            _db.updateHousehold(_household);
        }

        finish();
    }

    private void initializeDatabase() {
        this._db = new HouseholdDatabaseImpl(UUID.fromString(prefs.getHouseholdId()));
        _db.listenForUpdates(new SuccessValueEventListener<Household>() {

            @Override
            protected void onChange(Household household) {
                if(household == null){
                    finish();
                    return;
                }

                ClusterAlarm alarm = new HouseholdQuery(household).findClusterAlarmById(_currentAlarmId);
                if (alarm == null || !alarm.getActive()) {
                    stopAlarm();
                } else {
                    _household = household;
                   _currentAlarm = alarm;
                }
            }
        });
    }
}
