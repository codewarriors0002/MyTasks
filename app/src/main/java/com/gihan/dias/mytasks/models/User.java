package com.gihan.dias.mytasks.models;

import com.orm.SugarRecord;

public class User extends SugarRecord<User> {
    private String name;
    private String emailAddress;
    private String profileImgUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public  User(){

    }

    public  User(String name, String emailAddress, String profileImgUrl){
        this.name = name;
        this.emailAddress = emailAddress;
        this.profileImgUrl = profileImgUrl;

    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }
}
