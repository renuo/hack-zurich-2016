package ch.renuo.hackzurich2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HouseholdActivity extends AppCompatActivity {

    private String deviceId;
    private String householdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);

        Intent intent = getIntent();
        this.deviceId = intent.getStringExtra(getString(R.string.device_id));
        this.householdId = intent.getStringExtra(getString(R.string.household_id));

    }
}
