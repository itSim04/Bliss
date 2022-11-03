package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Gem {

    protected int gem_id;
    protected LocalDateTime mine_date;
    protected String edit_date;
    protected int diamonds;
    protected int remines;
    protected int comments;
    protected int owner_id;
    protected boolean is_liked;
    protected int root;

    public Gem(int gem_id, String mine_date, String edit_date, int owner_id, int diamonds, int remines, int comments, int root, boolean is_liked) {

        this.gem_id = gem_id;
        this.mine_date = LocalDateTime.parse(mine_date.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.edit_date = edit_date;
        this.owner_id = owner_id;
        this.diamonds = diamonds;
        this.remines = remines;
        this.comments = comments;
        this.is_liked = is_liked;
        this.root = root;

    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public boolean is_comment() {
        return this.root != this.gem_id;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public int getGem_id() {
        return gem_id;
    }

    public void setGem_id(int gem_id) {
        this.gem_id = gem_id;
    }

    public LocalDateTime getMine_date() {
        return mine_date;
    }

    public void setMine_date(LocalDateTime mine_date) {
        this.mine_date = mine_date;
    }

    public String getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(String edit_date) {
        this.edit_date = edit_date;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getRemines() {
        return remines;
    }

    public void setRemines(int remines) {
        this.remines = remines;
    }

    public boolean isLiked() {
        return is_liked;
    }

    public void setLiked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    @NonNull
    public abstract String toString();

    public void incrementDiamond() {

        diamonds++;

    }

    public void decrementDiamond() {

        diamonds--;

    }
}
