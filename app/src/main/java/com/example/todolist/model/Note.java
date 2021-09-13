package com.example.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "text")
    public  String text;

//    @ColumnInfo(name = "timestamp")
//    public Long timestamp;
//
//    @ColumnInfo(name = "done")
//    public boolean done;

    public Note() {
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return uid == note.uid &&
                Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Note(Parcel in){
        uid = in.readInt();
        text = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeInt(uid);
        dest.writeString(text);
    }
}
