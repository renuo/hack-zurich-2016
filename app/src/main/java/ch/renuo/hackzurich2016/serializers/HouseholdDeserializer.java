package ch.renuo.hackzurich2016.serializers;

import java.util.Map;

import ch.renuo.hackzurich2016.models.Household;

public class HouseholdDeserializer {
    private final Map<String, Object> serializedObject;

    public HouseholdDeserializer(Map<String, Object> serializedObject) {
        this.serializedObject = serializedObject;
    }

    public Household deserialize() {
        return null;
    }
}
