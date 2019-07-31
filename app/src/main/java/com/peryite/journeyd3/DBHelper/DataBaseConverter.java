package com.peryite.journeyd3.DBHelper;

import android.content.Context;

import com.peryite.journeyd3.DBHelper.DAO.ChapterDAO;
import com.peryite.journeyd3.DBHelper.DAO.TaskDAO;
import com.peryite.journeyd3.api.DataBaseApi;
import com.peryite.journeyd3.entities.ChapterEntity;
import com.peryite.journeyd3.entities.TaskEntity;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

import java.util.ArrayList;
import java.util.List;

public class DataBaseConverter implements DataBaseApi {
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

    @Override
    public ChapterDAO getChapterDAO() {
        return chapterDAO;
    }

    @Override
    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    @Override
    public void fillDataBase(List<Chapter> chapters) {
        for (Chapter chapter : chapters) {
            insertChapter(chapter);
        }
    }

    @Override
    public void clear() {
        chapterDAO.deleteAll();
    }

    @Override
    public List<Chapter> getAllChapters() {
        List<Chapter> chapters = new ArrayList<>();
        List<ChapterEntity> chapterEntities = getChapterDAO().getAllEntity();
        for (ChapterEntity chapterEntity : chapterEntities) {
            Chapter chapter = getChapterFromEntity(chapterEntity);
            chapters.add(chapter);
        }
        return chapters;
    }

    @Override
    public int updateTask(Task task){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setName(task.getName());
        taskEntity.setComplete(task.isComplete());
        return getTaskDAO().update(taskEntity);
    }

    @Override
    public boolean isEmptyDataBase(){
        if(getChapterDAO().getAllEntity().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private Chapter getChapterFromEntity(ChapterEntity chapterEntity){
        Chapter chapter = new Chapter();
        chapter.setId(chapterEntity.getId());
        chapter.setName(chapterEntity.getName());
        List<Task> tasks = new ArrayList<>();
        List<TaskEntity> taskEntities = getTaskDAO().getAllEntityByChapterId(chapter.getId());
        for (TaskEntity taskEntity : taskEntities) {
            Task task = getTaskFromEntity(taskEntity);
            tasks.add(task);
        }
        chapter.setTasks(tasks);
        return chapter;
    }

    private Task getTaskFromEntity(TaskEntity taskEntity){
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setName(taskEntity.getName());
        task.setComplete(taskEntity.isComplete());
        return task;
    }

    private long insertChapter(Chapter chapter) {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setName(chapter.getName());
        long id = chapterDAO.insert(chapterEntity);
        if (chapter.getTasks().isEmpty()) {
            return id;
        } else {
            for (Task task : chapter.getTasks()) {
                task.setId(insertTask(task, id));
            }
            return id;
        }
    }

    private long insertTask(Task task, long chapterId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        taskEntity.setComplete(task.isComplete());
        taskEntity.setChapterId(chapterId);
        long id = taskDAO.insert(taskEntity);
        return id;
    }
}
