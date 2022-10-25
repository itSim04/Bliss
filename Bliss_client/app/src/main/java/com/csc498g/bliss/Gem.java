package com.csc498g.bliss;

public class Gem {

    private int gem_id;
    private String mine_date;
    private String edit_date;

    public Gem(int gem_id, String mine_date, String edit_date) {
        this.gem_id = gem_id;
        this.mine_date = mine_date;
        this.edit_date = edit_date;
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

    @Override
    public String toString() {
        return "Gem{" +
                "gem_id=" + gem_id +
                ", mine_date='" + mine_date + '\'' +
                ", edit_date='" + edit_date + '\'' +
                '}';
    }
}
