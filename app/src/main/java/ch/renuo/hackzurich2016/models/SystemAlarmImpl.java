package ch.renuo.hackzurich2016.models;

import java.util.UUID;

public class SystemAlarmImpl implements SystemAlarm {
    private UUID id;
    private String time;
    private boolean active;

    public SystemAlarmImpl(UUID id, String time, boolean active) {
        this.id = id;
        this.time = time;
        this.active = active;
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
}
