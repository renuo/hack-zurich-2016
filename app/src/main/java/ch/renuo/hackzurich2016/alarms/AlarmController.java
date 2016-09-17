package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.UUID;

import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;
import ch.renuo.hackzurich2016.models.SystemAlarmImpl;

public class AlarmController {
    private Context _context;
    private AlarmScheduler _scheduler;

    public AlarmController(Context context) {
        this._context = context;
        this._scheduler = new AlarmScheduler(context);
    }

    public void setFiring(String uuid, boolean firing) {
        UUID.fromString(uuid);
        //systemAlarm.setFiring(firing);
    }

    public void onUpdate() {
        // Something changed in the database
        // Get latest

        _scheduler.installAlarm(getNextAlarm());
    }

    private SystemAlarm getNextAlarm() {
       return new SystemAlarmImpl(UUID.randomUUID(), "16:00", true);
    }
}
