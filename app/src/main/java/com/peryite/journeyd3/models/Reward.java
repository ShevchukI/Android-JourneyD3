package com.peryite.journeyd3.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Reward implements Parcelable {
    @ColumnInfo(name = "_id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "complete")
    private boolean complete;

    public Reward() {
    }

    @Ignore
    public Reward(String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    @Ignore
    public Reward(long id) {
        this.id = id;
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

    protected Reward(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.complete = in.readByte() != 0;
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel source) {
            return new Reward(source);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
