package ch.renuo.hackzurich2016.models;

import java.util.List;

public class DeviceImpl extends DTOMixin implements Device {
    private Cluster cluster;
    private List<SystemAlarm> systemAlarm;

    public DeviceImpl(Cluster cluster, List<SystemAlarm> systemAlarm) {
        this.cluster = cluster;
        this.systemAlarm = systemAlarm;
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }

    @Override
    public List<SystemAlarm> getSystemAlarms() {
        return systemAlarm;
    }
}
