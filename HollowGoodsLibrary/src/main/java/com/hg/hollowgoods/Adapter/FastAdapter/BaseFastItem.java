package com.hg.hollowgoods.Adapter.FastAdapter;

import android.text.TextUtils;

import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.RegexUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:基快速适配器Item
 * @Description:
 * @author: HollowGoods
 * @date: 2018年10月22日
 */
public class BaseFastItem {

    public final String CONTENT_ICON_HEAD = "*#HG#*";
    private Class<?> itemsNameClass;

    public void setItemsNameClass(Class<?> itemsNameClass) {
        this.itemsNameClass = itemsNameClass;
    }

    /**
     * 反射获取String类型的值
     *
     * @param obj
     * @param valueName
     * @return
     */
    public String getStringValue(Object obj, String valueName) {
        Object value = getObjValue(obj, valueName);
        return value == null ? "" : value + "";
    }

    /**
     * 反射获取变量值
     *
     * @param obj
     * @param valueName
     * @return
     */
    public Object getObjValue(Object obj, String valueName) {

        Object result = null;

        try {
            Class clazz = obj.getClass();
            Field field = clazz.getDeclaredField(valueName);

            if (field != null) {
                //设置些属性是可以访问的
                field.setAccessible(true);
                //得到此属性的值
                result = field.get(obj);
            }
        } catch (Exception e) {
            LogUtils.Log(e.getMessage(), "Exception");
        }

        return result;
    }

    /**
     * 获取翻译数组
     *
     * @param name
     * @return
     */
    public Object getContentItems(String name) {

        try {
            return itemsNameClass.getDeclaredField(name).get(null);
        } catch (IllegalAccessException e) {

        } catch (NoSuchFieldException e) {

        }

        return null;
    }

    public String getRealValue(Object value, String itemsName) {

        if (!TextUtils.isEmpty(itemsName)) {
            Object objItems = getContentItems(itemsName);

            if (objItems == null) {
                value = "";
            } else {
                if (objItems instanceof String[]) {
                    if (RegexUtils.isWholeNumber(value.toString())) {
                        int index = Integer.valueOf(value.toString());
                        String[] items = (String[]) objItems;

                        if (index < items.length && index >= 0) {
                            value = items[index];
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                } else if (objItems instanceof Integer[]) {
                    if (RegexUtils.isWholeNumber(value.toString())) {
                        int index = Integer.valueOf(value.toString());
                        Integer[] items = (Integer[]) objItems;

                        if (index < items.length && index >= 0) {
                            value = CONTENT_ICON_HEAD + items[index];
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                } else if (objItems instanceof Map) {
                    Map items = (Map) objItems;
                    Object temp = items.get(value);
                    value = temp == null ? "" : temp + "";
                } else if (objItems instanceof List) {
                    if (RegexUtils.isWholeNumber(value.toString())) {
                        int index = Integer.valueOf(value.toString());
                        List items = (List) objItems;

                        if (items != null && index < items.size() && index >= 0) {
                            Object temp = items.get(index);
                            value = temp == null ? "" : temp + "";
                        } else {
                            value = "";
                        }
                    } else {
                        value = "";
                    }
                } else {
                    value = "";
                }
            }
        } else {
            value = (value == null ? "" : value.toString());
        }

        return value.toString();
    }

    public String getRealValueByField(CommonBean item, String fieldName, String itemsName) {
        Object value = getObjValue(item, fieldName);
        return getRealValue(value, itemsName);
    }

}
