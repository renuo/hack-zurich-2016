package ch.renuo.hackzurich2016.alarms;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

import ch.renuo.hackzurich2016.data.HouseholdDatabase;
import ch.renuo.hackzurich2016.data.HouseholdDatabaseImpl;
import ch.renuo.hackzurich2016.data.HouseholdQuery;
import ch.renuo.hackzurich2016.data.SuccessValueEventListener;
import ch.renuo.hackzurich2016.helpers.PrefsHelper;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.Household;

public class SystemAlarmService extends Service {
    public static final String TAG = "SystemAlarmService";
    public static final String STOP_ALARM_EVENT = "STOP_ALARM_EVENT";

    public PrefsHelper prefs;
    private HouseholdDatabase _db;
    private AlarmScheduler _scheduler;
    private int id;

    @Override
    public void onCreate() {
        initializePrefs();
        initializeScheduler();
    }

    private void initializeScheduler() {
        this._scheduler = new AlarmScheduler(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying Service");
        super.onDestroy();
    }

    private void initializeDatabase() {
    showToast("householdId: " + prefs.getHouseholdId());
        if(prefs.getHouseholdId() == null) {
            showToast("Stopping Background Service");
            stopSelf();
            return;
        }

        this._db = new HouseholdDatabaseImpl(UUID.fromString(prefs.getHouseholdId()));
        _db.listenForUpdates(new SuccessValueEventListener<Household>() {

            @Override
            protected void onChange(Household household) {
                if(household == null){
                    Log.d(TAG, "Household is null");
                    showToast("Stopping Background Service: No DB");
                    stopSelf();
                    return;
                }

                showToast("Updating Alarms from DB");

                ClusterAlarm nextAlarm = getNextClusterAlarm(household);
                if(nextAlarm == null) {
                    showToast("Cancel Alarms from DB");
                    cancelScheduledAlarm();
                } else {
                    scheduleAlarm(nextAlarm);
                }
            }

            private ClusterAlarm getNextClusterAlarm(Household household) {
                return new HouseholdQuery(household).getNextClusterAlarm(prefs.getDeviceId());
            }
        });
    }

    private void initializePrefs() {
        this.prefs = new PrefsHelper(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting with startId: " + startId);
        this.id = startId;
        initializeDatabase();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void scheduleAlarm(ClusterAlarm alarm) {
        showToast("Scheduling Alarm: " + alarm.getTime());
        _scheduler.scheduleStartAlarm(alarm.getTimeAsCalendar(), alarm.getId().toString());
    }

    private void cancelScheduledAlarm() {
        _scheduler.cancelStartAlarm(null);
    }


    private void showToast(String message) {
//        Toast.makeText(this, "[" + this.id + "]" + message, Toast.LENGTH_SHORT).show();
    }

}
