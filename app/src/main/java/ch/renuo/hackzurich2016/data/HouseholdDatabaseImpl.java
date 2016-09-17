package ch.renuo.hackzurich2016.data;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;

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

    @Override
    public void createHousehold(UUID householdId, final SuccessValueEventListener<Household> listener) {
        // TODO: // FIXME: 17/Sep/16
//        HouseholdImpl household = new HouseholdImpl();
//        household.id = householdId.toString();
//        household.clusters = new ArrayList<Cluster>();
//        ClusterImpl e = new ClusterImpl(id, name, clusterAlarms, devices);
//        e.name = "huhu";
//        household.clusters.add(e);
        DatabaseReference myRef = database.getReference(householdId.toString());
//        myRef.setValue(household);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onChangeCall(dataSnapshot.getValue(HouseholdImpl.class));
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
