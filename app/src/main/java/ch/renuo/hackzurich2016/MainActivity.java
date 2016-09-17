package ch.renuo.hackzurich2016;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.UUID;

import ch.renuo.hackzurich2016.activities.AlarmActivity;
import ch.renuo.hackzurich2016.activities.HouseholdActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World 2");


        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String householdId = this.getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.household_id), null);
        if(householdId != null){
            goToHousehold(householdId);
        }
    }

    private void goToHousehold(String householdId) {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        String deviceId = prefs.getString(getString(R.string.device_id), null);
        if(deviceId == null){
            deviceId = UUID.randomUUID().toString();
            prefs.edit().putString(getString(R.string.device_id), deviceId).commit();
        }

        Intent intent = new Intent(this, HouseholdActivity.class);
        intent.putExtra(getString(R.string.device_id), deviceId);
        intent.putExtra(getString(R.string.household_id), householdId);
        startActivity(intent);
        finish();
    }

    public void onCreateHouseholdClick(View view){
        String householdId = UUID.randomUUID().toString();
        this.getPreferences(Context.MODE_PRIVATE).edit().putString(getString(R.string.household_id), householdId).commit();
        goToHousehold(householdId);
    }

    public void onAlarmClick(View view){
        Intent intent = new Intent(this, AlarmActivity.class);
//        intent.putExtra(getString(R.string.device_id), deviceId);
//        intent.putExtra(getString(R.string.household_id), householdId);
        startActivity(intent);
        finish();
    }
}
