package com.hg.hollowgoods.Adapter.HGFastAdapter.Util;

import android.text.TextUtils;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemCustomize;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemDate;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemFile;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemGroup;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemNumber;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemWord;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemCustomizeData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemDateData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemFileData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemGroupData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemNumberData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemWordData;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ChoiceItem;
import com.hg.hollowgoods.Util.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 快速适配器数据处理工具类
 * Created by Hollow Goods on 2019-05-07.
 */
public class HGFastDataUtils {

    private HashMap<Class<?>, Boolean> annotationTag = new HashMap<>();
    private HashMap<Integer, String> fieldTag = new HashMap<>();
    private HashMap<Integer, CommonBean> itemTag = new HashMap<>();
    private ArrayList<HGFastItemCustomizeData> customizeItemTag = new ArrayList<>();
    private ArrayList<HGFastItemGroupData> groupItemTag = new ArrayList<>();

    private CommonBean getHGFastItemWordData(CommonBean data, Field field) {

        HGFastItemWordData result = new HGFastItemWordData();
        HGFastItemWord annotation;

        annotation = field.getAnnotation(HGFastItemWord.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setItemMode(annotation.itemMode());
        result.setNotEmpty(annotation.isNotEmpty());
        result.setLabel(annotation.label());
        result.setLeftIconRes(annotation.leftIconRes());
        result.setRightIconRes(annotation.rightIconRes());
        result.setVisibleName(annotation.visibleName());
        if (TextUtils.isEmpty(result.getVisibleName())) {
            result.setShow(true);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            result.setShow(Boolean.valueOf(value + ""));
        }
        result.setLabelTextColorResName(annotation.labelTextColorResName());
        if (TextUtils.isEmpty(result.getLabelTextColorResName())) {
            result.setLabelTextColorRes(R.color.txt_color_dark);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getLabelTextColorResName());
            if (value != null) {
                result.setLabelTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setLabelTextColorRes(R.color.txt_color_dark);
            }
        }
        result.setMarginTop(annotation.marginTop());
        result.setMarginLeft(annotation.marginLeft());
        result.setMarginBottom(annotation.marginBottom());
        result.setMarginRight(annotation.marginRight());

        // 特有属性
        result.setContentHint(annotation.contentHint());
        result.setContentTextColorResName(annotation.contentTextColorResName());
        if (TextUtils.isEmpty(result.getContentTextColorResName())) {
            result.setContentTextColorRes(R.color.txt_color_normal);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getContentTextColorResName());
            if (value != null) {
                result.setContentTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setContentTextColorRes(R.color.txt_color_normal);
            }
        }
        Object content = ReflectUtils.getObjValue(data, field.getName());
        if (content == null) {
            result.setContent("");
        } else {
            result.setContent(content + "");
        }
        result.setSingleChoiceMode(annotation.singleChoiceMode());
        result.setSingleChoiceName(annotation.singleChoiceName());
        result.setSingleChoiceNameClass(annotation.singleChoiceNameClass());
        if (!TextUtils.isEmpty(result.getSingleChoiceName()) && result.getSingleChoiceNameClass() != HGFastItemWord.class) {
            result.setSingleChoiceItem(ReflectUtils.getStaticObjValue(result.getSingleChoiceNameClass(), result.getSingleChoiceName()));
        } else {
            result.setSingleChoiceItem(null);
        }
        result.setHttpMethod(annotation.httpMethod());
        result.setSingleChoiceNetRequestParamName(annotation.singleChoiceNetRequestParamName());
        result.setSingleChoiceNetDataKeyName(annotation.singleChoiceNetDataKeyName());
        result.setSingleChoiceNetDataValueName(annotation.singleChoiceNetDataValueName());
        result.setSingleChoiceNetDataValueDescribeName(annotation.singleChoiceNetDataValueDescribeName());
        if (!TextUtils.isEmpty(result.getSingleChoiceNetRequestParamName())) {
            result.setSingleChoiceNetRequestParam(ReflectUtils.getObjValue(data, result.getSingleChoiceNetRequestParamName()));
        }
        result.setSingleChoiceNetDataTypeName(annotation.singleChoiceNetDataTypeName());
        if (!TextUtils.isEmpty(result.getSingleChoiceNetDataTypeName())) {
            result.setSingleChoiceNetDataType((Type) ReflectUtils.getObjValue(data, result.getSingleChoiceNetDataTypeName()));
        }

        // 父类属性
        result.setOnlyRead(data.getOnlyReadItem(result.getItemId()));
        result.setConfigInput(data.getConfigInput(result.getItemId()));

        // 绑定的可见性变量名
        if (!TextUtils.isEmpty(result.getVisibleName())) {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            if (!TextUtils.equals(value + "", "true")) {
                return null;
            }
        }

        return result;
    }

    private CommonBean getHGFastItemFileData(CommonBean data, Field field) {

        HGFastItemFileData result = new HGFastItemFileData();
        HGFastItemFile annotation;

        annotation = field.getAnnotation(HGFastItemFile.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setItemMode(annotation.itemMode());
        result.setNotEmpty(annotation.isNotEmpty());
        result.setLabel(annotation.label());
        result.setLeftIconRes(annotation.leftIconRes());
        result.setRightIconRes(annotation.rightIconRes());
        result.setVisibleName(annotation.visibleName());
        result.setLabelTextColorResName(annotation.labelTextColorResName());
        if (TextUtils.isEmpty(result.getLabelTextColorResName())) {
            result.setLabelTextColorRes(R.color.txt_color_dark);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getLabelTextColorResName());
            if (value != null) {
                result.setLabelTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setLabelTextColorRes(R.color.txt_color_dark);
            }
        }
        result.setMarginTop(annotation.marginTop());
        result.setMarginLeft(annotation.marginLeft());
        result.setMarginBottom(annotation.marginBottom());
        result.setMarginRight(annotation.marginRight());

        // 特有属性
        result.setFileMode(annotation.fileMode());
        result.setMaxCount(annotation.maxCount());
        result.setQuality(annotation.quality());
        result.setFileFilter(annotation.fileFilter());

        // 父类属性
        result.setOnlyRead(data.getOnlyReadItem(result.getItemId()));
        result.setItemMedia(data.getMedia().get(result.getItemId()));

        // 绑定的可见性变量名
        if (!TextUtils.isEmpty(result.getVisibleName())) {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            if (!TextUtils.equals(value + "", "true")) {
                return null;
            }
        }

        return result;
    }

    private CommonBean getHGFastItemDateData(CommonBean data, Field field) {

        HGFastItemDateData result = new HGFastItemDateData();
        HGFastItemDate annotation;

        annotation = field.getAnnotation(HGFastItemDate.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setItemMode(annotation.itemMode());
        result.setNotEmpty(annotation.isNotEmpty());
        result.setLabel(annotation.label());
        result.setLeftIconRes(annotation.leftIconRes());
        result.setRightIconRes(annotation.rightIconRes());
        result.setVisibleName(annotation.visibleName());
        result.setLabelTextColorResName(annotation.labelTextColorResName());
        if (TextUtils.isEmpty(result.getLabelTextColorResName())) {
            result.setLabelTextColorRes(R.color.txt_color_dark);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getLabelTextColorResName());
            if (value != null) {
                result.setLabelTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setLabelTextColorRes(R.color.txt_color_dark);
            }
        }
        result.setMarginTop(annotation.marginTop());
        result.setMarginLeft(annotation.marginLeft());
        result.setMarginBottom(annotation.marginBottom());
        result.setMarginRight(annotation.marginRight());

        // 特有属性
        result.setDateFormatMode(annotation.dateFormatMode());

        // 父类属性
        result.setContentHint(annotation.contentHint());
        result.setContentTextColorResName(annotation.contentTextColorResName());
        if (TextUtils.isEmpty(result.getContentTextColorResName())) {
            result.setContentTextColorRes(R.color.txt_color_normal);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getContentTextColorResName());
            if (value != null) {
                result.setContentTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setContentTextColorRes(R.color.txt_color_normal);
            }
        }
        Object content = ReflectUtils.getObjValue(data, field.getName());
        if (content == null) {
            result.setContent("");
        } else {
            result.setContent(content + "");
        }
//        result.setSingleChoiceName(annotation.singleChoiceName());
//        result.setSingleChoiceNameClass(annotation.singleChoiceNameClass());
        if (!TextUtils.isEmpty(result.getSingleChoiceName()) && result.getSingleChoiceNameClass() != HGFastItemWord.class) {
            result.setSingleChoiceItem(ReflectUtils.getStaticObjValue(result.getSingleChoiceNameClass(), result.getSingleChoiceName()));
        } else {
            result.setSingleChoiceItem(null);
        }

        // 祖类属性
        result.setOnlyRead(data.getOnlyReadItem(result.getItemId()));
        result.setConfigInput(data.getConfigInput(result.getItemId()));

        // 绑定的可见性变量名
        if (!TextUtils.isEmpty(result.getVisibleName())) {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            if (!TextUtils.equals(value + "", "true")) {
                return null;
            }
        }

        return result;
    }

    private CommonBean getHGFastItemNumberData(CommonBean data, Field field) {

        HGFastItemNumberData result = new HGFastItemNumberData();
        HGFastItemNumber annotation;

        annotation = field.getAnnotation(HGFastItemNumber.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setItemMode(annotation.itemMode());
        result.setNotEmpty(annotation.isNotEmpty());
        result.setLabel(annotation.label());
        result.setLeftIconRes(annotation.leftIconRes());
        result.setRightIconRes(annotation.rightIconRes());
        result.setVisibleName(annotation.visibleName());
        result.setLabelTextColorResName(annotation.labelTextColorResName());
        if (TextUtils.isEmpty(result.getLabelTextColorResName())) {
            result.setLabelTextColorRes(R.color.txt_color_dark);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getLabelTextColorResName());
            if (value != null) {
                result.setLabelTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setLabelTextColorRes(R.color.txt_color_dark);
            }
        }
        result.setMarginTop(annotation.marginTop());
        result.setMarginLeft(annotation.marginLeft());
        result.setMarginBottom(annotation.marginBottom());
        result.setMarginRight(annotation.marginRight());

        // 特有属性
        result.setMinValue(annotation.minValue());
        result.setMaxValue(annotation.maxValue());
        result.setDifValue(annotation.difValue());

        // 父类属性
        result.setContentHint(annotation.contentHint());
        result.setContentTextColorResName(annotation.contentTextColorResName());
        if (TextUtils.isEmpty(result.getContentTextColorResName())) {
            result.setContentTextColorRes(R.color.txt_color_normal);
        } else {
            Object value = ReflectUtils.getObjValue(data, result.getContentTextColorResName());
            if (value != null) {
                result.setContentTextColorRes(Integer.valueOf(value + ""));
            } else {
                result.setContentTextColorRes(R.color.txt_color_normal);
            }
        }
        Object content = ReflectUtils.getObjValue(data, field.getName());
        if (content == null) {
            result.setContent("");
        } else {
            result.setContent(content + "");
        }
//        result.setSingleChoiceName(annotation.singleChoiceName());
//        result.setSingleChoiceNameClass(annotation.singleChoiceNameClass());
        if (!TextUtils.isEmpty(result.getSingleChoiceName()) && result.getSingleChoiceNameClass() != HGFastItemWord.class) {
            result.setSingleChoiceItem(ReflectUtils.getStaticObjValue(result.getSingleChoiceNameClass(), result.getSingleChoiceName()));
        } else {
            result.setSingleChoiceItem(null);
        }

        // 祖类属性
        result.setOnlyRead(data.getOnlyReadItem(result.getItemId()));
        result.setConfigInput(data.getConfigInput(result.getItemId()));

        // 绑定的可见性变量名
        if (!TextUtils.isEmpty(result.getVisibleName())) {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            if (!TextUtils.equals(value + "", "true")) {
                return null;
            }
        }

        return result;
    }

    private CommonBean getHGFastItemCustomizeData(CommonBean data, Field field) {

        HGFastItemCustomizeData result = new HGFastItemCustomizeData();
        HGFastItemCustomize annotation;

        annotation = field.getAnnotation(HGFastItemCustomize.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setVisibleName(annotation.visibleName());

        // 特有属性
        result.setItemType(annotation.itemType());
        result.setItemClass(annotation.itemClass());

        // 父类属性
        result.setOnlyRead(data.getOnlyReadItem(result.getItemId()));

        // 绑定的可见性变量名
        if (!TextUtils.isEmpty(result.getVisibleName())) {
            Object value = ReflectUtils.getObjValue(data, result.getVisibleName());
            if (!TextUtils.equals(value + "", "true")) {
                return null;
            }
        }

        return result;
    }

    private CommonBean getHGFastItemGroupData(CommonBean data, Field field) {

        HGFastItemGroupData result = new HGFastItemGroupData();
        HGFastItemGroup annotation;

        annotation = field.getAnnotation(HGFastItemGroup.class);

        // 公共属性
        result.setSortNumber(annotation.sortNumber());
        result.setItemId(annotation.id());
        result.setMarginTop(annotation.marginTop());
        result.setMarginLeft(annotation.marginLeft());
        result.setMarginBottom(annotation.marginBottom());
        result.setMarginRight(annotation.marginRight());

        // 特有属性
        result.setGroupItemIds(annotation.groupItemIds());
        result.setNeedCardView(annotation.isNeedCardView());

        return result;
    }

    /**
     * 解析列表数据
     *
     * @param data data
     * @return ArrayList<CommonBean>
     */
    public ArrayList<CommonBean> resolveHGFastItemData(CommonBean data) {

        ArrayList<CommonBean> result = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<Integer> sortNumbers = new ArrayList<>();
        Field[] allField = data.getClass().getDeclaredFields();
        CommonBean item;
        Field temp1;
        int temp2;

        annotationTag.clear();
        fieldTag.clear();
        itemTag.clear();
        customizeItemTag.clear();
        groupItemTag.clear();

        // 获取所有注解
        for (Field t : allField) {

            if (t.isAnnotationPresent(HGFastItemWord.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemWord.class).sortNumber());

                item = getHGFastItemWordData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        result.add(item);
                        annotationTag.put(HGFastItemWord.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);
                    }
                }
            } else if (t.isAnnotationPresent(HGFastItemFile.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemFile.class).sortNumber());

                item = getHGFastItemFileData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        result.add(item);
                        annotationTag.put(HGFastItemFile.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);
                    }
                }
            } else if (t.isAnnotationPresent(HGFastItemDate.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemDate.class).sortNumber());

                item = getHGFastItemDateData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        result.add(item);
                        annotationTag.put(HGFastItemDate.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);
                    }
                }
            } else if (t.isAnnotationPresent(HGFastItemNumber.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemNumber.class).sortNumber());

                item = getHGFastItemNumberData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        result.add(item);
                        annotationTag.put(HGFastItemNumber.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);
                    }
                }
            } else if (t.isAnnotationPresent(HGFastItemCustomize.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemCustomize.class).sortNumber());

                item = getHGFastItemCustomizeData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        item.addObj(HGParamKey.ObjData.getValue(), ReflectUtils.getObjValue(data, t.getName()));
                        result.add(item);
                        annotationTag.put(HGFastItemCustomize.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);

                        customizeItemTag.add(item.getData());
                    }
                }
            } else if (t.isAnnotationPresent(HGFastItemGroup.class)) {
                fields.add(t);
                sortNumbers.add(t.getAnnotation(HGFastItemGroup.class).sortNumber());

                item = getHGFastItemGroupData(data, t);

                if (item != null) {
                    boolean isShow = (boolean) ReflectUtils.getObjValue(item, "isShow");
                    if (isShow) {
                        result.add(item);
                        annotationTag.put(HGFastItemGroup.class, true);
                        fieldTag.put(item.getItemId(), t.getName());
                        itemTag.put(item.getItemId(), item);

                        groupItemTag.add(item.getData());
                    }
                }
            }
        }

        // 按sortNumber从小到大排序
        for (int i = 1; i < fields.size(); i++) {
            for (int j = 0; j < fields.size() - i; j++) {
                if (sortNumbers.get(j) > sortNumbers.get(j + 1)) {
                    temp1 = fields.get(j);
                    fields.set(j, fields.get(j + 1));
                    fields.set(j + 1, temp1);

                    temp2 = sortNumbers.get(j);
                    sortNumbers.set(j, sortNumbers.get(j + 1));
                    sortNumbers.set(j + 1, temp2);

                    item = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, item);
                }
            }
        }

        if (annotationTag.get(HGFastItemGroup.class) != null) {
            int[] ids;
            CommonBean commonBean;

            for (HGFastItemGroupData t : groupItemTag) {
                ids = t.getGroupItemIds();

                if (ids != null) {
                    for (int id : ids) {
                        commonBean = itemTag.get(id);
                        commonBean.setNeedRemove(true);

                        t.getGroupData().add(commonBean);
                    }
                }
            }
        }

        return result;
    }

    public void removeOverItems(ArrayList<CommonBean> data) {

        for (int i = 0; i < data.size(); ) {
            if (data.get(i).isNeedRemove()) {
                data.remove(i);
            } else {
                i++;
            }
        }

        boolean hasDate = false;
        boolean hasNumber = false;
        boolean hasWord = false;
        boolean hasFile = false;
        boolean hasCustomize = false;

        for (CommonBean t : data) {
            if (t instanceof HGFastItemDateData) {
                hasDate = true;
            } else if (t instanceof HGFastItemNumberData) {
                hasNumber = true;
            } else if (t instanceof HGFastItemWordData) {
                hasWord = true;
            } else if (t instanceof HGFastItemFileData) {
                hasFile = true;
            } else if (t instanceof HGFastItemCustomizeData) {
                hasCustomize = true;
            }
        }

        annotationTag.put(HGFastItemDate.class, hasDate);
        annotationTag.put(HGFastItemNumber.class, hasNumber);
        annotationTag.put(HGFastItemWord.class, hasWord);
        annotationTag.put(HGFastItemFile.class, hasFile);
        annotationTag.put(HGFastItemCustomize.class, hasCustomize);
    }

    public HashMap<Class<?>, Boolean> getAnnotationTag() {
        return annotationTag;
    }

    public void setFieldTag(HashMap<Integer, String> fieldTag) {
        this.fieldTag = fieldTag;
    }

    public HashMap<Integer, String> getFieldTag() {
        return fieldTag;
    }

    public HashMap<Integer, CommonBean> getItemTag() {
        return itemTag;
    }

    public ArrayList<HGFastItemCustomizeData> getCustomizeItemTag() {
        return customizeItemTag;
    }

    public static int getSingleChoiceItemCheckedPosition(Object singleChoiceItem, String content) {

        ArrayList<ChoiceItem> items = (ArrayList<ChoiceItem>) singleChoiceItem;
        int i = 0;

        for (ChoiceItem t : items) {
            if (TextUtils.equals(t.getItem() + "", content)) {
                return i;
            }

            i++;
        }

        return -1;
    }

    public static Object getSingleChoiceItemCheckedValue(Object singleChoiceItem, int checkedPosition) {

        if (checkedPosition < 0) {
            return "";
        }

        return ((ArrayList<ChoiceItem>) singleChoiceItem).get(checkedPosition).getItem();
    }

}
