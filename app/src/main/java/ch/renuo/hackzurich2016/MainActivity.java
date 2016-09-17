package ch.renuo.hackzurich2016;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        String deviceId = prefs.getString(getString(R.string.device_id), null);
        if(deviceId == null){
            deviceId = UUID.randomUUID().toString();
            prefs.edit().putString(getString(R.string.device_id), deviceId).commit();
        }
        String householdId = prefs.getString(getString(R.string.household_id), "hhid1");
        if(householdId != null){
            Intent intent = new Intent(this, HouseholdActivity.class);
            intent.putExtra(getString(R.string.device_id), deviceId);
            intent.putExtra(getString(R.string.household_id), householdId);
            startActivity(intent);
            finish();
        }

    }
}
