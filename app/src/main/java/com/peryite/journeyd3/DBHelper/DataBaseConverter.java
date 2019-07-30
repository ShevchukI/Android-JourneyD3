package com.peryite.journeyd3.DBHelper;

import android.content.Context;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.DBHelper.DAO.TaskDAO;
import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

import java.util.List;

public class DataBaseConverter {
    private static DataBaseConverter ourInstance = null;

    private Context context;

    public static DataBaseConverter getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DataBaseConverter(context);
        }
        return ourInstance;
    }


    private ChapterDAO chapterDAO;
    private TaskDAO taskDAO;

    private DataBaseConverter(Context context) {
        this.context = context;
        chapterDAO = JourneyDB.getInstance(context.getApplicationContext()).chapterDAO();
        taskDAO = JourneyDB.getInstance(context.getApplicationContext()).taskDAO();
    }

    public ChapterDAO getChapterDAO() {
        return chapterDAO;
    }

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public void fillDataBase(List<Chapter> chapters){
        for(Chapter chapter : chapters){
            insertChapter(chapter);
        }
    }

    private long insertChapter(Chapter chapter){
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setName(chapter.getName());
        long id = chapterDAO.insert(chapterEntity);
        if(chapter.getTasks().isEmpty()) {
            return id;
        } else {
            for(Task task : chapter.getTasks()){
                task.setId(insertTask(task, id));
            }
            return id;
        }
    }

    private long insertTask(Task task, long chapterId){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        taskEntity.setComplete(task.isComplete());
        taskEntity.setChapterId(chapterId);
        long id = taskDAO.insert(taskEntity);
        return id;
    }

    public void clearDataBase(){
        chapterDAO.deleteAll();
    }

}
