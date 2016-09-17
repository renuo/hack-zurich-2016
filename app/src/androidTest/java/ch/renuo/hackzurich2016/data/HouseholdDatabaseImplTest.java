package ch.renuo.hackzurich2016.data;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;

public class HouseholdDatabaseImplTest extends ApplicationTestCase<Application> {
    public HouseholdDatabaseImplTest() {
        super(Application.class);
    }

    public void testGetHouseholdWhenHouseholdDoesNotExist() throws Exception {
        UUID householdId = UUID.fromString("11111111-4ef2-4145-9f89-d7cc6686ea3a");
        HouseholdDatabaseImpl sut = new HouseholdDatabaseImpl(householdId);
        SuccessValueEventListener listener = new SuccessValueEventListener() {
            @Override
            public void onChange(Object object) {
                assertNull(object);
            }
        };
        sut.listenForUpdates(listener);
        assertFalse(listener.wasCalled());
        sut.awaitHack();
        assertTrue(listener.wasCalled());
    }

    public void testGetHouseholdWhenHouseholdExists() throws Exception {
        UUID householdId = UUID.fromString("22222222-4ef2-4145-9f89-d7cc6686ea3a");
        HouseholdDatabaseImpl sut = new HouseholdDatabaseImpl(householdId);
        SuccessValueEventListener<Household> listener = new SuccessValueEventListener<Household>() {
            @Override
            public void onChange(Household household) {
                assertNotNull(household);
            }
        };
        sut.createHousehold(listener);
        assertFalse(listener.wasCalled());
        sut.awaitHack();
        assertTrue(listener.wasCalled());
    }
}
