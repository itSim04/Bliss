package com.csc498g.bliss;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class Link {

    public static Drawable GetImage(String url) {
        try {
            InputStream stream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(stream, "Image");
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }


}
