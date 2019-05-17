package com.peryite.journeyd3;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.fragments.FragmentChapter;
import com.peryite.journeyd3.fragments.FragmentConquest;
import com.peryite.journeyd3.fragments.FragmentCredits;
import com.peryite.journeyd3.fragments.FragmentReward;
import com.peryite.journeyd3.tools.LogTag;
import com.peryite.journeyd3.tools.Parser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentChapter.OnFragmentInteractionListener,
        FragmentConquest.OnFragmentInteractionListener,
        FragmentReward.OnFragmentInteractionListener,
        FragmentCredits.OnFragmentInteractionListener {

    private FragmentChapter fragmentChapter;
    private FragmentConquest fragmentConquest;
    private FragmentReward fragmentReward;
    private FragmentCredits fragmentCredits;
    private DBHelper dbHelper;

    private SharedPreferences sharedPreferences;
    private final static String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentChapter = new FragmentChapter();
        fragmentConquest = new FragmentConquest();
        fragmentReward = new FragmentReward();
        fragmentCredits = new FragmentCredits();

        dbHelper = new DBHelper(this);

        if(dbHelper.checkRecords()){
            Log.d(LogTag.INFORMATION, "database not empty");

        } else {
            Log.d(LogTag.INFORMATION, "database empty");
            Parser parser = new Parser();

        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    fragmentChapter).commit();
            navigationView.setCheckedItem(R.id.nav_chapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chapter) {
            fragmentTransaction.replace(R.id.container, fragmentChapter);
        } else if (id == R.id.nav_conquest) {
            fragmentTransaction.replace(R.id.container, fragmentConquest);
        } else if (id == R.id.nav_reward) {
            fragmentReward = FragmentReward.newInstance("test newInstance");
            fragmentTransaction.replace(R.id.container, fragmentReward);
        } else if (id == R.id.nav_action_restart) {

        } else if (id == R.id.nav_action_update) {

        } else if (id == R.id.nav_credits) {
            fragmentTransaction.replace(R.id.container, fragmentCredits);
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(LogTag.RESULT, "onFragmentInteraction");
    }

    @Override
    public void onFragmentInteraction(String text) {
        Log.d(LogTag.RESULT, text);
    }

    private void restartJourney(){

    }

    private void updateJourney(){

    }

    private void parsingData(){
        Parser parser = new Parser();
        saveTitle(parser.getTitle());

    }

    private void saveTitle(String title){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TITLE, title);
        editor.commit();
    }

    private String loadTitle(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getString(TITLE, "");
    }
}
