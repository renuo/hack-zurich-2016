package ch.renuo.hackzurich2016.serializers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterAlarmImpl;
import ch.renuo.hackzurich2016.models.ClusterImpl;
import ch.renuo.hackzurich2016.models.Device;
import ch.renuo.hackzurich2016.models.DeviceImpl;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;
import ch.renuo.hackzurich2016.models.SystemAlarm;
import ch.renuo.hackzurich2016.models.SystemAlarmImpl;

public class HouseholdMock {
    @NonNull
    public static Household getHousehold() {
        SystemAlarm systemAlarm = new SystemAlarmImpl(UUID.randomUUID(), "time", true);

        List<SystemAlarm> systemAlarms = new ArrayList<>();
        systemAlarms.add(systemAlarm);

        ClusterAlarm clusterAlarm = new ClusterAlarmImpl(UUID.randomUUID(), "time", true, systemAlarms);

        List<ClusterAlarm> clusterAlarms = new ArrayList<>();
        clusterAlarms.add(clusterAlarm);

        String imageUrl = "";
        Device device = new DeviceImpl(UUID.randomUUID(), imageUrl, systemAlarms);

        List<Device> devices = new ArrayList<>();
        devices.add(device);

        Cluster cluster = new ClusterImpl(UUID.randomUUID(), "some", clusterAlarms, devices);

        List<Cluster> clusters = new ArrayList<>();
        clusters.add(cluster);

        return new HouseholdImpl(UUID.randomUUID(), clusters);
    }
}
