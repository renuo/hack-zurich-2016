package ch.renuo.hackzurich2016.models;

public interface SystemAlarm extends DTO{
    String getTime();

    boolean getActive();
}
