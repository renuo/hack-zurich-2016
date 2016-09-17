package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class DeviceImpl implements Device {
    private UUID id;
    private List<SystemAlarm> systemAlarm;

    public DeviceImpl(UUID id, List<SystemAlarm> systemAlarm) {
        this.id = id;
        this.systemAlarm = systemAlarm;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public List<SystemAlarm> getSystemAlarms() {
        return systemAlarm;
    }
}
