package com.csc498g.bliss;

public class TextGem extends Gem {

    private String content;

    public TextGem(int gem_id, String mine_date, String edit_date, int owner, String content, int diamonds, int remines) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
