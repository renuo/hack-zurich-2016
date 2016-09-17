package ch.renuo.hackzurich2016.models;

import android.util.Log;

import java.util.Calendar;
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
    public void setTime(String time) {
        this.time = time;
    }

    public Calendar getTimeAsCalendar() {
        String[] timeSplits = getTime().split(":");

        Calendar nextAlarmTime = Calendar.getInstance();
        nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeSplits[0]));
        nextAlarmTime.set(Calendar.MINUTE, Integer.valueOf(timeSplits[1]));
        nextAlarmTime.set(Calendar.SECOND, 0);
        nextAlarmTime.set(Calendar.MILLISECOND, 0);

        if(nextAlarmTime.compareTo(Calendar.getInstance()) > 0) {
            nextAlarmTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        return nextAlarmTime;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public List<SystemAlarm> getSystemAlarms() {
        return systemAlarms;
    }
}
