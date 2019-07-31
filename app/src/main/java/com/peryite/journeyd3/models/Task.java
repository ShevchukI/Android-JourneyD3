package com.peryite.journeyd3.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task implements Parcelable {
    private long id;
    private String name;
    private boolean complete;

    public Task(String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.complete = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
