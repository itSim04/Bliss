package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    int user_id;
    String username;
    String password;
    String email;
    LocalDate birthday;
    byte gender;
    String profile;
    String banner;
    int followings;
    int followers;


    public User(int user_id, String username, String password, String email, String birthday, byte gender, String profile, String banner, int followings, int followers) {
        this.user_id = user_id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.birthday = LocalDate.parse(birthday.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE);
        this.gender = gender;
        this.profile = profile;
        this.banner = banner;
        this.followings = followings;
        this.followers = followers;
    }


    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender=" + gender +
                ", profile='" + profile + '\'' +
                ", banner='" + banner + '\'' +
                '}';
    }
}
