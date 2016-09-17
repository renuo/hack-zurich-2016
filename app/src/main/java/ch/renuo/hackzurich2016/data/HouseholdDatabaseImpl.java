package ch.renuo.hackzurich2016.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import ch.renuo.hackzurich2016.serializers.HouseholdDeserializer;
import ch.renuo.hackzurich2016.serializers.HouseholdSerializer;

public class HouseholdDatabaseImpl implements HouseholdDatabase {
    public final FirebaseDatabase database;

    public HouseholdDatabaseImpl() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void getHousehold(UUID householdId, final SuccessValueEventListener<Household> listener) {
        DatabaseReference myRef = database.getReference(householdId.toString());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onChangeCall((Household) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @NonNull
    private static Household getMockHousehold() {
        SystemAlarm systemAlarm = new SystemAlarmImpl(UUID.randomUUID(), "time", true);

        List<SystemAlarm> systemAlarms = new ArrayList<>();
        systemAlarms.add(systemAlarm);

        ClusterAlarm clusterAlarm = new ClusterAlarmImpl(UUID.randomUUID(), "time", true, systemAlarms);

        List<ClusterAlarm> clusterAlarms = new ArrayList<>();
        clusterAlarms.add(clusterAlarm);

        Device device = new DeviceImpl(UUID.randomUUID(), systemAlarms);

        List<Device> devices = new ArrayList<>();
        devices.add(device);

        Cluster cluster = new ClusterImpl(UUID.randomUUID(), "some", clusterAlarms, devices);

        List<Cluster> clusters = new ArrayList<>();
        clusters.add(cluster);

        return new HouseholdImpl(UUID.randomUUID(), clusters);
    }

    @Override
    public void createHousehold(UUID householdId, final SuccessValueEventListener<Household> listener) {
        final Household household = new HouseholdImpl(UUID.randomUUID(), new ArrayList<Cluster>());
        //final Household household = getMockHousehold();

        Map<String, Object> serializedHousehold = new HouseholdSerializer(household).serialize();
        DatabaseReference myRef = database.getReference(householdId.toString());
        myRef.setValue(serializedHousehold);
        System.err.println(serializedHousehold.get("clusters"));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Household household = new HouseholdDeserializer(dataSnapshot.getValue()).deserialize();
                listener.onChangeCall(household);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void awaitHack() throws Exception {
        Tasks.await(database.getReference("hack-await").setValue("hack"));
    }
}
