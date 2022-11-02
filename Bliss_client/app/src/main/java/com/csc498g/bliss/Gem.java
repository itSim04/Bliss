package com.csc498g.bliss;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Gem {

    protected int gem_id;
    protected LocalDateTime mine_date;
    protected String edit_date;
    protected int diamonds;
    protected int remines;
    protected int owner_id;

    public Gem(int gem_id, String mine_date, String edit_date, int owner_id, int diamonds, int remines) {
        this.gem_id = gem_id;
        this.mine_date = LocalDateTime.parse(mine_date.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.edit_date = edit_date;
        this.owner_id = owner_id;
        this.diamonds = diamonds;
        this.remines = remines;
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

    public abstract String toString();

    public void incrementDiamond() {

        diamonds++;

    }

    public void decrementDiamond() {

        diamonds--;

    }
}
