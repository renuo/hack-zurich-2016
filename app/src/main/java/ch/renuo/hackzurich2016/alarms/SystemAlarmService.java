package ch.renuo.hackzurich2016.alarms;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

import ch.renuo.hackzurich2016.MainActivity;
import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.activities.AlarmActivity;
import ch.renuo.hackzurich2016.data.HouseholdDatabase;
import ch.renuo.hackzurich2016.data.HouseholdDatabaseImpl;
import ch.renuo.hackzurich2016.data.HouseholdQuery;
import ch.renuo.hackzurich2016.data.SuccessValueEventListener;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.Household;

public class SystemAlarmService extends Service {

    public static final String ACTION_RELOAD_SERVICE = "ch.renuo.hackzurich2016.service.ACTION_RELOAD_SERVICE";
    public static final String ACTION_START_ALARM = "ch.renuo.hackzurich2016.service.ACTION_START_ALARM";
    public static final String ACTION_STOP_ALARM = "ch.renuo.hackzurich2016.service.ACTION_STOP_ALARM";
    public static final String TAG = "SystemAlarmService";

    public SystemAlarmService() {
        super();
    }

    private SystemAlarmService self;
    private String _householdId;
    private String _deviceId;
    private HouseholdDatabase _db;
    private AlarmScheduler _scheduler;

    @Override
    public void onCreate() {
        this.self = this;
        initialize();
    }

    private void initializeScheduler() {
        this._scheduler = new AlarmScheduler(getApplicationContext());
    }

    private void initializeDatabase(String householdId) {
        this._db = new HouseholdDatabaseImpl(UUID.fromString(householdId));
        SuccessValueEventListener<Household> listener = new SuccessValueEventListener<Household>() {

            @Override
            protected void onChange(Household household) {
                if(household == null){
                    Log.d(TAG, "Household is null");
                    self.stopSelf();
                    return;
                }

                ClusterAlarm nextAlarm = new HouseholdQuery(household).getNextClusterAlarm(self._deviceId);

                if(nextAlarm == null) {
                    cancelScheduledAlarm();
                } else {
                    scheduleAlarm(nextAlarm);
                }

                Toast.makeText(self, "Updaaate!!", Toast.LENGTH_SHORT).show();

//                Cluster myCluster = findMyCluster(household);
//                if(myCluster == null){
//                    myCluster = new ClusterImpl(UUID.randomUUID(), "You", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
//                    // TODO: add image url from file
//                    String imageUrl = "";
//                    myCluster.getDevices().add(new DeviceImpl(self.deviceId, imageUrl, new ArrayList<SystemAlarm>()));
//                    household.getClusters().add(myCluster);
//                    hdb.updateHousehold(household);
//                }
            }
        };
        _db.listenForUpdates(listener);
    }

    private void initializePrefs() {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE);
        this._householdId = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE).getString(getString(R.string.household_id), null);
        this._deviceId = prefs.getString(getString(R.string.device_id), null);
    }

    public void initialize() {
        initializePrefs();
        initializeDatabase(_householdId);
        initializeScheduler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void scheduleAlarm(ClusterAlarm alarm) {
        Toast.makeText(self, "Scheduling Alarm: " + alarm.getTime(), Toast.LENGTH_SHORT).show();
        _scheduler.scheduleStartAlarm(alarm.getTimeAsCalendar(), alarm.getId().toString());
    }

    private void cancelScheduledAlarm() {
        Toast.makeText(self, "Canceling Alarm", Toast.LENGTH_SHORT).show();
        _scheduler.cancelStartAlarm("");
    }
}
