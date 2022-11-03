package com.csc498g.bliss;

import androidx.annotation.NonNull;

public class ImageGem extends Gem {

    private String img_src;

    public ImageGem(int gem_id, String mine_date, String edit_date, int owner, String content, int diamonds, int remines, int comments, int root, boolean is_liked) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines, comments, root, is_liked);
        this.img_src = img_src;

    }

    public String getImageSource() {
        return img_src;
    }

    public void setImageSource(String img_src) {
        this.img_src = img_src;
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
