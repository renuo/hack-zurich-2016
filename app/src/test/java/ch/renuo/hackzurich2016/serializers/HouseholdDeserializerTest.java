package ch.renuo.hackzurich2016.serializers;

import org.junit.Test;

import ch.renuo.hackzurich2016.models.Household;

import static org.junit.Assert.assertNotNull;

public class HouseholdDeserializerTest {
    @Test
    public void testGetMap() throws Exception {
        Household household = HouseholdMock.getHousehold();
        HouseholdSerializer householdSerializer = new HouseholdSerializer(household);
        HouseholdDeserializer deserializer = new HouseholdDeserializer(householdSerializer.serialize());
        Household deserializedHousehold = deserializer.deserialize();
        assertNotNull(deserializedHousehold);
    }
}
