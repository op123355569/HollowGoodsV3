package com.hg.hollowgoods.Adapter.FastAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListAutoNumber;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListDelete;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListEdit;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListFlag;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListImgUrl;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListTitle;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastList.FastListContentData;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastList.FastListData;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastList.FastListTitleData;
import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

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
        viewHolder.setVisible2(R.id.tv_title, data.titleData.isShowTitle);
        viewHolder.setVisible(R.id.iv_edit, data.isShowEdit);
        viewHolder.setVisible(R.id.iv_delete, data.isShowDelete);
        viewHolder.setVisible(R.id.number, data.isAutoNumber);

        viewHolder.setVisible(R.id.tv_label1, data.contentData1.isShowLabel);
        viewHolder.setVisible(R.id.tv_content1, data.contentData1.isShowContent);
        viewHolder.setVisible(R.id.ll_item1, data.contentData1.isShowLabel || data.contentData1.isShowContent);

        viewHolder.setVisible(R.id.tv_label2, data.contentData2.isShowLabel);
        viewHolder.setVisible(R.id.tv_content2, data.contentData2.isShowContent);
        viewHolder.setVisible(R.id.ll_item2, data.contentData2.isShowLabel || data.contentData2.isShowContent);

        viewHolder.setVisible(R.id.tv_label3, data.contentData3.isShowLabel);
        viewHolder.setVisible(R.id.tv_content3, data.contentData3.isShowContent);
        viewHolder.setVisible(R.id.ll_item3, data.contentData3.isShowLabel || data.contentData3.isShowContent);

        viewHolder.setVisible(R.id.tv_label4, data.contentData4.isShowLabel);
        viewHolder.setVisible(R.id.tv_content4, data.contentData4.isShowContent);
        viewHolder.setVisible(R.id.ll_item4, data.contentData4.isShowLabel || data.contentData4.isShowContent);

        viewHolder.setVisible(R.id.iv_img, data.isShowImg);
        viewHolder.setVisible(R.id.iv_flag, data.isShowFlag);

        // 设置标题字体颜色
        if (TextUtils.isEmpty(data.titleData.textColorResName)) {
            viewHolder.setTextColorRes(R.id.tv_title, R.color.txt_color_dark);
        } else {
            Object textColorRes = getObjValue(item, data.titleData.textColorResName);
            if (textColorRes == null) {
                viewHolder.setTextColorRes(R.id.tv_title, R.color.txt_color_dark);
            } else if (RegexUtils.isWholeNumber(textColorRes.toString())) {
                viewHolder.setTextColorRes(R.id.tv_title, new BigDecimal(textColorRes.toString()).intValue());
            } else {
                viewHolder.setTextColorRes(R.id.tv_title, R.color.txt_color_dark);
            }
        }
        // 填充标题字段
        if (data.titleData.isDateTitle) {
            viewHolder.setText(R.id.tv_title, StringUtils.getDateTimeString(data.titleData.title, data.titleData.dateFormatModeTitle));
        } else {
            viewHolder.setText(R.id.tv_title, data.titleData.title);
        }

        // 填充内容1-4
        setContent(viewHolder, item, data.contentData1, R.id.tv_label1, R.id.tv_content1);
        setContent(viewHolder, item, data.contentData2, R.id.tv_label2, R.id.tv_content2);
        setContent(viewHolder, item, data.contentData3, R.id.tv_label3, R.id.tv_content3);
        setContent(viewHolder, item, data.contentData4, R.id.tv_label4, R.id.tv_content4);

        // 背景图片
        if (data.imgUrl == null || TextUtils.isEmpty(data.imgUrl + "")) {
            viewHolder.setImageResource(R.id.iv_img, R.color.transparent);
        } else {
            if (data.isPicture) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(HGCommonResource.IMAGE_LOADING)
                        .error(HGCommonResource.IMAGE_LOAD_ERROR)
                        .centerCrop();
                GlideOptions glideOptions = new GlideOptions(data.imgUrl, null, GlideOptions.NORMAL_FADE_IN, requestOptions);
                viewHolder.setImageByUrl(R.id.iv_img, glideOptions);
            } else {
                viewHolder.setImageResource(R.id.iv_img, Integer.valueOf(data.imgUrl.toString()));
            }
        }

        // Flag
        if (data.flag == null || TextUtils.isEmpty(data.flag + "")) {
            viewHolder.setImageResource(R.id.iv_flag, R.color.transparent);
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(HGCommonResource.IMAGE_LOADING)
                    .error(HGCommonResource.IMAGE_LOAD_ERROR)
                    .centerCrop();
            GlideOptions glideOptions = new GlideOptions(data.flag, null, GlideOptions.NO_FADE_IN, requestOptions);
            viewHolder.setImageByUrl(R.id.iv_flag, glideOptions);
        }

        // 序号背景颜色
        if (data.numberBgColorRes == -1) {
            viewHolder.setSlanted(R.id.number, R.color.color_wrong, "" + (position + 1));
        } else {
            viewHolder.setSlanted(R.id.number, data.numberBgColorRes, "" + (position + 1));
        }

        // 编辑按钮
        if (data.editRes == -1) {
            viewHolder.setImageResource(R.id.iv_edit, R.drawable.ic_fast_edit);
        } else {
            viewHolder.setImageResource(R.id.iv_edit, data.editRes);
        }

        // 删除按钮
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
     * @param onFastClick onFastClick
     */
    public void setOnFastClick(OnFastClick onFastClick) {
        this.onFastClick = onFastClick;
    }


    private void setContent(ViewHolder viewHolder, CommonBean item, FastListContentData contentData, int labelViewId, int contentViewId) {

        if (TextUtils.isEmpty(contentData.textColorResName)) {
            viewHolder.setTextColorRes(contentViewId, contentData.isShowLabel ? R.color.txt_color_light : R.color.txt_color_normal);
        } else {
            Object textColorRes = getObjValue(item, contentData.textColorResName);
            if (textColorRes == null) {
                viewHolder.setTextColorRes(contentViewId, contentData.isShowLabel ? R.color.txt_color_light : R.color.txt_color_normal);
            } else if (RegexUtils.isWholeNumber(textColorRes.toString())) {
                viewHolder.setTextColorRes(contentViewId, new BigDecimal(textColorRes.toString()).intValue());
            } else {
                viewHolder.setTextColorRes(contentViewId, contentData.isShowLabel ? R.color.txt_color_light : R.color.txt_color_normal);
            }
        }

        viewHolder.setText(labelViewId, contentData.label);
        if (contentData.isDate) {
            viewHolder.setText(contentViewId, StringUtils.getDateTimeString(contentData.content, contentData.dateFormatMode));
        } else {
            viewHolder.setText(contentViewId, contentData.content);
        }
    }

    /**
     * 根据注解，封装数据
     *
     * @param item item
     * @return FastListData
     */
    private FastListData getData(CommonBean item) {

        FastListData result = new FastListData();
        Field[] allField = item.getClass().getDeclaredFields();

        if (item.getClass().isAnnotationPresent(FastListAutoNumber.class)) {
            // 自动显示序号
            result.isAutoNumber = true;
            FastListAutoNumber annotation = item.getClass().getAnnotation(FastListAutoNumber.class);
            result.numberBgColorRes = annotation.backgroundColorRes();
        }

        if (allField != null) {
            for (Field t : allField) {
                if (t.isAnnotationPresent(FastListTitle.class)) {
                    // 标题
                    if (result.titleData == null) {
                        result.titleData = new FastListTitleData();
                    }

                    result.titleData.isShowTitle = true;
                    FastListTitle annotation = t.getAnnotation(FastListTitle.class);
                    String itemsName = annotation.itemsName();

                    result.titleData.title = getRealValueByField(item, t.getName(), itemsName);
                    result.titleData.isDateTitle = annotation.isDate();
                    result.titleData.dateFormatModeTitle = annotation.dateFormatMode();
                    result.titleData.textColorResName = annotation.textColorResName();
                }

                if (t.isAnnotationPresent(FastListContent.class)) {
                    // 内容
                    FastListContent annotation = t.getAnnotation(FastListContent.class);
                    FastListContentData content;

                    switch (annotation.number()) {
                        case 1:
                            content = result.contentData1;
                            break;
                        case 2:
                            content = result.contentData2;
                            break;
                        case 3:
                            content = result.contentData3;
                            break;
                        case 4:
                            content = result.contentData4;
                            break;
                        default:
                            continue;
                    }

                    String label = annotation.label();
                    String itemsName = annotation.itemsName();
                    if (!TextUtils.isEmpty(label)) {
                        content.isShowLabel = true;
                        content.label = label;
                    }

                    content.isShowContent = true;
                    content.content = getRealValueByField(item, t.getName(), itemsName);
                    content.isDate = annotation.isDate();
                    content.dateFormatMode = annotation.dateFormatMode();
                    content.textColorResName = annotation.textColorResName();
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

}
