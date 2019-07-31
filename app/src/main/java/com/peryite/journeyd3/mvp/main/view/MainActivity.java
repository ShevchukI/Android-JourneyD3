package com.peryite.journeyd3.mvp.main.view;

import android.content.SharedPreferences;
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

import com.peryite.journeyd3.DBHelper.DataBaseConverter;
import com.peryite.journeyd3.R;
import com.peryite.journeyd3.api.DataBaseApi;
import com.peryite.journeyd3.exception.SwitchDefaultException;
import com.peryite.journeyd3.mvp.chapter.contract.ChapterContract;
import com.peryite.journeyd3.mvp.main.contract.MainContract;
import com.peryite.journeyd3.managers.FragmentManager;
import com.peryite.journeyd3.mvp.chapter.presenter.ChapterFragmentPresenter;
import com.peryite.journeyd3.mvp.main.presenter.MainPresenter;
import com.peryite.journeyd3.utils.Constant;
import com.peryite.journeyd3.utils.LogTag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Unbinder unbinder;

    private MainContract.Presenter presenter;
    private ChapterContract.Presenter chapterPresenter;
    private SharedPreferences sharedPreferences;
    private DataBaseApi dataBaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initViews();
        Log.d(MainActivity.class.getSimpleName(), "onCreate: ");
        sharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, MODE_PRIVATE);
        presenter = new MainPresenter(this, sharedPreferences);
        presenter.start();
    }

    @Override
    public void start() {
        Log.d(LogTag.INFORMATION, "Info: show main fragment.");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MAIN_CONTAINER_ID, FragmentManager.getInstance().getFirstFragment());
        transaction.commit();

        navigationView.setCheckedItem(R.id.nav_chapter);

        dataBaseApi = DataBaseConverter.getInstance(this);
        chapterPresenter = new ChapterFragmentPresenter(FragmentManager.getInstance().getFragmentChapter(), dataBaseApi);

        presenter.showTitle();
    }

    @Override
    public void selectFragment(Fragment fragment, int id_navigator) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(MAIN_CONTAINER_ID, fragment);
        transaction.commit();

        navigationView.setCheckedItem(id_navigator);
    }

    @Override
    public void showTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void restartChapter() {
        chapterPresenter.restart();
    }

    @Override
    public void updateChapter() {
        chapterPresenter.update();
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
            default:
                try {
                    new SwitchDefaultException("Navigation menu: default switch case!");
                } catch (SwitchDefaultException e) {
                    e.getMessage();
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        unbinder.unbind();
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
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
