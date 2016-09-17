package ch.renuo.hackzurich2016.alarms;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import ch.renuo.hackzurich2016.MainActivity;
import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.UI;
import ch.renuo.hackzurich2016.data.HouseholdDatabase;
import ch.renuo.hackzurich2016.data.HouseholdDatabaseImpl;
import ch.renuo.hackzurich2016.data.HouseholdQuery;
import ch.renuo.hackzurich2016.data.SuccessValueEventListener;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterImpl;
import ch.renuo.hackzurich2016.models.Device;
import ch.renuo.hackzurich2016.models.DeviceImpl;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.SystemAlarm;

public class SystemAlarmService extends Service {

    public static final String ACTION_RELOAD_SERVICE = "ch.renuo.hackzurich2016.service.ACTION_RELOAD_SERVICE";
    public static final String ACTION_STOP_ALARM = "ch.renuo.hackzurich2016.service.ACTION_STOP_ALARM";

    public SystemAlarmService() {
        super();
    }

    private SystemAlarmService self;
    private String _householdId;
    private String _deviceId;

    @Override
    public void onCreate() {
        loadPrefs();
        this.self = this;
        initializeDatabase(_householdId);
    }


    private HouseholdDatabase initializeDatabase(String householdId) {

        HouseholdDatabase db = new HouseholdDatabaseImpl(UUID.fromString(householdId));
        SuccessValueEventListener<Household> listener = new SuccessValueEventListener<Household>() {

            @Override
            protected void onChange(Household household) {
                if(household == null){
                    Log.d("Service", "Household is null");
                    self.stopSelf();
                    return;
                }

                ClusterAlarm nextAlarm = new HouseholdQuery(household).getNextClusterAlarm(self._deviceId);

                Toast.makeText(self, "Updaaate!! Next Alarm: " + nextAlarm.getTime(), Toast.LENGTH_SHORT).show();
                Log.d("Service", "There is an update!!");

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
        db.listenForUpdates(listener);
        return db;
    }

    private void loadPrefs() {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE);
        this._householdId = this.getSharedPreferences(MainActivity.PREFKEY, Context.MODE_PRIVATE).getString(getString(R.string.household_id), null);
        this._deviceId = prefs.getString(getString(R.string.device_id), null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
