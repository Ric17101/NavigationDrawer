package com.a17.ric.app.common.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UserSessionManager {

    private static final String TAG = UserSessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    private Context _context;
    private static UserSessionManager mInstance;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_ID = "id";

    // Constructor
    private UserSessionManager(Context context) {
        this._context = context;
        if (_context == null) ;
        else {
            pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            // Log.e(TAG, "pref.contains(PREF_NAME)=" + pref.contains(PREF_NAME));
            editor = pref.edit();
        }
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserSessionManager(context);
        }
        return mInstance;
    }

    // public set

    /**
     * Create login mSession
     */
    public void createLoginSession(String name, String email, String id) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, null/*LoginActivity2.class*/);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored mSession data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

        // return user
        return user;
    }

    public static String getUserID(Context mContext) {
        UserSessionManager mSession = UserSessionManager.getInstance(mContext);
        String user_id = mSession.getUserDetails().get(UserSessionManager.KEY_USER_ID);
        return user_id;
    }

    public void clearPrefs() {
        // Clearing all data from Shared Preferences
        // if (editor == null) return;
        editor.clear();
        editor.commit();
    }

    /**
     * Clear mSession details
     */
    public void logoutUser() {

        clearPrefs();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, null/*LoginActivity2.class*/);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        // ((Activity)_context).overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        // if (pref == null) return false;
        return pref.getBoolean(IS_LOGIN, false);
    }
}
