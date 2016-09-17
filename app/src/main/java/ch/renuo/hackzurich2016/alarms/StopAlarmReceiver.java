package ch.renuo.hackzurich2016.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import ch.renuo.hackzurich2016.activities.AlarmActivity;

public class StopAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String uuid = extractUUID(intent);
        stopFiring(new AlarmController(context), uuid);
        stopRinging(context, uuid);
    }

    private void stopRinging(Context context, String uuid) {
        AlarmActivity.this.finish();
    }

    @NonNull
    private String extractUUID(Intent intent) {
        String uuid = intent.getStringExtra(AlarmScheduler.ALARM_UUID_TAG);
        return uuid != null ? uuid : "";
    }

    private void stopFiring(AlarmController controller, String uuid) {
        controller.setFiring(uuid, false);
    }
}
