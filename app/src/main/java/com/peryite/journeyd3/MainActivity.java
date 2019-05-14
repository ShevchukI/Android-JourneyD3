package com.peryite.journeyd3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.peryite.journeyd3.DBHelper.DBHelper;
import com.peryite.journeyd3.fragments.ChapterFragment;
import com.peryite.journeyd3.tools.LogTag;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnStart;
//    private LinearLayout mainLL;
//    private String[] chapters;
//

    private DBHelper dbHelper;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        dbHelper = new DBHelper(this);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(DBHelper.NAME, dbHelper);
//        ChapterFragment chapterFragment = new ChapterFragment();
//        chapterFragment.setArguments(bundle);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.main_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChapterFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_chapter);
        }




//        Intent intent = new Intent(this, ChapterFragment.class);
//        intent.putExtra(DBHelper.NAME, dbHelper);
//        startActivity(intent);
//        btnStart =  findViewById(R.id.btnStart);
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LogTag.RESULT, "btnStart pressed!");
//            }
//        });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                createViewChaptersAndTasks();
//            }
//        });
    }

//    private void createViewChaptersAndTasks() {
//        mainLL = (LinearLayout) findViewById(R.id.mainLinearLayout);
//        LinearLayout.LayoutParams layoutParamsChapter
//                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParamsChapter.gravity = Gravity.CENTER;
//
//        LinearLayout.LayoutParams layoutParamsChapterTask
//                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParamsChapterTask.gravity = Gravity.LEFT;
//
//        chapterList = dbHelper.getAllChapters();
//
//        for (Chapter chapter : chapterList) {
//            TextView tvChapter = new TextView(this);
//            tvChapter.setId(chapter.getId());
//            tvChapter.setText(chapter.getName());
//            mainLL.addView(tvChapter, layoutParamsChapter);
//            for (ChapterTask chapterTask : chapter.getTasks()) {
//                final CheckBox cbChapterTask = new CheckBox(this);
//                cbChapterTask.setId(chapterTask.getId());
//                cbChapterTask.setText(chapterTask.getName());
//                if (chapterTask.isDone()) {
//                    cbChapterTask.setChecked(true);
//                } else {
//                    cbChapterTask.setChecked(false);
//                }
//                cbChapterTask.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (Chapter chapter : chapterList) {
//                            for (ChapterTask chapterTask : chapter.getTasks()) {
//                                if (cbChapterTask.getId() == chapterTask.getId()) {
//                                    chapterTask.setDone(cbChapterTask.isChecked());
////                                    Log.d(LogTag.RESULT, "ID: " + chapterTask.getId()
////                                            + " Name: " + chapterTask.getName() + " Done: " + chapterTask.isDone()
////                                            + " ChBox: " + cbChapterTask.isChecked());
//                                    return;
//                                }
//                            }
//                        }
//                    }
//                });
//                mainLL.addView(cbChapterTask, layoutParamsChapterTask);
//            }
//        }
//
//    }
//

//
//    private String[] getChaptersAndTasksArray(Document document) {
//        Elements headers = document.getElementsByClass("header");
//        String[] chapter = new String[headers.size()];
//        for (int i = 0; i < chapter.length; i++) {
//            chapter[i] = headers.get(i).text();
//            Elements tasks = document.getElementsByClass("cat" + (i + 1)).not(".rewards");
//            for (Element task : tasks) {
//                task.text(task.text().replace("- ", ""));
//                chapter[i] += "\n" + task.text();
//            }
//        }
//        return chapter;
//    }


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
//        saveJourney();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_chapter:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChapterFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void saveJourney() {
//        dbHelper.updateChapter(chapterList);
//    }
}
