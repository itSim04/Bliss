package com.csc498g.bliss;

public class TextGem extends Gem {

    private String content;
    private int diamonds;
    private int remines;

    public TextGem(int gem_id, String mine_date, String edit_date, String owner, String content, int diamonds, int remines) {
        super(gem_id, mine_date, edit_date, owner);
        this.content = content;
        this.diamonds = diamonds;
        this.remines = remines;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
