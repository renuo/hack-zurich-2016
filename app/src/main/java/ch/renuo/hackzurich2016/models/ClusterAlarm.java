package ch.renuo.hackzurich2016.models;

public interface ClusterAlarm {
    Cluster getCluster();

    String getTime();

    boolean getActive();
}
