package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Calendar;
import java.util.UUID;

import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;
import ch.renuo.hackzurich2016.models.SystemAlarmImpl;

public class AlarmController {
    private Context _context;
    public final static String STOP_ALARM_EVENT = "STOP_ALARM_EVENT";

    public AlarmController(Context context) {
        this._context = context;
    }

    public void stopRingingAlarm() {
        Log.d("sender", "Broadcasting message");
//        setFiring(getNext);
        Intent intent = new Intent(STOP_ALARM_EVENT);
        LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
    }
}
