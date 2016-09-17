package ch.renuo.hackzurich2016.models;

public class ClusterAlarmImpl extends DTOMixin implements ClusterAlarm {
    private Cluster cluster;
    private String time;
    private boolean active;

    public ClusterAlarmImpl(Cluster cluster, String time, boolean active) {
        this.cluster = cluster;
        this.time = time;
        this.active = active;
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
