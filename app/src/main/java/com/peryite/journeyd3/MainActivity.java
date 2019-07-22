package com.peryite.journeyd3;

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

import com.peryite.journeyd3.contracts.ChapterContract;
import com.peryite.journeyd3.contracts.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.presenters.ChapterFragmentPresenter;
import com.peryite.journeyd3.presenters.MainPresenter;
import com.peryite.journeyd3.utils.LogTag;

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

    private MainContract.Presenter mainPresenter;
    private ChapterContract.Presenter chapterPresenter;
    private Fragment currentFragment;

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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LogTag.RESULT, "saveJourney ");
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

}
