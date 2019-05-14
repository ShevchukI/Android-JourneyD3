package com.peryite.journeyd3.tools;

import android.util.Log;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.ChapterTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ParserDocument {

    private final static String URL = "https://d3resource.com/journey/";
    private Document document;

    public ParserDocument() {
        try {
            this.document = Jsoup.connect(URL).get();
            Log.d(LogTag.RESULT, "document connected by URL");
        } catch (IOException e) {
            Log.d(LogTag.ERROR, "Error: " + e.getMessage() + "\n");
        }
    }

    public ArrayList<Chapter> getChaptersAndTasksArray() {
        Elements headers = document.getElementsByClass("header");
        ArrayList<Chapter> chapters = new ArrayList<>();
        ArrayList<ChapterTask> chapterTasks = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            chapterTasks.clear();
            chapters.add(new Chapter(headers.get(i).text()));
            chapters.get(i).setTasks(getTasksByChapter(i + 1));
        }
        Log.d(LogTag.RESULT, "get Chapters from document");
        return chapters;
    }

    private ArrayList<ChapterTask> getTasksByChapter(int chapterNumber) {
        ArrayList<ChapterTask> chapterTasks = new ArrayList<>();
        Elements tasks = document.getElementsByClass("cat" + (chapterNumber)).not(".rewards");
        for (Element task : tasks) {
            task.text(task.text().replace("- ", ""));
            chapterTasks.add(new ChapterTask(task.text(), false));
        }
        return chapterTasks;
    }
}
