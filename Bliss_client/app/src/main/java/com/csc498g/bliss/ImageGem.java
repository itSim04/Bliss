package com.csc498g.bliss;

public class ImageGem extends Gem {

    private String img_src;


    public ImageGem(int gem_id, String mine_date, String edit_date, String owner, String img_src, int diamonds, int remines) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines);
        this.img_src = img_src;

    }

    public String getImageSource() {
        return img_src;
    }

    public void setImageSource(String img_src) {
        this.img_src = img_src;
    }


}
