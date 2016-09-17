package ch.renuo.hackzurich2016.models;

import java.util.List;

public class ClusterImpl implements Cluster {
    private Household household;
    private String name;
    private List<ClusterAlarm> clusterAlarms;
    private List<Device> devices;

    public void Cluster(Household household, String name, List<ClusterAlarm> clusterAlarms,
                        List<Device> devices) {
        this.household = household;
        this.name = name;
        this.clusterAlarms = clusterAlarms;
        this.devices = devices;
    }

    @Override
    public Household getHousehold() {
        return household;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ClusterAlarm> getClusterAlarms() {
        return clusterAlarms;
    }

    @Override
    public List<Device> getDevices() {
        return devices;
    }
}
