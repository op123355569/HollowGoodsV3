package com.hg.hollowgoods.Util.PhotoPicter.Adapters;

import android.databinding.BindingAdapter;

import com.hg.hollowgoods.Util.PhotoPicter.Widget.BGANinePhotoLayout;

import java.util.ArrayList;

/**
 * 创建时间:16/12/5 上午12:44
 * 描述:
 */
public class BGANinePhotoLayoutAdapter {

    @BindingAdapter({"bga_npl_delegate"})
    public static void setDelegate(BGANinePhotoLayout ninePhotoLayout, BGANinePhotoLayout.Delegate delegate) {
        ninePhotoLayout.setDelegate(delegate);
    }

    @BindingAdapter({"bga_npl_data"})
    public static void setData(BGANinePhotoLayout ninePhotoLayout, ArrayList<String> data) {
        ninePhotoLayout.setData(data);
    }
}
