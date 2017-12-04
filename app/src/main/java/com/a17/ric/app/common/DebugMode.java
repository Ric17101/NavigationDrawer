package com.a17.ric.app.common;

import android.content.Context;
import android.util.Log;
import android.view.Menu;

import com.a17.ric.app.common.session.UserSessionManager;

import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_PROJECTJOB;


/**
 * Created by firstcom on 23/10/2017.
 * For Debugging purposes
 */

public class DebugMode {

    public static final String TAG = DebugMode.class.getSimpleName();

    // in some.class.with.Constants
    public static final boolean DEV_MODE = true;
    private static final int DEV_TAB = 5;

    public DebugMode() { }

    public static int getDebugModeTAB() {
        return DEV_TAB;
    }

    public boolean DebugMode() {
        if (DEV_MODE) {
            Log.d(TAG, "somemessage");
        }

        return false;
    }

    /**
     * Set INVISIBLE ProjectJobs TAB when you are Logged in as DEV
     * NAVIGATION Drawer Item ProjectJob will be visible only when logged in as Administrator/ID:1
     * @param context - Current Context or the Activity itself
     * @param menu - Menu Drawer Items
     */
    public static void setDebugModeDrawer(Context context, Menu menu) {
        if (!isAdministrator(context))
            menu.getItem(NAVIGATION_DRAWER_SELECTED_PROJECTJOB).setVisible(false);
    }

    /*
        TO TEST if current user is the Dev
     */
    public static boolean isAdministrator(Context context) {
        String userID = UserSessionManager.getUserID(context);
        if (userID == null)
            return false;
        if (userID.equals("1"))
            return true;

        return false;
    }
}
