package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmScheduler {
    public static final String ALARM_UUID_TAG = "ALARM_UUID";
    public static final String TAG = "AlarmScheduler";

    private AlarmManager _alarmManager;
    private Context _context;

    public AlarmScheduler(Context context) {
        this._context = context;
        this._alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scheduleStartAlarm(Calendar at, String id) {
        long in = at.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        Log.d(TAG, "in: " + in + " id " + id );
        _alarmManager.set(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(), getOperation(id));
    }

    public void cancelStartAlarm(String id) {
        _alarmManager.cancel(getOperation(id));
    }

    private PendingIntent getOperation(String id) {
        Intent operation = new Intent(_context, StartAlarmReceiver.class);
        operation.putExtra(ALARM_UUID_TAG, id);
        return PendingIntent.getBroadcast(_context, 0, operation, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
