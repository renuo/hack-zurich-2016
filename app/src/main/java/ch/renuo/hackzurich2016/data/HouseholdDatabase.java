package ch.renuo.hackzurich2016.data;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;

public interface HouseholdDatabase {
    void getHousehold(UUID householdId, SuccessValueEventListener<Household> listener);

    public void createHousehold(UUID householdId, SuccessValueEventListener<Household> listener);
}
