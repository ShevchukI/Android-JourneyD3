package com.peryite.journeyd3.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class Reward implements Parcelable {
    private int id;
    private String name;
    private boolean done;

    public Reward(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public Reward(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
    }

    protected Reward(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.done = in.readByte() != 0;
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
}
