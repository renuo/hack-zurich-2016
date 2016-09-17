package ch.renuo.hackzurich2016.data;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;
import ch.renuo.hackzurich2016.serializers.HouseholdDeserializer;
import ch.renuo.hackzurich2016.serializers.HouseholdSerializer;

public class HouseholdDatabaseImpl implements HouseholdDatabase {
    public final FirebaseDatabase database;
    private final DatabaseReference householdDb;

    public HouseholdDatabaseImpl(UUID householdId) {
        database = FirebaseDatabase.getInstance();
        householdDb = database.getReference(householdId.toString());
    }

    @Override
    public void listenForUpdates(final SuccessValueEventListener<Household> listener) {
        householdDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object dataSnapshotValue = dataSnapshot.getValue();
                if (dataSnapshotValue == null) {
                    listener.onChangeCall(null);
                    return;
                }
                Household household = new HouseholdDeserializer(dataSnapshotValue).deserialize();
                listener.onChangeCall(household);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Of course, no errors will occur. Ever. ¯\_(ツ)_/¯
            }
        });
    }

    @Override
    public void createHousehold(final SuccessValueEventListener<Household> listener) {
        final Household household = new HouseholdImpl(UUID.randomUUID(), new ArrayList<Cluster>());
        updateHousehold(household);
        listenForUpdates(listener);
    }

    @Override
    public void updateHousehold(Household household) {
        householdDb.setValue(new HouseholdSerializer(household).serialize());
    }

    public void awaitHack() throws Exception {
        Tasks.await(database.getReference("hack await").setValue("this is for the tests"));
    }
}
