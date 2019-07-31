package com.peryite.journeyd3.api;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.DBHelper.DAO.TaskDAO;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

import java.util.List;

public interface DataBaseApi {
    void clear();

    ChapterDAO getChapterDAO();

    TaskDAO getTaskDAO();

    void fillDataBase(List<Chapter> chapters);

    boolean isEmptyDataBase();

    List<Chapter> getAllChapters();

    int updateTask(Task task);

}
