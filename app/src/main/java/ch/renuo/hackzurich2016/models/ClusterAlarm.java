package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public interface ClusterAlarm {
    UUID getId();

    String getTime();

    boolean getActive();

    List<SystemAlarm> getSystemAlarms();
}
