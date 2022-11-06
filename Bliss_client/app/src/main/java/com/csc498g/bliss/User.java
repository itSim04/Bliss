package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

    // A class that holds a user

    private int user_id; // The id of the user
    private String username; // The username of the user
    private String password; // The password of the user (WIP)
    private String email; // The email of the user
    private String bio; // The bio of the user
    private LocalDate birthday; // The birthday of the user
    private LocalDate join_date; // The join date of the user
    private byte gender; // The gender of the user
    private String profile; // The profile picture of the user
    private String banner; // The banner of the user
    private int followings; // The followings count (Approx)
    private int followers; // The followers count (Approx)


    public User(int user_id, String username, String password, String email, String bio, String birthday, String join_date, byte gender, String profile, String banner, int followings, int followers) {

        // Constructor
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

    // Accessors
    public int getFollowings() {
        return followings;
    }

    public int getFollowers() {
        return followers;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getJoinDate() {
        return join_date;
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

    // Increments and Decrements followings/followers
    public void incrementFollowings() {
        followings++;
    }
    public void incrementFollowers() {
        followers++;
    }
    public void decrementFollowings() {
        followings--;
    }
    public void decrementFollowers() {
        followers--;
    }
}
