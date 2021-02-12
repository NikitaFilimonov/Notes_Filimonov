package com.example.notes.note;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class Note implements Parcelable {
    private String id;
    private String name;
    private String text;
    private boolean like;
    private Date dateCreated;

    public Note(String name, String text, boolean like, Date dateCreated) {
        this.name = name;
        this.text = text;
        this.like = like;
        this.dateCreated = dateCreated;
    }

    protected Note(Parcel in) {
        name = in.readString();
        text = in.readString();
        like = in.readByte() != 0;
        dateCreated = new Date(in.readLong());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isLike() {
        return like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getText());
        parcel.writeByte((byte) (like ? 1 : 0));
        parcel.writeLong(getDateCreated().getTime());
    }

    public Date getDateCrated() {
        return dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
