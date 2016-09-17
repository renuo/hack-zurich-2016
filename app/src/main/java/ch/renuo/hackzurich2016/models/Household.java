package ch.renuo.hackzurich2016.models;

import java.util.List;

public interface Household extends DTO{
    String getName();

    List<Cluster> getClusters();
}
