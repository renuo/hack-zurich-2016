package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public interface Device {
    UUID getId();

    String getImageUrl();

    List<SystemAlarm> getSystemAlarms();

    void setImageUrl(String imageUrl);
}
