package ch.renuo.hackzurich2016.data;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;

public class HouseholdDatabaseImplTest extends ApplicationTestCase<Application> {
    private final HouseholdDatabaseImpl sut;

    public HouseholdDatabaseImplTest() {
        super(Application.class);
        sut = new HouseholdDatabaseImpl();
    }

    public void testGetHouseholdWhenHouseholdDoesNotExist() throws Exception {
        UUID householdId = UUID.fromString("11111111-4ef2-4145-9f89-d7cc6686ea3a");
        SuccessValueEventListener listener = new SuccessValueEventListener() {
            @Override
            public void onChange(Object object) {
                assertNull(object);
            }
        };
        sut.getHousehold(householdId, listener);
        assertFalse(listener.wasCalled());
        sut.awaitHack();
        assertTrue(listener.wasCalled());
    }

    public void testGetHouseholdWhenHouseholdExists() throws Exception {
        UUID householdId = UUID.fromString("22222222-4ef2-4145-9f89-d7cc6686ea3a");
        SuccessValueEventListener<Household> listener = new SuccessValueEventListener<Household>() {
            @Override
            public void onChange(Household household) {
                assertNotNull(household);
            }
        };
        sut.createHousehold(householdId, listener);
        assertFalse(listener.wasCalled());
        sut.awaitHack();
        assertTrue(listener.wasCalled());
    }
}
