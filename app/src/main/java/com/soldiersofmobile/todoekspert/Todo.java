package com.soldiersofmobile.todoekspert;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {
 
 
    //"content":"empty todo" + 
    //public Date createdAt;//":"2015-05-23T16:29:31.468Z", 
    public String objectId;
 
 
    //public Date updatedAt; 
    public User user;
    //"user":{"__type":"Pointer","className":"_User","objectId":"S9g5cEY48r"}}]} 
 
 
    public static class User { 
        public String objectId;
 
 
    } 
 
 
 
 
 
 
    private String content;
    private boolean done;
 
 
    public String getContent() {
        return content;
    } 
 
 
    public void setContent(String content) {
        this.content = content;
    } 
 
 
    public boolean isDone() { 
        return done;
    } 
 
 
    public void setDone(boolean done) {
        this.done = done;
    } 
 
 
 
 
    public int describeContents() { 
        return 0; 
    } 
 
 
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(content);
        out.writeByte((byte) (done ? 1 : 0));
    } 
 
 
    public static final Parcelable.Creator<Todo> CREATOR
            = new Parcelable.Creator<Todo>() { 
        public Todo createFromParcel(Parcel in) { 
            return new Todo(in); 
        } 
 
 
        public Todo[] newArray(int size) { 
            return new Todo[size]; 
        } 
    }; 
 
 
    public Todo() { 
 
 
    } 
 
 
    private Todo(Parcel in) {
        content = in.readString();
        done = in.readByte() == 1;
    } 
 
 
    @Override 
    public String toString() {
        return "Todo{" + 
                ", objectId='" + objectId + '\'' +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", done=" + done +
                '}'; 
    } 
} 