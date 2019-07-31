package com.peryite.journeyd3.utils;

import android.util.Log;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Reward;
import com.peryite.journeyd3.models.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private static final Parser ourInstance = new Parser();

    private final static String URL = "https://d3resource.com/journey/";
    private Document document;

    public static Parser getInstance() {
        return ourInstance;
    }

    private Parser() {
        if (document == null) {
            while (document == null) {
                document = getDocument();
            }
        }
    }

    private Document getDocument() {
        try {
            document = Jsoup.connect(URL).get();
            Log.d(LogTag.RESULT, "document connected by URL");
        } catch (IOException e) {
            Log.d(LogTag.ERROR, "Error: " + e.getMessage() + "\n");
        } finally {
            return document;
        }
    }

    private ArrayList<Reward> getAllRewards() {
        ArrayList<Reward> rewards = new ArrayList<>();
        Elements elementRewards = document.getElementsByClass("rewards");
        for (Element reward : elementRewards) {
            rewards.add(new Reward(reward.text(), false));
        }
        return rewards;
    }

    public ArrayList<Chapter> getChaptersAndTasksArray() {
        Elements headers = document.getElementsByClass("header");
        ArrayList<Chapter> chapters = new ArrayList<>();
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            tasks.clear();
            chapters.add(new Chapter(headers.get(i).text()));
            chapters.get(i).setTasks(getTasksByChapter(i + 1));
        }
        ArrayList<Reward> rewards = getAllRewards();
        for (int i = 0; i < chapters.size(); i++) {
            chapters.get(i).setReward(rewards.get(i));
        }
        Log.d(LogTag.RESULT, "get Chapters from document");
        return chapters;
    }

    private ArrayList<Task> getTasksByChapter(int chapterNumber) {
        ArrayList<Task> chapterTasks = new ArrayList<>();
        Elements tasks = document.getElementsByClass("cat" + (chapterNumber)).not(".rewards");
        for (Element task : tasks) {
            task.text(task.text().replace("- ", ""));
            chapterTasks.add(new Task(task.text(), false));
        }
        return chapterTasks;
    }

    public String getTitle() {
        return document.title();
    }

}
