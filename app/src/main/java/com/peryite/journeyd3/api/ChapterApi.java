package com.peryite.journeyd3.api;

import com.peryite.journeyd3.models.Chapter;

public interface ChapterApi {
    int getCountChapterDoneTask(Chapter chapter);
    int getCountChapterAllTask(Chapter chapter);

}
