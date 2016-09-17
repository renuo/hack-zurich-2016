package ch.renuo.hackzurich2016.models;

import java.util.List;

public interface Device extends DTO{
    Cluster getCluster();

    List<SystemAlarm> getSystemAlarms();
}
