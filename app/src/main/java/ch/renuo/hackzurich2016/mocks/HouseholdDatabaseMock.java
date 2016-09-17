package ch.renuo.hackzurich2016.mocks;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.renuo.hackzurich2016.UI;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterAlarmImpl;
import ch.renuo.hackzurich2016.models.ClusterImpl;
import ch.renuo.hackzurich2016.models.Device;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;

/**
 * Created by yk on 17/09/16.
 */
public class HouseholdDatabaseMock {

    private final String householdId;

    private Household household;

    public HouseholdDatabaseMock(String householdId){
        this.householdId = householdId;
        this.household = new HouseholdImpl("My Household", new ArrayList<Cluster>());
        Cluster cluster1 = new ClusterImpl(this.household, "Cluster1", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
        this.household.getClusters().add(cluster1);
        ClusterAlarm c1a1 = new ClusterAlarmImpl(cluster1, "12:33", true);
        cluster1.getClusterAlarms().add(c1a1);
        ClusterAlarm c1a2 = new ClusterAlarmImpl(cluster1, "14:55", true);
        cluster1.getClusterAlarms().add(c1a2);
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", false));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", false));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", false));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", false));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        cluster1.getClusterAlarms().add(new ClusterAlarmImpl(cluster1, "12:32", true));
        Cluster cluster2 = new ClusterImpl(this.household, "Cluster2", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
        this.household.getClusters().add(cluster2);
        ClusterAlarm c2a1 = new ClusterAlarmImpl(cluster2, "08:00", true);
        cluster2.getClusterAlarms().add(c2a1);
    }

    public Household getHousehold(){
        return household;
    }

    public void saveHousehold(Household household){
        this.household = household;
    }

    // Clusters
    public List<Cluster> getClusters(){
        return null;
    }

    public void saveCluster(Cluster cluster){

    }

    // Devices
    public void saveDevice(Device device){

    }

    // Cluster Alarms
    public void saveClusterAlarm(ClusterAlarm alarm){
        Log.e("e", "f");
        UI.ui().refreshUI();
    }
    public void snoozeClusterAlarm(ClusterAlarm alarm){

    }
    public void deleteClusterAlarm(ClusterAlarm alarm){

    }

}
