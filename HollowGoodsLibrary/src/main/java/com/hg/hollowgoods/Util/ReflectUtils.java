package com.hg.hollowgoods.Util;

import java.lang.reflect.Field;

/**
 * Created by Hollow Goods on 2019-05-07.
 */
public class ReflectUtils {

    /**
     * 反射获取属性
     *
     * @param obj       obj
     * @param valueName valueName
     * @return Object
     */
    public static Object getObjValue(Object obj, String valueName) {

        Object result = null;

        if (obj != null) {
            try {
                Class clazz = obj.getClass();
                Field field = clazz.getDeclaredField(valueName);

                if (field != null) {
                    //设置些属性是可以访问的
                    field.setAccessible(true);
                    //得到此属性的值
                    result = field.get(obj);
                }
            } catch (Exception ignored) {

            }
        }

        return result;
    }

    /**
     * 反射设置属性
     *
     * @param obj       obj
     * @param valueName valueName
     * @param value     value
     */
    public static void setObjValue(Object obj, String valueName, Object value) {
        if (obj != null && value != null) {
            try {
                Field f = obj.getClass().getDeclaredField(valueName);
                f.setAccessible(true);
                f.set(obj, value);
            } catch (Exception ignored) {

            }
        }
    }

    public static Class<?> getFieldType(Object obj, String valueName) {

        if (obj != null) {
            try {
                Field f = obj.getClass().getDeclaredField(valueName);
                f.setAccessible(true);

                return f.getType();
            } catch (Exception ignored) {

            }
        }

        return null;
    }

    public static Object getStaticObjValue(Class<?> clazz, String valueName) {

        try {
            return clazz.getDeclaredField(valueName).get(null);
        } catch (Exception ignored) {

        }

        return null;
    }

}
