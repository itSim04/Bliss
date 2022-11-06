package com.csc498g.bliss;

import androidx.annotation.NonNull;

public class ImageGem extends Gem {

    // Specific class for Image gems (WIP)

    private String img_src; // The source of the Image (WIP)


    public ImageGem(int gem_id, String mine_date, String edit_date, int owner, String content, int diamonds, int remines, int comments, int root, boolean is_liked, int is_voted) {

        // Constructor

        super(gem_id, mine_date, edit_date, owner, diamonds, remines, comments, root, is_liked, is_voted);
        this.img_src = img_src;

    }

    // Accessors
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
