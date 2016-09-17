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
    private static SystemAlarm systemAlarm = new SystemAlarmImpl(UUID.randomUUID(), "16:00", true);
    public static final String ALARM_UUID_TAG = "ALARM_UUID";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNextAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, StartAlarmReceiver.class);
        intent.putExtra(ALARM_UUID_TAG, systemAlarm.getId().toString());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Log.i("AlarmController", "Setting alarm!");
//        SystemAlarm nextAlarm = new SystemAlarmImpl(UUID.randomUUID(), "14:14", true);
//        long alarmTime = new AlarmTimeHelper().alarmTime(nextAlarm).getTimeInMillis();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
        long alarmTime = cal.getTimeInMillis();

        Log.i("AlarmController", "Alarm in " + (alarmTime - Calendar.getInstance().getTimeInMillis()));

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
    }

    public static void setFiring(UUID alarmUUID, boolean firing) {
        systemAlarm.setFiring(firing);
    }
//
//    private static final String INDICATOR_ACTION = "indicator";
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private static void updateNextAlarmInAlarmManager(Context context, SystemAlarm nextAlarm) {
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(
//                Context.ALARM_SERVICE);
//
//        int flags = nextAlarm == null ? PendingIntent.FLAG_NO_CREATE : 0;
//        PendingIntent operation = PendingIntent.getBroadcast(context, 0 /* requestCode */,
//                AlarmController.createIndicatorIntent(context), flags);
//
//        PendingIntent viewIntent = PendingIntent.getActivity(context, nextAlarm.hashCode(),
//                AlarmNotifications.createViewAlarmIntent(context, nextAlarm),
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        if (nextAlarm != null) {
//            long alarmTime = new AlarmTimeHelper().alarmTime(nextAlarm).getTimeInMillis();
//
//            AlarmManager.AlarmClockInfo info =
//                    new AlarmManager.AlarmClockInfo(alarmTime, viewIntent);
//            alarmManager.setAlarmClock(info, operation);
//        } else if (operation != null) {
//            alarmManager.cancel(operation);
//        }
//    }
//
//    private SystemAlarm getNextAlarm() {
//
//    }
//
//    public static Intent createIndicatorIntent(Context context) {
//        return new Intent(context, AlarmController.class).setAction(INDICATOR_ACTION);
//    }
}
