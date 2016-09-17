package ch.renuo.hackzurich2016.models;

import java.util.UUID;

/**
 * Created by yk on 17/09/16.
 */
abstract public class DTOMixin implements DTO{
    private String id = UUID.randomUUID().toString();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
