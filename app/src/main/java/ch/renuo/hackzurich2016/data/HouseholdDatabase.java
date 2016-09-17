package ch.renuo.hackzurich2016.data;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;

public interface HouseholdDatabase {
    void listenForUpdates(SuccessValueEventListener<Household> listener);

    public void createHousehold(SuccessValueEventListener<Household> listener);

    public void updateHousehold(Household household);
}
