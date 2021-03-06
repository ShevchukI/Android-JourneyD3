package com.peryite.journeyd3;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.contracts.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.presenters.ChapterFragmentPresenter;
import com.peryite.journeyd3.presenters.MainPresenter;
import com.peryite.journeyd3.utils.Constant;
import com.peryite.journeyd3.utils.LogTag;
import com.peryite.journeyd3.utils.Parser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    private final static int MAIN_CONTAINER_ID = R.id.container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tv_season_title)
    AppCompatTextView title;

    private MainContract.Presenter mainPresenter;
    private ChapterContract.Presenter chapterPresenter;
    private Fragment currentFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        Log.d(MainActivity.class.getSimpleName(), "onCreate: ");
        mainPresenter = new MainPresenter(this);
        chapterPresenter = new ChapterFragmentPresenter(FragmentManager.getInstance().getFragmentChapter());
        mainPresenter.start();
        new TitleLoader().execute();
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

    class TitleLoader extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String title = "";
            if (titleShareIsEmpty()) {
                saveTitleFromParser();
                title = getTitleFromShare();
            } else {
                title = getTitleFromShare();
            }
            return title;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String titleName) {
            super.onPostExecute(titleName);
            title.setText(titleName);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_chapter:
                mainPresenter.onClickChapter(item.getItemId());
                break;
            case R.id.nav_conquest:
                mainPresenter.onClickConquest(item.getItemId());
                break;
            case R.id.nav_reward:
                mainPresenter.onClickReward(item.getItemId());
                break;
            case R.id.nav_action_restart:
                mainPresenter.onClickRestart();
                break;
            case R.id.nav_action_update:
                mainPresenter.onClickUpdate();
                new TitleLoader().execute();
                break;
            case R.id.nav_credits:
                mainPresenter.onClickCredits(item.getItemId());
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public void resetChapter() {
        chapterPresenter.resetChapter();
    }

    @Override
    public void updateChapter() {
        chapterPresenter.updateChapter();
    }

    private void showTitle() {
        if (titleShareIsEmpty()) {
            saveTitleFromParser();
        }
        title.setText(getTitleFromShare());
    }

    private String getTitleFromShare() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedTitle = sharedPreferences.getString(Constant.SHARED_TITLE, "");
        return savedTitle;
    }

    private boolean titleShareIsEmpty() {
        if (getTitleFromShare().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void saveTitleFromParser() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        Editor ed = sharedPreferences.edit();
        String title = Parser.getInstance().getTitle();
        ed.putString(Constant.SHARED_TITLE, title);
        ed.apply();
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.RESULT, "saveJourney ");
    }
}
