package ch.renuo.hackzurich2016.models;

public class SystemAlarmImpl extends DTOMixin implements SystemAlarm {
    private String time;
    private boolean active;

    public SystemAlarmImpl(String time, boolean active) {
        this.time = time;
        this.active = active;
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
