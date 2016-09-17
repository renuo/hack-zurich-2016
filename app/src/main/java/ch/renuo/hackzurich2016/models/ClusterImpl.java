package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class ClusterImpl implements Cluster {
    private UUID id;
    private String name;
    private List<ClusterAlarm> clusterAlarms;
    private List<Device> devices;

    public ClusterImpl(UUID id, String name, List<ClusterAlarm> clusterAlarms, List<Device> devices) {
        this.id = id;
        this.name = name;
        this.clusterAlarms = clusterAlarms;
        this.devices = devices;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
