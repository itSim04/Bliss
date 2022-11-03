package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

    private int user_id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private LocalDate birthday;
    private LocalDate join_date;
    private byte gender;
    private String profile;
    private String banner;
    private int followings;
    private int followers;


    public User(int user_id, String username, String password, String email, String bio, String birthday, String join_date, byte gender, String profile, String banner, int followings, int followers) {
        this.user_id = user_id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ISO_LOCAL_DATE);
        this.join_date = LocalDate.parse(join_date, DateTimeFormatter.ISO_LOCAL_DATE);
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getJoin_date() {
        return join_date;
    }

    public void setJoin_date(LocalDate join_date) {
        this.join_date = join_date;
    }
}
