package ch.renuo.hackzurich2016.models;

import java.util.List;
import java.util.UUID;

public interface Household {
    UUID getId();

    List<Cluster> getClusters();
}
