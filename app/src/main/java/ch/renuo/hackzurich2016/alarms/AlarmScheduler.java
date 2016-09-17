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

public class AlarmScheduler {
    public static final String ALARM_UUID_TAG = "ALARM_UUID";

    private AlarmManager _alarmManager;
    private Context _context;

    public AlarmScheduler(Context context) {
        this._context = context;
        this._alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void installAlarm(SystemAlarm alarm) {
        setNextAlarm(alarm);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setNextAlarm(SystemAlarm alarm) {
        long alarmTime = new AlarmTimeHelper().alarmTime(alarm).getTimeInMillis();
        Log.i("AlarmScheduler", "Alarm in " + (alarmTime - Calendar.getInstance().getTimeInMillis()));

        _alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, getPendingIntent(alarm));
    }

    private PendingIntent getPendingIntent(SystemAlarm alarm) {
        Intent intent = new Intent(_context, StartAlarmReceiver.class);
        intent.putExtra(ALARM_UUID_TAG, alarm.getId().toString());
        return PendingIntent.getBroadcast(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
