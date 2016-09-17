package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class DeviceImpl implements Device {
    private UUID id;
    private List<SystemAlarm> systemAlarm;
    private String imageUrl;

    public DeviceImpl(UUID id, String imageUrl, List<SystemAlarm> systemAlarm) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.systemAlarm = systemAlarm;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public List<SystemAlarm> getSystemAlarms() {
        return systemAlarm;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
