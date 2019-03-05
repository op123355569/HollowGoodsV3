package com.hg.hollowgoods.Adapter.FastAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ItemViewDelegate;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItem;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemFileMaxCount;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemNumberPicker;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemSwitch;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastItemData;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.FormatUtils;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 快速适配器布局——详细项布局
 * Created by HG on 2018-06-14.
 */
public class ItemFastItem extends BaseFastItem implements ItemViewDelegate<CommonBean> {

    private Context context;
    private OnFastClick onFastClick = null;

    private HashMap<FastItemMode, Integer> defaultRightIcon = new HashMap<FastItemMode, Integer>() {
        {
            put(FastItemMode.File, R.drawable.ic_fast_file);
            put(FastItemMode.Choose, R.drawable.ic_fast_choose);
            put(FastItemMode.Input, R.drawable.ic_fast_edit);
            put(FastItemMode.Ohter, R.drawable.ic_fast_edit);
        }
    };

    public ItemFastItem(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_fast_item;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == FastAdapter.ITEM_TYPE_ITEM;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, final int position) {

        if (item instanceof FastItemData) {
            final FastItemData data = item.getData();

            // 上间距
            viewHolder.setVisible(R.id.marginTop, data.marginTop > 0);
            viewHolder.setViewHeight(R.id.marginTop, data.marginTop);

            // 排序号
            viewHolder.setVisible(R.id.tv_sortNumber, HGSystemConfig.IS_DEBUG_MODEL);
            viewHolder.setText(R.id.tv_sortNumber, data.sortNumber + "");

            // 左侧图标
            viewHolder.setVisible(R.id.iv_leftIcon, data.isShowLeftIconRes);
            if (data.isShowLeftIconRes) {
                viewHolder.setImageResource(R.id.iv_leftIcon, data.leftIconRes);
            } else {
                viewHolder.setImageResource(R.id.iv_leftIcon, R.color.transparent);
            }

            // 必填标识
            viewHolder.setVisible2(R.id.tv_notEmptyFlag, data.isNotEmpty);

            // 标签
            viewHolder.setVisible(R.id.tv_label, data.isShowLabel);
            viewHolder.setText(R.id.tv_label, data.label);

            // 内容
            viewHolder.setVisible(R.id.tv_content, data.isNeedContent);
            viewHolder.setVisible(R.id.iv_content, data.isNeedContent);
            viewHolder.setTextHint(R.id.tv_content, data.contentHint);
            Object content;
            if (!data.isNeedContent || data.fastItemMode == FastItemMode.File || (data.isShowNumberPicker && !data.isOnlyRead())) {
                content = "";
            } else {
                content = getRealValue(data.content, data.itemsName);
            }

            if (data.isNeedContent) {
                viewHolder.setVisible(R.id.tv_content, !content.toString().startsWith(CONTENT_ICON_HEAD));
                viewHolder.setVisible(R.id.iv_content, content.toString().startsWith(CONTENT_ICON_HEAD));

                if (content.toString().startsWith(CONTENT_ICON_HEAD)) {
                    viewHolder.setImageResource(R.id.iv_content, Integer.parseInt(content.toString().substring(6)));
                    viewHolder.setText(R.id.tv_content, "");
                } else {
                    if (data.isDate) {
                        viewHolder.setText(R.id.tv_content, StringUtils.getDateTimeString(content.toString(), data.dateFormatMode));
                    } else {
                        viewHolder.setText(R.id.tv_content, content.toString());
                    }
                    viewHolder.setImageResource(R.id.iv_content, R.color.transparent);
                }
            } else {
                if (data.isDate) {
                    viewHolder.setText(R.id.tv_content, StringUtils.getDateTimeString(content.toString(), data.dateFormatMode));
                } else {
                    viewHolder.setText(R.id.tv_content, content.toString());
                }
                viewHolder.setImageResource(R.id.iv_content, R.color.transparent);
            }

            // 设置内容字体颜色
            if (TextUtils.isEmpty(data.textColorResName)) {
                viewHolder.setTextColorRes(R.id.tv_content, R.color.txt_color_normal);
            } else {
                if (data.textColorRes == null) {
                    viewHolder.setTextColorRes(R.id.tv_content, R.color.txt_color_normal);
                } else if (RegexUtils.isWholeNumber(data.textColorRes.toString())) {
                    viewHolder.setTextColorRes(R.id.tv_content, new BigDecimal(data.textColorRes.toString()).intValue());
                } else {
                    viewHolder.setTextColorRes(R.id.tv_content, R.color.txt_color_normal);
                }
            }

            // 设置必填项标签的字体颜色
            if (data.isNotEmpty) {
                if (data.fastItemMode != FastItemMode.File && TextUtils.isEmpty(content.toString())) {
                    viewHolder.setTextColorRes(R.id.tv_label, R.color.color_wrong);
                } else if (data.fastItemMode == FastItemMode.File && (data.getMedia().get(data.sortNumber) == null || data.getMedia().get(data.sortNumber).size() == 0)) {
                    viewHolder.setTextColorRes(R.id.tv_label, R.color.color_wrong);
                } else {
                    viewHolder.setTextColorRes(R.id.tv_label, R.color.txt_color_dark);
                }
            } else {
                viewHolder.setTextColorRes(R.id.tv_label, R.color.txt_color_dark);
            }

            // 右侧图标
            if (data.isShowSwitchButton) {
                viewHolder.setVisible(R.id.iv_rightIcon, false);
                viewHolder.setVisible(R.id.switch_rightIcon, true);
                viewHolder.setEnabled(R.id.switch_rightIcon, !data.isOnlyRead());

                if (TextUtils.equals(data.content.toString().toLowerCase(), "true")) {
                    viewHolder.setChecked(R.id.switch_rightIcon, true);
                } else {
                    viewHolder.setChecked(R.id.switch_rightIcon, false);
                }
            } else {
                viewHolder.setVisible(R.id.iv_rightIcon, !data.isOnlyRead());
                viewHolder.setVisible(R.id.switch_rightIcon, false);

                if (data.isOnlyRead()) {
                    viewHolder.setImageResource(R.id.iv_rightIcon, R.color.transparent);
                } else {
                    if (data.rightIconRes == -1) {
                        viewHolder.setImageResource(R.id.iv_rightIcon, defaultRightIcon.get(data.fastItemMode));
                    } else {
                        viewHolder.setImageResource(R.id.iv_rightIcon, data.rightIconRes);
                    }
                }
            }

            // 文件数量标签
            viewHolder.setVisible(R.id.fl_count, data.fastItemMode == FastItemMode.File);
            ArrayList<Media> media = data.getMedia().get(data.sortNumber);
            if (data.fileMaxCount < 1) {
                viewHolder.setText(R.id.tv_count, media == null ? "0" : media.size() + "");
            } else {
                viewHolder.setText(R.id.tv_count, (media == null ? "0" : media.size() + "") + "/" + data.fileMaxCount);
            }

            // 文件预览
            if (media == null || media.size() == 0) {
                viewHolder.setImageResource(R.id.iv_imgPre, R.color.transparent);
            } else {
                Media m = media.get(media.size() - 1);
                if (m.getFile() == null && TextUtils.isEmpty(m.getUrl())) {
                    viewHolder.setImageResource(R.id.iv_imgPre, R.color.transparent);
                } else {
                    RequestOptions requestOptions = new RequestOptions()
                            .circleCrop();
                    String url;
                    if (m.getFile() == null) {
                        url = m.getUrl();
                    } else {
                        url = m.getFile().getAbsolutePath();
                    }
                    GlideOptions glideOptions = new GlideOptions(url, null, GlideOptions.NO_FADE_IN, requestOptions);
                    viewHolder.setImageByUrl(R.id.iv_imgPre, glideOptions);
                }
            }

            // 数字选择器
            viewHolder.setVisible(R.id.iv_minus, data.isShowNumberPicker && !data.isOnlyRead());
            viewHolder.setVisible(R.id.iv_plus, data.isShowNumberPicker && !data.isOnlyRead());
            viewHolder.setVisible(R.id.tv_number, data.isShowNumberPicker && !data.isOnlyRead());

            if (data.isShowNumberPicker) {
                Double number = getNumber(data);

                if (data.isOnlyRead()) {
                    viewHolder.setImageResource(R.id.iv_minus, R.drawable.ic_remove_grey_24dp);
                } else {
                    if (data.numberPickerMin != null) {
                        if (number.doubleValue() == data.numberPickerMin.doubleValue()) {
                            viewHolder.setImageResource(R.id.iv_minus, R.drawable.ic_remove_grey_24dp);
                        } else {
                            viewHolder.setImageResource(R.id.iv_minus, R.drawable.ic_remove_black_24dp);
                        }
                    } else {
                        viewHolder.setImageResource(R.id.iv_minus, R.drawable.ic_remove_black_24dp);
                    }
                }

                if (data.isOnlyRead()) {
                    viewHolder.setImageResource(R.id.iv_plus, R.drawable.ic_add_grey_24dp);
                } else {
                    if (data.numberPickerMax != null) {
                        if (number.doubleValue() == data.numberPickerMax.doubleValue()) {
                            viewHolder.setImageResource(R.id.iv_plus, R.drawable.ic_add_grey_24dp);
                        } else {
                            viewHolder.setImageResource(R.id.iv_plus, R.drawable.ic_add_black_24dp);
                        }
                    } else {
                        viewHolder.setImageResource(R.id.iv_plus, R.drawable.ic_add_black_24dp);
                    }
                }

                Object result = getResultNumber(data, number);
                viewHolder.setText(R.id.tv_number, result.toString());
            } else {
                viewHolder.setText(R.id.tv_number, "");
            }

            // 点击事件
            viewHolder.setOnClickListener(R.id.fl_rightIcon, new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {

                    ArrayList<Media> media = data.getMedia().get(data.sortNumber);

                    if (media == null || data.fileMaxCount < 1 || media.size() < data.fileMaxCount) {
                        if (onFastClick != null) {
                            onFastClick.onOperateClick(view, position, data.sortNumber);
                        }
                    } else {
                        String tip = context.getString(R.string.is_had_max_count);
                        tip = String.format(tip, data.fileMaxCount + "");
                        t.error(tip);
                    }
                }
            });

            viewHolder.setOnClickListener(R.id.fl_count, new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (onFastClick != null) {
                        onFastClick.onFilePreClick(view, position, data.sortNumber);
                    }
                }
            });

            viewHolder.setOnClickListener(R.id.iv_minus, new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (onFastClick != null) {
                        Double number = getNumber(data);
                        Double tempResult = number - data.numberPickerDif;
                        tempResult = FormatUtils.doNumberFormat(tempResult, data.numberPickerPointCount);

                        if (data.numberPickerMin == null || tempResult.doubleValue() >= data.numberPickerMin.doubleValue()) {
                            Object result = getResultNumber(data, tempResult);
                            onFastClick.onNumberPickerClick(view, position, data.sortNumber, result);
                        } else {
                            String tips = context.getString(R.string.is_min);
                            tips = String.format(tips, getResultNumber(data, data.numberPickerMin));
                            t.error(tips);
                        }
                    }
                }
            });

            viewHolder.setOnClickListener(R.id.iv_plus, new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (onFastClick != null) {
                        Double number = getNumber(data);
                        Double tempResult = number + data.numberPickerDif;
                        tempResult = FormatUtils.doNumberFormat(tempResult, data.numberPickerPointCount);

                        if (data.numberPickerMax == null || tempResult.doubleValue() <= data.numberPickerMax.doubleValue()) {
                            Object result = getResultNumber(data, tempResult);
                            onFastClick.onNumberPickerClick(view, position, data.sortNumber, result);
                        } else {
                            String tips = context.getString(R.string.is_max);
                            tips = String.format(tips, getResultNumber(data, data.numberPickerMax));
                            t.error(tips);
                        }
                    }
                }
            });

            // 为margin的占位控件设置点击事件，防止margin区域可被点击
            viewHolder.setOnClickListener(R.id.marginTop, new OnViewClickListener(false));
        } else {
            // 数据类型不正确设置可见性为不可见
            viewHolder.setVisible(R.id.marginTop, false);
            viewHolder.setVisible(R.id.iv_leftIcon, false);
            viewHolder.setVisible(R.id.tv_notEmptyFlag, false);
            viewHolder.setVisible(R.id.tv_label, false);
            viewHolder.setVisible(R.id.tv_content, false);
            viewHolder.setVisible(R.id.iv_content, false);
            viewHolder.setVisible(R.id.iv_rightIcon, false);
            viewHolder.setVisible(R.id.fl_count, false);
            viewHolder.setVisible(R.id.tv_sortNumber, false);
            viewHolder.setVisible(R.id.iv_minus, false);
            viewHolder.setVisible(R.id.iv_plus, false);
            viewHolder.setVisible(R.id.tv_number, false);
            viewHolder.setVisible(R.id.fl_rightIcon, false);
            viewHolder.setVisible(R.id.switch_rightIcon, false);
        }
    }

    private Object getResultNumber(FastItemData data, Double tempNumber) {

        Object result;

        if (data.numberPickerType == Integer.class) {
            result = (int) tempNumber.doubleValue();
        } else if (data.numberPickerType == Long.class) {
            result = (long) tempNumber.doubleValue();
        } else if (data.numberPickerType == Float.class) {
            result = (float) tempNumber.doubleValue();
        } else if (data.numberPickerType == Double.class) {
            result = tempNumber.doubleValue();
        } else {
            result = (int) tempNumber.doubleValue();
        }

        return result;
    }

    private Double getNumber(FastItemData data) {

        Double number;

        if (TextUtils.isEmpty(data.content.toString()) || !RegexUtils.isRealNumber1(data.content.toString())) {
            number = null;
        } else {
            number = Double.valueOf(data.content.toString());
        }

        if (data.numberPickerMin != null) {
            if (number == null) {
                number = data.numberPickerMin;
            } else {
                if (number.doubleValue() < data.numberPickerMin.doubleValue()) {
                    number = data.numberPickerMin;
                }
            }
        }

        if (data.numberPickerMax != null) {
            if (number == null) {
                number = data.numberPickerMax;
            } else {
                if (number.doubleValue() > data.numberPickerMax.doubleValue()) {
                    number = data.numberPickerMax;
                }
            }
        }

        if (number == null) {
            number = 0d;
        }

        return number;
    }

    /**
     * 设置点击监听
     *
     * @param onFastClick
     */
    public void setOnFastClick(OnFastClick onFastClick) {
        this.onFastClick = onFastClick;
    }

    private FastItemData getDetailItemData(CommonBean bean, Field t) {

        FastItemData data = new FastItemData();
        FastItem annotation;
        boolean isNotEmpty;
        String label;
        boolean isNeedContent;
        int sortNumber;
        String itemsName;
        int leftIconRes;
        int rightIconRes;
        FastItemMode mode;
        int marginTop;
        String visible;
        Boolean readability;
        boolean isOnlyRead;
        int fileMaxCount;
        String numberPickerMax;
        String numberPickerMin;
        String numberPickerDif;
        Class<?> numberPickerType;
        int numberPickerPointCount;
        boolean isDate;
        StringUtils.DateFormatMode dateFormatMode;
        String contentHint;
        String textColorResName;
        Object textColorRes;

        annotation = t.getAnnotation(FastItem.class);

        isNotEmpty = annotation.isNotEmpty();
        label = annotation.label();
        isNeedContent = annotation.isNeedContent();
        sortNumber = annotation.sortNumber();
        itemsName = annotation.itemsName();
        leftIconRes = annotation.leftIconRes();
        rightIconRes = annotation.rightIconRes();
        mode = annotation.mode();
        marginTop = annotation.marginTop();
        visible = annotation.visible();
        isDate = annotation.isDate();
        dateFormatMode = annotation.dateFormatMode();
        contentHint = annotation.contentHint();
        textColorResName = annotation.textColorResName();

        //  右侧图标
        readability = bean.getOnlyReadItem(t.getName());
        isOnlyRead = readability == null ? true : readability;
        data.rightIconRes = rightIconRes;
        // 可读性
        data.setOnlyRead(isOnlyRead);
        // 必填
        data.isNotEmpty = isNotEmpty;
        // 标签
        data.label = label;
        data.isShowLabel = !TextUtils.isEmpty(label);
        // 内容
        data.isNeedContent = isNeedContent;
        data.content = getObjValue(bean, t.getName());
        if (data.content == null) {
            data.content = "";
        }
        // 排序号
        data.sortNumber = sortNumber;
        // 上间距
        data.marginTop = marginTop;
        // 模式
        data.fastItemMode = mode;
        if (data.fastItemMode == FastItemMode.File) {
            data.setMedia(bean.getMedia());
        }
        // 翻译数组名
        data.itemsName = itemsName;
        // 左侧图标
        data.leftIconRes = leftIconRes;
        data.isShowLeftIconRes = leftIconRes != -1;
        // 绑定的可见性变量名
        if (TextUtils.isEmpty(visible)) {
            data.visible = true;
        } else {
            String value = getStringValue(bean, visible);
            data.visible = TextUtils.equals(value, "true");
        }
        // 是否是日期
        data.isDate = isDate;
        if (isDate) {
            data.dateFormatMode = dateFormatMode;
        }
        data.contentHint = contentHint;
        data.textColorResName = textColorResName;
        textColorRes = getObjValue(bean, data.textColorResName);
        data.textColorRes = textColorRes;

        if (t.isAnnotationPresent(FastItemFileMaxCount.class)) {
            FastItemFileMaxCount annotation2 = t.getAnnotation(FastItemFileMaxCount.class);
            fileMaxCount = annotation2.maxCount();
            data.fileMaxCount = fileMaxCount;
        }

        if (t.isAnnotationPresent(FastItemNumberPicker.class)) {
            FastItemNumberPicker annotation3 = t.getAnnotation(FastItemNumberPicker.class);
            data.isShowNumberPicker = true;
            numberPickerMax = annotation3.max();
            numberPickerMin = annotation3.min();
            numberPickerDif = annotation3.dif();
            numberPickerType = annotation3.type();
            numberPickerPointCount = annotation3.pointCount();

            if (TextUtils.isEmpty(numberPickerMax) || !RegexUtils.isRealNumber1(numberPickerMax)) {
                data.numberPickerMax = null;
            } else {
                data.numberPickerMax = Double.valueOf(numberPickerMax);
            }

            if (TextUtils.isEmpty(numberPickerMin) || !RegexUtils.isRealNumber1(numberPickerMin)) {
                data.numberPickerMin = null;
            } else {
                data.numberPickerMin = Double.valueOf(numberPickerMin);
            }

            if (TextUtils.isEmpty(numberPickerDif) || !RegexUtils.isRealNumber1(numberPickerDif)) {
                data.numberPickerDif = 1d;
            } else {
                data.numberPickerDif = Double.valueOf(numberPickerDif);
            }

            data.numberPickerType = numberPickerType;
            data.numberPickerPointCount = numberPickerPointCount;

            if (data.numberPickerMin != null && data.numberPickerMax != null && data.numberPickerMax <= data.numberPickerMin) {
                data.numberPickerMax = null;
            }

            if (data.numberPickerDif < 0) {
                data.numberPickerDif = Math.abs(data.numberPickerDif);
            }

            if (data.numberPickerType == Integer.class) {

            } else if (data.numberPickerType == Long.class) {

            } else if (data.numberPickerType == Float.class) {

            } else if (data.numberPickerType == Double.class) {

            } else {
                data.numberPickerType = Integer.class;
            }

            if (data.numberPickerPointCount < 0) {
                data.numberPickerPointCount = 0;
            }
        }

        if (t.isAnnotationPresent(FastItemSwitch.class)) {
            if (t.getType() == boolean.class || t.getType() == Boolean.class) {
                data.isShowSwitchButton = true;
            }
        }

        if (!data.visible) {
            return null;
        }

        return data;
    }

    public ArrayList<FastItemData> getDetailItemData(CommonBean bean) {

        ArrayList<FastItemData> data = new ArrayList<>();
        ArrayList<Field> allField = getAllField(bean);
        FastItemData item;

        for (Field t : allField) {
            item = getDetailItemData(bean, t);

            if (item != null) {
                data.add(item);
            }
        }

        return data;
    }

    public FastItemData getDetailItemData(CommonBean bean, int sort) {

        ArrayList<Field> allField = getAllField(bean);
        Field t = null;
        FastItem annotation;

        for (Field p : allField) {
            annotation = p.getAnnotation(FastItem.class);
            if (annotation.sortNumber() == sort) {
                t = p;
                break;
            }
        }

        if (t != null) {
            return getDetailItemData(bean, t);
        }

        return null;
    }

    public ArrayList<Field> getAllField(CommonBean bean) {

        ArrayList<Field> result = new ArrayList<>();
        Field[] allField = bean.getClass().getDeclaredFields();
        Field temp;
        FastItem annotation1;
        FastItem annotation2;

        for (Field t : allField) {
            if (t.isAnnotationPresent(FastItem.class)) {
                result.add(t);
            }
        }

        for (int i = 1; i < result.size(); i++) {
            for (int j = 0; j < result.size() - i; j++) {
                annotation1 = result.get(j).getAnnotation(FastItem.class);
                annotation2 = result.get(j + 1).getAnnotation(FastItem.class);
                if (annotation1.sortNumber() > annotation2.sortNumber()) {
                    temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }

        return result;
    }

}
