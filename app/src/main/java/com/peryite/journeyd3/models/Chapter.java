package com.peryite.journeyd3.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Chapter implements Parent<Task>, Parcelable {
    @ColumnInfo(name = "_id")
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @Ignore
    private List<Task> tasks;
    @Ignore
    private Reward reward;

    public Chapter(String name) {
        this.name = name;
    }

    @Override
    public List<Task> getChildList() {
        return tasks;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.tasks);
        dest.writeParcelable(this.reward, flags);
    }

    protected Chapter(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.tasks = in.createTypedArrayList(Task.CREATOR);
        this.reward = in.readParcelable(Reward.class.getClassLoader());
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
