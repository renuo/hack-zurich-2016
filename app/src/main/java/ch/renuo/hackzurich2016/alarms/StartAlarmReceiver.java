package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.UUID;

import ch.renuo.hackzurich2016.activities.AlarmActivity;
import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;
import ch.renuo.hackzurich2016.models.SystemAlarmImpl;

public class StartAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmUUID = intent.getStringExtra(AlarmController.ALARM_UUID_TAG);
        alarmUUID = alarmUUID != null ? alarmUUID : "";

        Log.i("StartAlarmReceiver", "Starting Alarm " + alarmUUID);

        AlarmController.setFiring(UUID.fromString(alarmUUID), true);
        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.putExtra(AlarmController.ALARM_UUID_TAG, alarmUUID);
        context.startActivity(activityIntent);

        // Start the service, keeping the device awake while it is launching.
//            startWakefulService(context, service);

        // END_INCLUDE(alarm_onreceive)
    }

    // END_INCLUDE(set_alarm)
//
//        /**
//         * Cancels the alarm.
//         * @param context
//         */
//        // BEGIN_INCLUDE(cancel_alarm)
//        public void cancelAlarm(Context context) {
//            // If the alarm has been set, cancel it.
//            if (alarmManager!= null) {
//                alarmManager.cancel(alarmIntent);
//            }
//
//            // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
//            // alarm when the device is rebooted.
//            ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//            PackageManager pm = context.getPackageManager();
//
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
//        // END_INCLUDE(cancel_alarm)
//    }
}
