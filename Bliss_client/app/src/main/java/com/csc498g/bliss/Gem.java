package com.csc498g.bliss;

import androidx.annotation.NonNull;

public abstract class Gem {

    private int gem_id;
    private String mine_date;
    private String edit_date;
    private int diamonds;
    private int remines;
    private int owner_id;

    public Gem(int gem_id, String mine_date, String edit_date, int owner_id, int diamonds, int remines) {
        this.gem_id = gem_id;
        this.mine_date = mine_date;
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

    public String getMine_date() {
        return mine_date;
    }

    public void setMine_date(String mine_date) {
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

    @NonNull
    @Override
    public String toString() {
        return "Gem{" +
                "gem_id=" + gem_id +
                ", mine_date='" + mine_date + '\'' +
                ", edit_date='" + edit_date + '\'' +
                '}';
    }
}
