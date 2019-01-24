package com.hg.hollowgoods.Adapter.FastAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListAutoNumber;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent1;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent2;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent3;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent4;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListDelete;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListEdit;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListFlag;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListImgUrl;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListTitle;
import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.Glide.GlideOptions;

import java.lang.reflect.Field;

/**
 * 快速适配器布局——列表布局
 * Created by HG on 2018-06-13.
 */
public class ItemFastList extends BaseFastItem implements ItemViewDelegate<CommonBean> {

    private Context context;
    private OnFastClick onFastClick = null;

    public ItemFastList(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_fast_list;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == FastAdapter.ITEM_TYPE_LIST;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, final int position) {

        FastListData data = getData(item);

        // 设置可见性
        viewHolder.setVisible2(R.id.tv_title, data.isShowTitle);
        viewHolder.setVisible(R.id.iv_edit, data.isShowEdit);
        viewHolder.setVisible(R.id.iv_delete, data.isShowDelete);
        viewHolder.setVisible(R.id.number, data.isAutoNumber);

        viewHolder.setVisible(R.id.tv_label1, data.isShowLabel1);
        viewHolder.setVisible(R.id.tv_content1, data.isShowContent1);
        viewHolder.setVisible(R.id.ll_item1, data.isShowLabel1 || data.isShowContent1);

        viewHolder.setVisible(R.id.tv_label2, data.isShowLabel2);
        viewHolder.setVisible(R.id.tv_content2, data.isShowContent2);
        viewHolder.setVisible(R.id.ll_item2, data.isShowLabel2 || data.isShowContent2);

        viewHolder.setVisible(R.id.tv_label3, data.isShowLabel3);
        viewHolder.setVisible(R.id.tv_content3, data.isShowContent3);
        viewHolder.setVisible(R.id.ll_item3, data.isShowLabel3 || data.isShowContent3);

        viewHolder.setVisible(R.id.tv_label4, data.isShowLabel4);
        viewHolder.setVisible(R.id.tv_content4, data.isShowContent4);
        viewHolder.setVisible(R.id.ll_item4, data.isShowLabel4 || data.isShowContent4);

        viewHolder.setVisible(R.id.iv_img, data.isShowImg);
        viewHolder.setVisible(R.id.iv_flag, data.isShowFlag);

        // 设置字体颜色
        viewHolder.setTextColorRes(R.id.tv_content1, data.isShowLabel1 ? R.color.txt_color_light : R.color.txt_color_normal);
        viewHolder.setTextColorRes(R.id.tv_content2, data.isShowLabel2 ? R.color.txt_color_light : R.color.txt_color_normal);
        viewHolder.setTextColorRes(R.id.tv_content3, data.isShowLabel3 ? R.color.txt_color_light : R.color.txt_color_normal);
        viewHolder.setTextColorRes(R.id.tv_content4, data.isShowLabel4 ? R.color.txt_color_light : R.color.txt_color_normal);

        // 填充字段
        viewHolder.setText(R.id.tv_title, data.title);
        viewHolder.setText(R.id.tv_label1, data.label1);
        viewHolder.setText(R.id.tv_content1, data.content1);
        viewHolder.setText(R.id.tv_label2, data.label2);
        viewHolder.setText(R.id.tv_content2, data.content2);
        viewHolder.setText(R.id.tv_label3, data.label3);
        viewHolder.setText(R.id.tv_content3, data.content3);
        viewHolder.setText(R.id.tv_label4, data.label4);
        viewHolder.setText(R.id.tv_content4, data.content4);

        if (data.imgUrl == null || TextUtils.isEmpty(data.imgUrl + "")) {
            viewHolder.setImageResource(R.id.iv_img, R.color.transparent);
        } else {
            if (data.isPicture) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(CommonResource.IMAGE_LOADING)
                        .error(CommonResource.IMAGE_LOAD_ERROR)
                        .centerCrop();
                GlideOptions glideOptions = new GlideOptions(data.imgUrl, null, GlideOptions.NORMAL_FADE_IN, requestOptions);
                viewHolder.setImageByUrl(R.id.iv_img, glideOptions);
            } else {
                viewHolder.setImageResource(R.id.iv_flag, Integer.valueOf(data.imgUrl.toString()));
            }
        }

        if (data.flag == null || TextUtils.isEmpty(data.flag + "")) {
            viewHolder.setImageResource(R.id.iv_flag, R.color.transparent);
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(CommonResource.IMAGE_LOADING)
                    .error(CommonResource.IMAGE_LOAD_ERROR)
                    .centerCrop();
            GlideOptions glideOptions = new GlideOptions(data.flag, null, GlideOptions.NO_FADE_IN, requestOptions);
            viewHolder.setImageByUrl(R.id.iv_flag, glideOptions);
        }

        if (data.numberBgColorRes == -1) {
            viewHolder.setSlanted(R.id.number, R.color.color_wrong, "" + (position + 1));
        } else {
            viewHolder.setSlanted(R.id.number, data.numberBgColorRes, "" + (position + 1));
        }

        if (data.editRes == -1) {
            viewHolder.setImageResource(R.id.iv_edit, R.drawable.ic_fast_edit);
        } else {
            viewHolder.setImageResource(R.id.iv_edit, data.editRes);
        }

        if (data.deleteRes == -1) {
            viewHolder.setImageResource(R.id.iv_delete, R.drawable.ic_fast_delete);
        } else {
            viewHolder.setImageResource(R.id.iv_delete, data.deleteRes);
        }

        // 设置点击事件
        viewHolder.setOnClickListener(R.id.iv_edit, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (onFastClick != null) {
                    onFastClick.onEditClick(view, position);
                }
            }
        });

        viewHolder.setOnClickListener(R.id.iv_delete, new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (onFastClick != null) {
                    onFastClick.onDeleteClick(view, position);
                }
            }
        });
    }

    /**
     * 设置点击监听
     *
     * @param onFastClick
     */
    public void setOnFastClick(OnFastClick onFastClick) {
        this.onFastClick = onFastClick;
    }

    /**
     * 根据注解，封装数据
     *
     * @param item
     * @return
     */
    private FastListData getData(CommonBean item) {

        FastListData result = new FastListData();
        Field[] allField = item.getClass().getDeclaredFields();

        if (item.getClass().isAnnotationPresent(FastListAutoNumber.class)) {
            // 自动显示序号
            result.isAutoNumber = true;
            FastListAutoNumber annotation = item.getClass().getAnnotation(FastListAutoNumber.class);
            int backgroundColorRes = annotation.backgroundColorRes();
            result.numberBgColorRes = backgroundColorRes;
        }

        if (allField != null) {
            for (Field t : allField) {
                if (t.isAnnotationPresent(FastListTitle.class)) {
                    // 标题
                    result.isShowTitle = true;
                    FastListTitle annotation = t.getAnnotation(FastListTitle.class);
                    String itemsName = annotation.itemsName();

                    result.title = getRealValueByField(item, t.getName(), itemsName);
                }

                if (t.isAnnotationPresent(FastListContent1.class)) {
                    // 标签1
                    FastListContent1 annotation = t.getAnnotation(FastListContent1.class);
                    String label = annotation.label();
                    String itemsName = annotation.itemsName();
                    if (!TextUtils.isEmpty(label)) {
                        result.isShowLabel1 = true;
                        result.label1 = label;
                    }

                    // 内容1
                    result.isShowContent1 = true;
                    result.content1 = getRealValueByField(item, t.getName(), itemsName);
                }

                if (t.isAnnotationPresent(FastListContent2.class)) {
                    // 标签2
                    FastListContent2 annotation = t.getAnnotation(FastListContent2.class);
                    String label = annotation.label();
                    String itemsName = annotation.itemsName();
                    if (!TextUtils.isEmpty(label)) {
                        result.isShowLabel2 = true;
                        result.label2 = label;
                    }

                    // 内容2
                    result.isShowContent2 = true;
                    result.content2 = getRealValueByField(item, t.getName(), itemsName);
                }

                if (t.isAnnotationPresent(FastListContent3.class)) {
                    // 标签3
                    FastListContent3 annotation = t.getAnnotation(FastListContent3.class);
                    String label = annotation.label();
                    String itemsName = annotation.itemsName();
                    if (!TextUtils.isEmpty(label)) {
                        result.isShowLabel3 = true;
                        result.label3 = label;
                    }

                    // 内容3
                    result.isShowContent3 = true;
                    result.content3 = getRealValueByField(item, t.getName(), itemsName);
                }

                if (t.isAnnotationPresent(FastListContent4.class)) {
                    // 标签4
                    FastListContent4 annotation = t.getAnnotation(FastListContent4.class);
                    String label = annotation.label();
                    String itemsName = annotation.itemsName();
                    if (!TextUtils.isEmpty(label)) {
                        result.isShowLabel4 = true;
                        result.label4 = label;
                    }

                    // 内容4
                    result.isShowContent4 = true;
                    result.content4 = getRealValueByField(item, t.getName(), itemsName);
                }

                if (t.isAnnotationPresent(FastListEdit.class)) {
                    // 编辑按钮
                    String value = getStringValue(item, t.getName());
                    result.isShowEdit = !TextUtils.isEmpty(value) && TextUtils.equals(value, "true");
                    FastListEdit annotation = t.getAnnotation(FastListEdit.class);
                    result.editRes = annotation.iconRes();
                }

                if (t.isAnnotationPresent(FastListDelete.class)) {
                    // 删除按钮
                    String value = getStringValue(item, t.getName());
                    result.isShowDelete = !TextUtils.isEmpty(value) && TextUtils.equals(value, "true");
                    FastListDelete annotation = t.getAnnotation(FastListDelete.class);
                    result.deleteRes = annotation.iconRes();
                }

                if (t.isAnnotationPresent(FastListImgUrl.class)) {
                    // 图片链接
                    Object value = getObjValue(item, t.getName());
                    result.isShowImg = value != null;
                    result.imgUrl = value;
                    FastListImgUrl annotation = t.getAnnotation(FastListImgUrl.class);
                    result.isPicture = annotation.isPicture();
                }

                if (t.isAnnotationPresent(FastListFlag.class)) {
                    // 标识
                    Object value = getObjValue(item, t.getName());
                    result.isShowFlag = value != null;
                    result.flag = value;
                }
            }
        }

        return result;
    }

    /**
     * 封装数据
     */
    private class FastListData {

        private int numberBgColorRes;

        private String title;
        private boolean isShowTitle;

        private String label1;
        private boolean isShowLabel1;
        private String content1;
        private boolean isShowContent1;

        private String label2;
        private boolean isShowLabel2;
        private String content2;
        private boolean isShowContent2;

        private String label3;
        private boolean isShowLabel3;
        private String content3;
        private boolean isShowContent3;

        private String label4;
        private boolean isShowLabel4;
        private String content4;
        private boolean isShowContent4;

        private Object imgUrl;
        private boolean isShowImg;
        private boolean isPicture;

        private boolean isAutoNumber;

        private boolean isShowEdit;
        private int editRes;

        private boolean isShowDelete;
        private int deleteRes;

        private boolean isShowFlag;
        private Object flag;

        public FastListData() {
            this.numberBgColorRes = -1;
            this.isShowTitle = false;
            this.isShowLabel1 = false;
            this.isShowContent1 = false;
            this.isShowLabel2 = false;
            this.isShowContent2 = false;
            this.isShowLabel3 = false;
            this.isShowContent3 = false;
            this.isShowLabel4 = false;
            this.isShowContent4 = false;
            this.isShowImg = false;
            this.isPicture = true;
            this.isShowEdit = false;
            this.isShowDelete = false;
            this.isAutoNumber = false;
            this.editRes = -1;
            this.deleteRes = -1;
            this.isShowFlag = false;
            this.flag = null;
        }
    }


}
