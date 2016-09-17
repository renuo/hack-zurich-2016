package ch.renuo.hackzurich2016.serializers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.Device;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.SystemAlarm;

public class HouseholdSerializer {
    private final Household household;

    public HouseholdSerializer(Household household) {
        this.household = household;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", household.getId().toString());
        map.put("clusters", getClusters());
        map.put("devices", getDevices());
        map.put("clusterAlarms", getClusterAlarms());
        map.put("systemAlarms", getSystemAlarms());
        return map;
    }

    @NonNull
    private List<Object> getClusters() {
        List<Object> clusters = new ArrayList<>();
        for (Cluster cluster : household.getClusters())
            clusters.add(getCluster(cluster));

        return clusters;
    }

    @NonNull
    private Map<String, Object> getCluster(Cluster cluster) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", cluster.getId().toString());
        map.put("name", cluster.getName());
        return map;
    }

    @NonNull

    private List<Object> getClusterAlarms() {
        List<Object> clusterAlarms = new ArrayList<>();
        for (Cluster cluster : household.getClusters())
            for (ClusterAlarm clusterAlarm : cluster.getClusterAlarms())
                clusterAlarms.add(getClusterAlarm(cluster.getId(), clusterAlarm));

        return clusterAlarms;
    }

    @NonNull

    private Map<String, Object> getClusterAlarm(UUID clusterId, ClusterAlarm clusterAlarm) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", clusterAlarm.getId().toString());
        map.put("clusterId", clusterId.toString());
        map.put("active", clusterAlarm.getActive());
        map.put("time", clusterAlarm.getTime());
        return map;
    }

    @NonNull

    private List<Object> getDevices() {
        List<Object> clusterAlarms = new ArrayList<>();
        for (Cluster cluster : household.getClusters())
            for (Device device : cluster.getDevices())
                clusterAlarms.add(getDevice(cluster.getId(), device));

        return clusterAlarms;
    }

    @NonNull

    private Map<String, Object> getDevice(UUID clusterId, Device device) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", device.getId().toString());
        map.put("imageUrl", device.getImageUrl());
        map.put("clusterId", clusterId.toString());
        return map;
    }

    @NonNull
    private List<Object> getSystemAlarms() {
        List<Object> systemAlarms = new ArrayList<>();
        for (Cluster cluster : household.getClusters())
            for (ClusterAlarm clusterAlarm : cluster.getClusterAlarms())
                for (SystemAlarm systemAlarm : clusterAlarm.getSystemAlarms())
                    systemAlarms.add(getSystemAlarm(cluster.getId(), clusterAlarm.getId(), systemAlarm));

        return systemAlarms;
    }

    @NonNull
    private Map<String, Object> getSystemAlarm(UUID clusterId, UUID clusterAlarmId, SystemAlarm systemAlarm) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", systemAlarm.getId().toString());
        map.put("active", systemAlarm.getActive());
        map.put("time", systemAlarm.getTime());
        map.put("clusterId", clusterId.toString());
        map.put("clusterAlarmId", clusterAlarmId.toString());
        return map;
    }
}
