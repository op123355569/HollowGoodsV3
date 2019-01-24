package com.hg.hollowgoods.UI.Activity.Example.Ex33.gallery;

import com.hg.hollowgoods.R;

import java.util.Arrays;
import java.util.List;

public class Gallery {

    public static Gallery get() {
        return new Gallery();
    }

    private Gallery() {
    }

    public List<Image> getData() {
        return Arrays.asList(
                new Image(R.drawable.ic_ex_1),
                new Image(R.drawable.ic_ex_2),
                new Image(R.drawable.ic_ex_6),
                new Image(R.drawable.ic_ex_8),
                new Image(R.drawable.ic_ex_9),
                new Image(R.drawable.ic_ex_10));
    }

}
