package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public interface Cluster {
    UUID getId();

    String getName();

    List<ClusterAlarm> getClusterAlarms();

    List<Device> getDevices();
}
