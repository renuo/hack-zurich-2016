package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;

public class AlarmScheduler {
    public static final String ALARM_UUID_TAG = "ALARM_UUID";

    private AlarmManager _alarmManager;
    private Context _context;

    public AlarmScheduler(Context context) {
        this._context = context;
        this._alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scheduleStartAlarm(Calendar at, String id) {
        _alarmManager.set(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(), getPendingIntent(id));
    }

    public void cancelStartAlarm(String id) {
        _alarmManager.cancel(getPendingIntent(id));
    }

    private PendingIntent getPendingIntent(String id) {
        Intent intent = new Intent(_context, StartAlarmReceiver.class);

        intent.putExtra(ALARM_UUID_TAG, id);
        return PendingIntent.getBroadcast(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
