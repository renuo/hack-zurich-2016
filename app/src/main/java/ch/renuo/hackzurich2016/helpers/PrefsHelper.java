package ch.renuo.hackzurich2016.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ch.renuo.hackzurich2016.R;

public class PrefsHelper {
    public static final String PREFKEY = "com.renuo.hackzurich2016.prefs";
    private final Context _context;
    private SharedPreferences _prefs;

    public PrefsHelper(Context context) {
        this._context = context;
        this._prefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public String getDeviceId() {
        return _prefs.getString(_context.getString(R.string.device_id), null);
    }

    public String getHouseholdId() {
        return _prefs.getString(_context.getString(R.string.household_id), null);
    }

    public SharedPreferences.Editor edit() {
       return _prefs.edit();
    }
}
