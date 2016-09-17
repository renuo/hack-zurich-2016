package ch.renuo.hackzurich2016.models;

import java.util.List;

public class HouseholdImpl implements Household {
    private String name;
    private List<Cluster> cluster;

    public HouseholdImpl(String name, List<Cluster> cluster) {
        this.name = name;
        this.cluster = cluster;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Cluster> getClusters() {
        return cluster;
    }
}
