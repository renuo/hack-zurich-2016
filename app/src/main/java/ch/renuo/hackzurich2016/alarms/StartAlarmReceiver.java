package ch.renuo.hackzurich2016.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import ch.renuo.hackzurich2016.activities.AlarmActivity;

public class StartAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startActivity(context, extractUUID(intent));
    }

    private void startActivity(Context context, String uuid) {
        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.putExtra(AlarmScheduler.ALARM_UUID_TAG, uuid);
        context.startActivity(activityIntent);
    }

    @NonNull
    private String extractUUID(Intent intent) {
        return intent.getStringExtra(AlarmScheduler.ALARM_UUID_TAG);
    }
}
