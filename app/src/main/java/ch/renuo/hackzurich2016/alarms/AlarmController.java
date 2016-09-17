package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;

public class AlarmController {
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
