package ch.renuo.hackzurich2016.alarms;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.UUID;

import ch.renuo.hackzurich2016.activities.AlarmActivity;
import ch.renuo.hackzurich2016.helpers.AlarmTimeHelper;
import ch.renuo.hackzurich2016.models.SystemAlarm;
import ch.renuo.hackzurich2016.models.SystemAlarmImpl;

public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {
    private AlarmManager alarmManager;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        // BEGIN_INCLUDE(alarm_onreceive)
        /*
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         *
         * ComponentName comp = new ComponentName(context.getPackageName(),
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         *
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */
//            Intent service = new Intent(context, AlarmActivity.class);

        context.startActivity(new Intent(context, AlarmActivity.class));

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
