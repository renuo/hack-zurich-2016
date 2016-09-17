package ch.renuo.hackzurich2016.models;

import java.util.UUID;

/**
 * Created by yk on 17/09/16.
 */
abstract public class DTOMixin implements DTO{
    private String id;

    @Override
    public String getId() {
        if(id == null){
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
