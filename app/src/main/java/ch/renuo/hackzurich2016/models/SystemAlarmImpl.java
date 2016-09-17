package ch.renuo.hackzurich2016.models;

import java.util.UUID;

public class SystemAlarmImpl implements SystemAlarm {
    private UUID id;
    private String time;
    private boolean active;
    private boolean firing;

    public SystemAlarmImpl(UUID id, String time, boolean active) {
        this.id = id;
        this.time = time;
        this.active = active;
        this.firing = false;
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
    public boolean getFiring() {
        return firing;
    }

    @Override
    public void setFiring(boolean firing) {
        this.firing = firing;
    }
}
