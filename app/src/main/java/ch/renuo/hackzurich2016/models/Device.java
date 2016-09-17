package ch.renuo.hackzurich2016.models;

import java.util.List;

public interface Device {
    Cluster getCluster();

    List<SystemAlarm> getSystemAlarms();
}
