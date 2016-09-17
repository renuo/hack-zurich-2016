package ch.renuo.hackzurich2016.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.WakefulBroadcastReceiver;

import ch.renuo.hackzurich2016.activities.AlarmActivity;

public class StartAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.putExtra(AlarmScheduler.ALARM_UUID_TAG, extractUUID(intent));
        context.startActivity(activityIntent);
    }

    @NonNull
    private String extractUUID(Intent intent) {
        return intent.getStringExtra(AlarmScheduler.ALARM_UUID_TAG);
    }
}
