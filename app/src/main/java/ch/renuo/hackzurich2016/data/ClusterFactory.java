package ch.renuo.hackzurich2016.data;

import java.util.ArrayList;
import java.util.UUID;

import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterImpl;
import ch.renuo.hackzurich2016.models.Device;

public class ClusterFactory {
    public static Cluster getDefault() {
        return new ClusterImpl(UUID.randomUUID(), "You", new ArrayList<ClusterAlarm>(), new ArrayList<Device>());
    }
}
