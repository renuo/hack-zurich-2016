package ch.renuo.hackzurich2016.alarms;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import ch.renuo.hackzurich2016.activities.AlarmActivity;

public class BootCompletedReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            Log.i("BootCompletedReceiver", "Boot is complete");
//            context.startService(new Intent(context, SystemAlarmService.class));
        }
    }
}
