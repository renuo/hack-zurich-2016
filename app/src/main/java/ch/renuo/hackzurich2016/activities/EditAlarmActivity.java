package ch.renuo.hackzurich2016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import ch.renuo.hackzurich2016.R;

public class EditAlarmActivity extends AppCompatActivity {

    private String clusterId;
    private String alarmId;
    private String alarmTime;
    private boolean alarmActive;
    private boolean alarmNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        Intent intent = getIntent();
        this.clusterId = intent.getStringExtra(getString(R.string.cluster_id));
        this.alarmId = intent.getStringExtra(getString(R.string.alarm_id));
        this.alarmTime = intent.getStringExtra(getString(R.string.alarm_time));
        this.alarmActive = intent.getBooleanExtra(getString(R.string.alarm_active), false);
        this.alarmNew = intent.getBooleanExtra(getString(R.string.alarm_new), false);

        String[] timeSplits = alarmTime.split(":");
        int hour = Integer.valueOf(timeSplits[0]);
        int minute = Integer.valueOf(timeSplits[1]);

        ((TimePicker)findViewById(R.id.timePicker)).setIs24HourView(true);
        ((TimePicker)findViewById(R.id.timePicker)).setCurrentHour(hour);
        ((TimePicker)findViewById(R.id.timePicker)).setCurrentMinute(minute);
        ((Switch)findViewById(R.id.activeSwitch)).setChecked(alarmActive);
    }

    private void doResult(int retval){
        this.alarmActive = ((Switch)findViewById(R.id.activeSwitch)).isChecked();
        int hour = ((TimePicker)findViewById(R.id.timePicker)).getCurrentHour();
        int minute = ((TimePicker)findViewById(R.id.timePicker)).getCurrentMinute();
        this.alarmTime = String.format("%02d", hour) + ":" + String.format("%02d", minute);

        Intent result = new Intent();
        result.putExtra(getString(R.string.cluster_id), this.clusterId);
        result.putExtra(getString(R.string.alarm_id), this.alarmId);
        result.putExtra(getString(R.string.alarm_time), this.alarmTime);
        result.putExtra(getString(R.string.alarm_active), this.alarmActive);
        result.putExtra(getString(R.string.alarm_new), this.alarmNew);
        setResult(retval, result);
        finish();
    }

    public void onSaveClicked(View view){
        doResult(0);
    }

    public void onDeleteClicked(View view){
        doResult(1);
    }
}
