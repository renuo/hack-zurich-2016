package ch.renuo.hackzurich2016.mocks;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.renuo.hackzurich2016.UI;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterAlarmImpl;
import ch.renuo.hackzurich2016.models.ClusterImpl;
import ch.renuo.hackzurich2016.models.Device;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;
import ch.renuo.hackzurich2016.models.SystemAlarm;

/**
 * Created by yk on 17/09/16.
 */
public class HouseholdDatabaseMock {

    private final String householdId;

    private Household household;

    public static HouseholdDatabaseMock db;

    public HouseholdDatabaseMock(String householdId){
        this.householdId = householdId;
        this.household = new HouseholdImpl(UUID.randomUUID(), new ArrayList<Cluster>());
        Cluster cluster1 = new ClusterImpl(UUID.randomUUID(), "Cluster1", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
        this.household.getClusters().add(cluster1);
        ClusterAlarm c1a1 = new ClusterAlarmImpl(UUID.randomUUID(), "12:33", true, new ArrayList<SystemAlarm>());
        cluster1.getClusterAlarms().add(c1a1);
        ClusterAlarm c1a2 = new ClusterAlarmImpl(UUID.randomUUID(), "14:55", true, new ArrayList<SystemAlarm>());
        cluster1.getClusterAlarms().add(c1a2);
        for(int i=0;i<10;i++) {
            cluster1.getClusterAlarms().add(new ClusterAlarmImpl(UUID.randomUUID(), "12:32", true, new ArrayList<SystemAlarm>()));
        }
        Cluster cluster2 = new ClusterImpl(UUID.randomUUID(), "Cluster2", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
        this.household.getClusters().add(cluster2);
        ClusterAlarm c2a1 = new ClusterAlarmImpl(UUID.randomUUID(), "08:00", true, new ArrayList<SystemAlarm>());
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

    public void saveCluster(Household household, Cluster cluster){
        if(household.getClusters().indexOf(cluster) < 0){
            household.getClusters().add(cluster);
        }
        UI.ui().refreshUI();
    }

    // Devices
    public void saveDevice(Device device){

    }

    // Cluster Alarms
    public void saveClusterAlarm(Cluster cluster, ClusterAlarm alarm){
        Log.e("e", "saving alarm");
        int a = cluster.getClusterAlarms().indexOf(alarm);
        if(a < 0){
            cluster.getClusterAlarms().add(alarm);
        }
        UI.ui().refreshUI();
    }
    public void snoozeClusterAlarm(ClusterAlarm alarm){

    }

    public void deleteClusterAlarm(Cluster cluster, ClusterAlarm alarm){

    }

}
