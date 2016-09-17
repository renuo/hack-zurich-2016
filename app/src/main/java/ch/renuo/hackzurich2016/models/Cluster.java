package ch.renuo.hackzurich2016.models;

import java.util.List;

public interface Cluster {
    Household getHousehold();

    String getName();

    List<ClusterAlarm> getClusterAlarms();

    List<Device> getDevices();

}
