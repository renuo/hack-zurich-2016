package ch.renuo.hackzurich2016;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.UUID;

import ch.renuo.hackzurich2016.activities.HouseholdActivity;
import ch.renuo.hackzurich2016.alarms.AlarmController;

public class MainActivity extends AppCompatActivity {

    public static final String PREFKEY = "com.renuo.hackzurich2016.prefs";
    public static final int REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World 2");
//        this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit().clear().commit();

        // Connect this to db
        new AlarmController(this).onUpdate();

        setContentView(R.layout.activity_main);

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.signOut();
//        if (auth.getCurrentUser() != null) {
//            String loginMessage = "Welcome " + auth.getCurrentUser().getEmail() + "!";
//            Toast.makeText(MainActivity.this, loginMessage, Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.activity_main);
//
//        } else {
//            startActivityForResult(
//                    AuthUI.getInstance().createSignInIntentBuilder()
//                            .setProviders(
//                                    AuthUI.EMAIL_PROVIDER,
//                                    AuthUI.GOOGLE_PROVIDER,
//                                    AuthUI.FACEBOOK_PROVIDER)
//                            .build(),
//                    AcquireEmailHelper.RC_SIGN_IN);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String householdId = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).getString(getString(R.string.household_id), null);
        if(householdId != null){
            goToHousehold(householdId, false);
        }
    }

    private void goToHousehold(String householdId, boolean create) {
        SharedPreferences prefs = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
        String deviceId = prefs.getString(getString(R.string.device_id), null);
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString();
            prefs.edit().putString(getString(R.string.device_id), deviceId).commit();
        }

        Intent intent = new Intent(this, HouseholdActivity.class);
        intent.putExtra(getString(R.string.device_id), deviceId);
        intent.putExtra(getString(R.string.household_id), householdId);
        intent.putExtra(getString(R.string.create_household), create);
        startActivity(intent);
        finish();
    }

    public void onCreateHouseholdClick(View view) {
        String householdId = UUID.randomUUID().toString();
        this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit().putString(getString(R.string.household_id), householdId).commit();
        goToHousehold(householdId, true);
    }

    //http://stackoverflow.com/questions/8831050/android-how-to-read-qr-code-in-my-application
    public void onJoinHouseholdClick(View view) {
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, REQUEST_CODE);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
        }
    }

    //http://stackoverflow.com/questions/8831050/android-how-to-read-qr-code-in-my-application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                goToHousehold(contents, false);
            }
            if (resultCode == RESULT_CANCELED) {
                //handle cancel
            }
        }
    }
}
