package com.soldiersofmobile.todoekspert;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Sylwester on 2015-12-01.
 */
public class LoginResponse {


    private Date createdAt;//": "2013-11-22T21:48:51.785Z"
    private String objectId;//": "S9g5cEY48r"
    private String sessionToken;//": "r:GQrg1r53j3HULYmTIMI1yrs9T",
    //private //"updatedAt": "2013-11-22T21:48:51.785Z",
    private String username;

    public String getObjectId() {
        return objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "createdAt=" + createdAt +
                ", objectId='" + objectId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
