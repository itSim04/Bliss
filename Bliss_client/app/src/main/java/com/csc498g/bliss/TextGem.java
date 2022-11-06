package com.csc498g.bliss;

public class TextGem extends Gem {

    // Specific class for Text Gems

    private String content; // The text content

    public TextGem(int gem_id, String mine_date, String edit_date, int owner, String content, int diamonds, int remines, int comments, int root, boolean is_liked, int is_voted) {

        // Constructor
        super(gem_id, mine_date, edit_date, owner, diamonds, remines, comments, root, is_liked, is_voted);
        this.content = content;

    }

    // Accessors
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextGem{" +
                "gem_id=" + gem_id +
                ", mine_date=" + mine_date +
                ", edit_date='" + edit_date + '\'' +
                ", diamonds=" + diamonds +
                ", remines=" + remines +
                ", comments=" + comments +
                ", owner_id=" + owner_id +
                ", is_liked=" + is_liked +
                ", root=" + root +
                ", content='" + content + '\'' +
                '}';
    }
}
