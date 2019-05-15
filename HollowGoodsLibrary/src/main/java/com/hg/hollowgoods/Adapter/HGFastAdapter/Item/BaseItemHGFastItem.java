package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;

/**
 * Created by Hollow Goods on 2019-05-07.
 */
abstract class BaseItemHGFastItem<CommonBean> implements ItemViewDelegate<CommonBean> {

    /**
     * 根据当前运行环境debug或者release来显示或者隐藏排序号和id
     */
    void setRunModeView(ViewHolder viewHolder, int viewId, int sortNumber, int id) {
        if (HGSystemConfig.IS_DEBUG_MODEL) {
            // debug模式
            viewHolder.setVisible(viewId, true);
            viewHolder.setText(viewId, sortNumber + "\n" + id);
        } else {
            // release模式
            viewHolder.setVisible(viewId, false);
        }
    }

    /**
     * 设置Label
     */
    void setLabel(ViewHolder viewHolder, int notEmptyFlagViewId, int labelViewId, boolean isNotEmpty, String label, int labelTextColorRes, int contentSize) {

        if (isNotEmpty) {
            // 内容不能为空
            viewHolder.setVisible(notEmptyFlagViewId, true);
            if (contentSize < 1) {
                viewHolder.setTextColorRes(labelViewId, R.color.google_red);
            } else {
                viewHolder.setTextColorRes(labelViewId, labelTextColorRes);
            }
        } else {
            // 内容可以为空
            viewHolder.setVisible(notEmptyFlagViewId, false);
            viewHolder.setTextColorRes(labelViewId, labelTextColorRes);
        }

        if (TextUtils.isEmpty(label)) {
            // 标签为空
            viewHolder.setVisible2(labelViewId, false);
        } else {
            // 标签不为空
            viewHolder.setVisible2(labelViewId, true);
            viewHolder.setText(labelViewId, label);
        }
    }

    /**
     * 设置左侧图标
     */
    void setLeftIcon(ViewHolder viewHolder, int viewId, int iconRes) {
        if (iconRes == -1) {
            // 不显示左侧图标
            viewHolder.setVisible(viewId, false);
        } else {
            // 显示左侧图标
            viewHolder.setVisible(viewId, true);
            viewHolder.setImageResource(viewId, iconRes);
        }
    }

    /**
     * 设置右侧图标
     */
    void setRightIcon(ViewHolder viewHolder, int rightIconViewId, int contentMarginViewId, int iconRes, ItemMode itemMode, boolean isOnlyRead) {

        if (isOnlyRead) {
            // 只读
            viewHolder.setVisible(rightIconViewId, false);
            viewHolder.setVisible(contentMarginViewId, true);
        } else {
            // 可编辑
            if (iconRes == -1) {
                switch (itemMode) {
                    case Input:
                        viewHolder.setVisible(rightIconViewId, true);
                        viewHolder.setVisible(contentMarginViewId, false);
                        viewHolder.setImageResource(rightIconViewId, R.drawable.ic_fast_edit);
                        break;
                    case SingleChoice:
                        viewHolder.setVisible(rightIconViewId, true);
                        viewHolder.setVisible(contentMarginViewId, false);
                        viewHolder.setImageResource(rightIconViewId, R.drawable.ic_fast_choose);
                        break;
                    case File:
                        viewHolder.setVisible(rightIconViewId, true);
                        viewHolder.setVisible(contentMarginViewId, false);
                        viewHolder.setImageResource(rightIconViewId, R.drawable.ic_fast_file);
                        break;
                    case Customize:
                    default:
                        viewHolder.setVisible(rightIconViewId, false);
                        viewHolder.setVisible(contentMarginViewId, true);
                        break;
                }
            } else {
                viewHolder.setVisible(rightIconViewId, true);
                viewHolder.setVisible(contentMarginViewId, false);
                viewHolder.setImageResource(rightIconViewId, iconRes);
            }
        }
    }

    void setMargin(ViewHolder viewHolder, int viewId, int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
        View marginView = viewHolder.getView(viewId);
        RecyclerView.LayoutParams flp = (RecyclerView.LayoutParams) marginView.getLayoutParams();
        flp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
    }

}
