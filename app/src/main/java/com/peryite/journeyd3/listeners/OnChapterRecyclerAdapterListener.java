package com.peryite.journeyd3.listeners;

import com.peryite.journeyd3.models.Chapter;
import com.peryite.journeyd3.viewHolders.parent.ChapterViewHolder;

public interface OnChapterRecyclerAdapterListener {
    void notifyParentByPosition(int parentPosition);
}
