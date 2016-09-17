package ch.renuo.hackzurich2016.models;

import java.util.UUID;

public interface SystemAlarm {
    UUID getId();

    String getTime();

    boolean getActive();

    boolean getFiring();

    void setFiring(boolean firing);
}
