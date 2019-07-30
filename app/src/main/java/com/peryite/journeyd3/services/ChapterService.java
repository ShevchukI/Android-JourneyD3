package com.peryite.journeyd3.services;

import com.peryite.journeyd3.api.ChapterApi;
import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.models.Task;

public class ChapterService implements ChapterApi {

    @Override
    public int getCountChapterDoneTask(Chapter chapter) {
        int count = 0;
        for(Task task : chapter.getTasks()){
            if(task.isComplete()){
                count++;
            }
        }
        return count;
    }

    @Override
    public int getCountChapterAllTask(Chapter chapter) {
        return chapter.getTasks().size();
    }
}
