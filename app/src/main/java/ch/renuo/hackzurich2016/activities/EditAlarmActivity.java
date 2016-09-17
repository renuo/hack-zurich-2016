package ch.renuo.hackzurich2016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import ch.renuo.hackzurich2016.R;

public class EditAlarmActivity extends AppCompatActivity {

    private String alarmId;
    private String alarmTime;
    private boolean alarmActive;
    private boolean alarmNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        Intent intent = getIntent();
        this.alarmId = intent.getStringExtra(getString(R.string.alarm_id));
        this.alarmTime = intent.getStringExtra(getString(R.string.alarm_time));
        this.alarmActive = intent.getBooleanExtra(getString(R.string.alarm_active), false);
        this.alarmNew = intent.getBooleanExtra(getString(R.string.alarm_new), false);

        String[] timeSplits = alarmTime.split(":");
        int hour = Integer.valueOf(timeSplits[0]);
        int minute = Integer.valueOf(timeSplits[1]);

        ((TimePicker)findViewById(R.id.timePicker)).setHour(hour);
        ((TimePicker)findViewById(R.id.timePicker)).setMinute(minute);
        ((Switch)findViewById(R.id.activeSwitch)).setChecked(alarmActive);
    }

    public void onSaveClicked(View view){
        Intent result = new Intent();
        result.putExtra(getString(R.string.alarm_id), this.alarmId);
        result.putExtra(getString(R.string.alarm_time), this.alarmTime);
        result.putExtra(getString(R.string.alarm_active), this.alarmActive);
        result.putExtra(getString(R.string.alarm_new), this.alarmNew);
        setResult(0, result);
    }

    public void onDeleteClicked(View view){
        Intent result = new Intent();
        result.putExtra(getString(R.string.alarm_id), this.alarmId);
        result.putExtra(getString(R.string.alarm_time), this.alarmTime);
        result.putExtra(getString(R.string.alarm_active), this.alarmActive);
        result.putExtra(getString(R.string.alarm_new), this.alarmNew);
        setResult(1, result);
    }
}
