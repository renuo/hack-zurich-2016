package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class DeviceImpl implements Device {
    private UUID id;
    private List<SystemAlarm> systemAlarm;
    private String imageUrl;

    public DeviceImpl(UUID id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
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
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
