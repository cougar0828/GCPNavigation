package com.cougar.gcpnavigation.main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.functions.HelpFeedback;
import com.cougar.gcpnavigation.functions.JourneyList;
import com.cougar.gcpnavigation.functions.JourneyLobby;
import com.cougar.gcpnavigation.functions.NewJourney;

public class MainActivity extends Activity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Fragment mFragment = null;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initUI();
    }

    private void initUI() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position) {

            case 0:
                mFragment = new JourneyLobby();
                break;
            case 1:
                mFragment = new NewJourney();
                break;
            case 2:
                mFragment = new JourneyList();
                break;
            case 3:
                mFragment = new HelpFeedback();
                break;
        }
        if (mFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mFragment).commit();
        }
    }

    public void onSectionAttached(int number) {

        String[] drawerMenu = getResources().getStringArray(
                R.array.arr_drawerMenu);

        if (number >= 1) {
            mTitle = drawerMenu[number - 1];
            Log.d(TAG, "Title : " + mTitle);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

//    public void toSettings(View v) {
//        Toast.makeText(this, "To Settings", Toast.LENGTH_LONG).show();
//    }
//
//    public void toHelp(View v) {
//        Toast.makeText(this, "To Help", Toast.LENGTH_LONG).show();
//    }
}
