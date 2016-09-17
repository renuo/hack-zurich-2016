package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public class HouseholdImpl implements Household {
    public UUID id;
    public List<Cluster> clusters;

    public HouseholdImpl(UUID id, List<Cluster> clusters) {
        this.id = id;
        this.clusters = clusters;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public List<Cluster> getClusters() {
        return clusters;
    }
}
