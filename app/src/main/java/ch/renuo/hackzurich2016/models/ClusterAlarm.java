package ch.renuo.hackzurich2016.models;

public interface ClusterAlarm extends DTO{
    Cluster getCluster();

    String getTime();
    void setTime(String time);

    boolean getActive();
    void setActive(boolean active);
}
