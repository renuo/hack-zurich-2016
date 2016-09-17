package ch.renuo.hackzurich2016.data;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
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
    public void getHousehold(final SuccessValueEventListener<Household> listener) {
        addListener(listener, householdDb);
    }

    @Override
    public void createHousehold(final SuccessValueEventListener<Household> listener) {
        final Household household = new HouseholdImpl(UUID.randomUUID(), new ArrayList<Cluster>());
        Map<String, Object> serializedHousehold = new HouseholdSerializer(household).serialize();
        householdDb.setValue(serializedHousehold);

        addListener(listener, householdDb);
    }

    private void addListener(final SuccessValueEventListener<Household> listener, DatabaseReference householdDb) {
        householdDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Household household = new HouseholdDeserializer(dataSnapshot.getValue()).deserialize();
                listener.onChangeCall(household);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // no errors will occur, ever
            }
        });
    }

    @Override
    public void updateHousehold(Household household) {
        // TODO: implement this
    }

    public void awaitHack() throws Exception {
        Tasks.await(database.getReference("hack await").setValue("this is for the tests"));
    }
}
