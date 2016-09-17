package ch.renuo.hackzurich2016.models;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface ClusterAlarm {
    UUID getId();

    String getTime();
    void setTime(String time);

    boolean getActive();
    void setActive(boolean active);

    Calendar getTimeAsCalendar();
}
