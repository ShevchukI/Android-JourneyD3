package com.peryite.journeyd3;

import android.content.SharedPreferences;
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
import android.widget.LinearLayout;

import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.fragments.FragmentChapter;
import com.peryite.journeyd3.fragments.FragmentConquest;
import com.peryite.journeyd3.fragments.FragmentCredits;
import com.peryite.journeyd3.fragments.FragmentReward;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.utils.AppAllComponent;
import com.peryite.journeyd3.utils.LogTag;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
//        FragmentConquest.OnFragmentInteractionListener,
//        FragmentReward.OnFragmentInteractionListener,
//        FragmentCredits.OnFragmentInteractionListener
{
//    @Inject
    private FragmentChapter fragmentChapter;
    private FragmentConquest fragmentConquest;
    private FragmentReward fragmentReward;
    private FragmentCredits fragmentCredits;
    private DBHelper dbHelper;

    private SharedPreferences sharedPreferences;
    private final static String TITLE = "title";
    private List<Chapter> chapterList;

    private LinearLayout chapterLinear;

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

        FragmentManager.getInstance().initFragments();

        fragmentChapter = FragmentManager.getInstance().getFragmentChapter();
        fragmentConquest = FragmentManager.getInstance().getFragmentConquest();
        fragmentCredits = FragmentManager.getInstance().getFragmentCredits();
        fragmentReward = FragmentManager.getInstance().getFragmentReward();

//        fragmentChapter = new FragmentChapter();
//        fragmentConquest = new FragmentConquest();
//        fragmentReward = new FragmentReward();
//        fragmentCredits = new FragmentCredits();
//
//
//
//        dbHelper = new DBHelper(this);
//
//
//
//        if (dbHelper.checkRecords()) {
//            Log.d(LogTag.INFORMATION, "database not empty");
//            chapterList = dbHelper.getAllChapters();
//            fragmentChapter = FragmentChapter.newInstance(chapterList);
//        } else {
//            Log.d(LogTag.INFORMATION, "database empty");
////            updateJourney();
//        }

//        if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        fragmentChapter).commit();
        navigationView.setCheckedItem(R.id.nav_chapter);
//        }

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chapter) {
            Log.d(LogTag.CLICK, "Click: chapter button ");

            if (chapterLinear != null) {
                chapterLinear.removeAllViews();
            }
            fragmentChapter = fragmentChapter.newInstance(chapterList);
            fragmentTransaction.replace(R.id.container, fragmentChapter);
        } else if (id == R.id.nav_conquest) {
            Log.d(LogTag.CLICK, "Click: conquest button ");
            fragmentTransaction.replace(R.id.container, fragmentConquest);
        } else if (id == R.id.nav_reward) {
            Log.d(LogTag.CLICK, "Click: reward button ");
//            fragmentReward = FragmentReward.newInstance("test newInstance");
            fragmentTransaction.replace(R.id.container, fragmentReward);
        } else if (id == R.id.nav_action_restart) {
            Log.d(LogTag.CLICK, "Click: restart button ");
        } else if (id == R.id.nav_action_update) {
            Log.d(LogTag.CLICK, "Click: update button ");
//            updateJourney();
        } else if (id == R.id.nav_credits) {
            Log.d(LogTag.CLICK, "Click: credits button ");
            fragmentTransaction.replace(R.id.container, fragmentCredits);
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.RESULT, "saveJourney ");
//        saveJourney();
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//        Log.d(LogTag.RESULT, "onFragmentInteraction");
//    }

    private void restartJourney() {

    }

//    private void updateJourney() {
//        new Thread(() -> {
//            Parser parser = new Parser();
//            chapterList = parser.getChaptersAndTasksArray();
//            dbHelper.deleteAllRecords();
//            dbHelper.fillDatabase(chapterList);
//            fragmentChapter = FragmentChapter.newInstance(chapterList);
//            Log.d(LogTag.RESULT, "updateJourney: updated!");
//            for (Chapter chapter:chapterList){
//                Log.d(LogTag.RESULT, "updateJourney: " + chapter.getName());
//            }
//        }).start();
//
//    }

//    private void parsingData() {
//        Parser parser = new Parser();
//        saveTitle(parser.getTitle());
//
//    }

//    private void saveTitle(String title) {
//        sharedPreferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(TITLE, title);
//        editor.apply();
//    }
//
//    private String loadTitle() {
//        sharedPreferences = getPreferences(MODE_PRIVATE);
//        return sharedPreferences.getString(TITLE, "");
//    }

//    private void saveJourney() {
//        dbHelper.updateChapter(chapterList);
//        Log.d(LogTag.RESULT, "DataBase updated!");
//    }

}
