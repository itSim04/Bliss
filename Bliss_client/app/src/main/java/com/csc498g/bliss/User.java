package com.csc498g.bliss;

public class User {

    int user_id;
    String username;
    String email;
    String birthday;
    byte gender;
    String profile;
    String banner;

    public User(int user_id, String username, String email, String birthday, byte gender, String profile, String banner) {

        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.profile = profile;
        this.banner = banner;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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
