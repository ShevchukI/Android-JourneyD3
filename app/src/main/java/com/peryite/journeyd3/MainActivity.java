package com.peryite.journeyd3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.peryite.journeyd3.contracts.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.presenters.MainPresenter;
import com.peryite.journeyd3.utils.LogTag;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    private final static int MAIN_CONTAINER_ID = R.id.container;
    private final static String SAVED_FRAGMENT = "saved_fragment";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private MainContract.Presenter presenter;

    private Fragment currentFragment;

    private SharedPreferences sharedPreferences;
    private final static String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        Log.d(MainActivity.class.getSimpleName(), "onCreate: ");
        presenter = new MainPresenter(this);
        presenter.start();

    }


    private void initViews() {
        Log.d(MainActivity.class.getSimpleName(), "initViews: started");

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Log.d(MainActivity.class.getSimpleName(), "initViews: finished");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_chapter:
                presenter.onClickChapter(item.getItemId());
                break;
            case R.id.nav_conquest:
                presenter.onClickConquest(item.getItemId());
                break;
            case R.id.nav_reward:
                presenter.onClickReward(item.getItemId());
                break;
            case R.id.nav_action_restart:
                presenter.onClickRestart();
                break;
            case R.id.nav_action_update:
                presenter.onClickUpdate();
                break;
            case R.id.nav_credits:
                presenter.onClickCredits(item.getItemId());
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.RESULT, "saveJourney ");
//        saveJourney();
    }


    private void restartJourney() {

    }

    @Override
    public void showMainFragment() {
        Log.d(LogTag.INFORMATION, "Info: show main fragment.");
        currentFragment = FragmentManager.getInstance().getFirstFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MAIN_CONTAINER_ID, currentFragment);
        transaction.commit();
        navigationView.setCheckedItem(R.id.nav_chapter);

    }

    @Override
    public void selectFragment(Fragment fragment, int id_navigator) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MAIN_CONTAINER_ID, fragment);
        transaction.commit();
        navigationView.setCheckedItem(id_navigator);
        currentFragment = fragment;
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
