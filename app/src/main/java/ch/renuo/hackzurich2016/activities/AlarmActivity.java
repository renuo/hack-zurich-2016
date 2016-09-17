package ch.renuo.hackzurich2016.activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

import ch.renuo.hackzurich2016.MainActivity;
import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.alarms.AlarmController;
import ch.renuo.hackzurich2016.alarms.AlarmScheduler;
import ch.renuo.hackzurich2016.data.HouseholdDatabase;
import ch.renuo.hackzurich2016.data.HouseholdDatabaseImpl;
import ch.renuo.hackzurich2016.data.HouseholdQuery;
import ch.renuo.hackzurich2016.data.SuccessValueEventListener;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.Household;

public class AlarmActivity extends AppCompatActivity {

    private Ringtone ringtone;
    private HouseholdDatabase _db;
    private ClusterAlarm _alarm;
    private String _alarmId;
    private AlarmActivity self;
    private Household _household;
    private String _householdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
        this._alarmId = getIntent().getStringExtra(AlarmScheduler.ALARM_UUID_TAG);

        SharedPreferences prefs = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE);
        this._householdId = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE).getString(getString(R.string.household_id), null);

        initializeDatabase(_householdId);

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
        ((TextView)findViewById(R.id.alarmUUIDTextView)).setText(_alarmId.toString());
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
        if(_alarm != null && _household != null) {
            _alarm.setActive(false);
            this._alarm = null;
            _db.updateHousehold(_household);
        }

        finish();
    }

    public void setAlarm(ClusterAlarm alarm) {
        this._alarm = alarm;
    }

    public String getAlarmId() {
        return this._alarmId;
    }

    public void setHousehold(Household household) {
        this._household = household;
    }

    private void initializeDatabase(String householdId) {
        this._db = new HouseholdDatabaseImpl(UUID.fromString(householdId));
        SuccessValueEventListener<Household> listener = new SuccessValueEventListener<Household>() {

            @Override
            protected void onChange(Household household) {
                if(household == null){
                    self.finish();
                    return;
                }

                self.setHousehold(household);

                Pair<Cluster, ClusterAlarm> alarmPair = new HouseholdQuery(household).findAlarmById(self.getAlarmId());
                if (alarmPair == null || !alarmPair.second.getActive()) {
                    self.stopAlarm();
                } else {
                    self.setAlarm(alarmPair.second);
                }
            }
        };
        _db.listenForUpdates(listener);
    }
}
