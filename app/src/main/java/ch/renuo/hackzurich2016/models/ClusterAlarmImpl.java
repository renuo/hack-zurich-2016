package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class ClusterAlarmImpl implements ClusterAlarm {
    private UUID id;
    private String time;
    private boolean active;
    private List<SystemAlarm> systemAlarms;

    public ClusterAlarmImpl(UUID id, String time, boolean active, List<SystemAlarm> systemAlarms) {
        this.id = id;
        this.time = time;
        this.active = active;
        this.systemAlarms = systemAlarms;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    @Override
    public List<SystemAlarm> getSystemAlarms() {
        return systemAlarms;
    }
}
