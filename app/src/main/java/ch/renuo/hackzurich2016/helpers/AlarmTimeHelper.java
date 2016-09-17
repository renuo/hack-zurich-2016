package ch.renuo.hackzurich2016.helpers;

import android.util.Log;

import java.sql.Time;
import java.util.Calendar;

import ch.renuo.hackzurich2016.models.SystemAlarm;

public class AlarmTimeHelper {

    public Calendar alarmTime(SystemAlarm alarm) {
        String[] timeSplits = alarm.getTime().split(":");

        Calendar nextAlarmTime = Calendar.getInstance();
//        nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeSplits[0]));
//        nextAlarmTime.set(Calendar.MINUTE, Integer.valueOf(timeSplits[1]));
//        nextAlarmTime.set(Calendar.SECOND, 0);
//        nextAlarmTime.set(Calendar.MILLISECOND, 0);
//
//        if(nextAlarmTime.compareTo(Calendar.getInstance()) > 0) {
//
//        }

        nextAlarmTime.add(Calendar.SECOND, 10);

        Log.i("AlarmTimeHelper", nextAlarmTime.toString());
        return nextAlarmTime;
    }

}
