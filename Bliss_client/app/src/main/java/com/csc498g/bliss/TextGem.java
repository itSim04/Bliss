package com.csc498g.bliss;

import androidx.annotation.NonNull;

public class TextGem extends Gem {

    private String content;

    public TextGem(int gem_id, String mine_date, String edit_date, int owner, String content, int diamonds, int remines, int root, boolean is_liked) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines, root, is_liked);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @NonNull
    @Override
    public String toString() {
        return "Gem{" +
                "gem_id=" + gem_id +
                ", mine_date='" + mine_date + '\'' +
                ", edit_date='" + edit_date + '\'' +
                ", diamonds=" + diamonds +
                ", remines=" + remines +
                ", owner_id=" + owner_id +
                '}';
    }
}
