package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Gem {

    // Class holding the Gems

    protected int gem_id; // The Gem ID
    protected LocalDateTime mine_date; // The date the Gem was mined
    protected String edit_date; // The date the Gem was edited (WIP)
    protected int diamonds; // Number of Diamonds on the Gem (Approx)
    protected int remines; // Number of remines on the Gem (WIP)
    protected int comments; // Number of comments on the Gem
    protected int owner_id; // The Owner of the Gem's ID
    protected boolean is_liked; // Whether the Gem was liked by the current user
    protected int is_voted; // Whether the current user voted (If Applicable)
    protected int root; // The parent of the Gem (The ID if the Gem is not a comment)

    public Gem(int gem_id, String mine_date, String edit_date, int owner_id, int diamonds, int remines, int comments, int root, boolean is_liked, int is_voted) {

        // General Constructor
        this.gem_id = gem_id;
        this.mine_date = LocalDateTime.parse(mine_date.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.edit_date = edit_date;
        this.owner_id = owner_id;
        this.diamonds = diamonds;
        this.remines = remines;
        this.comments = comments;
        this.is_liked = is_liked;
        this.is_voted = is_voted;
        this.root = root;

    }

    // Accessors

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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public int getIs_voted() {
        return is_voted;
    }

    public void setIs_voted(int is_voted) {
        this.is_voted = is_voted;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    @NonNull
    public abstract String toString();

    public void incrementDiamond() {

        // Increments the Diamond count
        diamonds++;

    }

    public void decrementDiamond() {

        // Decrement the Diamond count
        diamonds--;

    }
}
