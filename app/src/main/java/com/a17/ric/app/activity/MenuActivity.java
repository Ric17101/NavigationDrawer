package com.a17.ric.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.a17.ric.app.R;
import com.a17.ric.app.common.DebugMode;
import com.a17.ric.app.fragment.PrimaryFragment;
import com.a17.ric.app.fragment.SentFragment;
import com.a17.ric.app.fragment.SocialFragment;
import com.a17.ric.app.fragment.UnderMaintenanceFragment;

import static com.a17.ric.app.common.Constant.LANDING_PAGE_ACTIVE_KEY;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_PROJECTJOB;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B1;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B2;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B3;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_SERVICEJOB;
import static com.a17.ric.app.common.Constant.NAVIGATION_DRAWER_SELECTED_TOOLBOX;

/**
 * Created by firstcom on 12/4/2017.
 *
 */

public class MenuActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MenuActivity.class.getSimpleName();

    private MenuItem mCurrentItem;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private NavigationView mNavigationUserView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init_DrawerNav();
    }

    /**
     * Can be used to title the TAB on the ViewPagerActivity
     * By using the MODE CONSTANT of modeOfForm
     */
    private int fromBundleToActiveNavigation() {
        Intent intent = getIntent();
        // return intent.getIntExtra(LANDING_PAGE_ACTIVE_KEY, 0);
        return intent.getIntExtra(LANDING_PAGE_ACTIVE_KEY, (DebugMode.isAdministrator(this) ? DebugMode.getDebugModeTAB() : 0));
    }

    private Fragment initActiveFragment() {

        // Need to set the Current Item Selected on the Navigation
        mCurrentItem = mNavigationView.getMenu().getItem(fromBundleToActiveNavigation());
        String title = mCurrentItem.getTitle().toString();
        Fragment activeFragment;
        switch (fromBundleToActiveNavigation()) {
            case NAVIGATION_DRAWER_SELECTED_SERVICEJOB :
//                activeFragment = new ServiceJobFragmentTab();
//                break;
            default:
            case NAVIGATION_DRAWER_SELECTED_PROJECTJOB :
            case NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B1 :
            case NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B2 :
            case NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B3 :
                activeFragment = SentFragment.newInstance(title);
                break;
            case NAVIGATION_DRAWER_SELECTED_TOOLBOX :
                activeFragment = new SocialFragment();
                break;
        }
        return activeFragment;
    }

    /**
     * Return the Current Item selected on the Navigation Drawer
     * @return
     */
    private int getCurrentNavSelected() {

        int navItemSelected = 0;
        switch (mCurrentItem.getItemId()) {
            case R.id.nav_servicejobs :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_SERVICEJOB;
                break;
            case R.id.nav_projectjobs :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_PROJECTJOB;
                break;
            case R.id.nav_checklist :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B1;
                break;
            case R.id.nav_process_pw :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B2;
                break;
            case R.id.nav_process_eps :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_PROJECTJOB_B3;
                break;
            case R.id.nav_toolbox :
                navItemSelected = NAVIGATION_DRAWER_SELECTED_TOOLBOX;
                break;
        }
        return navItemSelected;
    }

    private void init_DrawerNav() {
        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationUserView = (NavigationView) findViewById(R.id.nav_view_user);

        /**
         * Lets inflate the very first fragmentType
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, initActiveFragment()).commit(); // TO RENDER THE  1st TAB on MAIN MENU

        /**
         * Setup click events on the Navigation View Items.
         */
        mNavigationView.getMenu().getItem(fromBundleToActiveNavigation()).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);

        /**
         * Setup click events on the User Navigation View Items.
         */
        mNavigationUserView.setNavigationItemSelectedListener(this);

        /**
         * Setup Drawer Toggle of the Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        // For Debugging Only if you are Dev
        DebugMode.setDebugModeDrawer(MenuActivity.this, mNavigationView.getMenu());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers(); // Close Nav Draw after onClick of Tabs

        if (mCurrentItem != null)
            if (mCurrentItem == menuItem) { // To just retain the current Fragment
                menuItem.setChecked(true); // Set Active Tab
                return true;
            }

        mCurrentItem = menuItem; // To Remember the Last MenuItem Being Selected
        menuItem.setChecked(true); // Set Active Tab

        switch (menuItem.getItemId()) {
            case R.id.nav_signout:
                // logout();
                break;
        }

        switch (menuItem.getItemId()) {
            case R.id.nav_servicejobs :
                FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                transaction1.replace(R.id.containerView, new PrimaryFragment()).commit();
                break;
            case R.id.nav_projectjobs :
                FragmentTransaction transaction2 = mFragmentManager.beginTransaction();
                transaction2.replace(R.id.containerView, new SentFragment()).commit();
                break;
            case R.id.nav_checklist :
                FragmentTransaction transaction3 = mFragmentManager.beginTransaction();
                transaction3.replace(R.id.containerView, new SocialFragment()).commit();
                break;
            case R.id.nav_process_pw :
                FragmentTransaction transaction4 = mFragmentManager.beginTransaction();
                transaction4.replace(R.id.containerView, new SentFragment()).commit();
                break;
            case R.id.nav_process_eps :
                FragmentTransaction transaction5 = mFragmentManager.beginTransaction();
                transaction5.replace(R.id.containerView, new UnderMaintenanceFragment()).commit();
                break;
            case R.id.nav_toolbox :
                FragmentTransaction transaction6 = mFragmentManager.beginTransaction();
                transaction6.replace(R.id.containerView,
                        new SocialFragment(), SocialFragment.TAG).commit();
                mFragmentTransaction = transaction6;
                break;
        }
        return false;
    }
}
