package com.hg.hollowgoods.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            Class<?> clazz = obj.getClass();

            while (clazz != Object.class) {
                try {
                    Field field = clazz.getDeclaredField(valueName);

                    if (field != null) {
                        //设置些属性是可以访问的
                        field.setAccessible(true);
                        //得到此属性的值
                        result = field.get(obj);

                        break;
                    } else {
                        clazz = clazz.getSuperclass();
                    }
                } catch (Exception e) {
                    clazz = clazz.getSuperclass();
                }
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
            Class<?> clazz = obj.getClass();

            while (clazz != Object.class) {
                try {
                    Field f = clazz.getDeclaredField(valueName);
                    f.setAccessible(true);
                    f.set(obj, value);

                    break;
                } catch (Exception e) {
                    clazz = clazz.getSuperclass();
                }
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

    public static Object invokeMethod(Object obj, Method method, Object... param) {

        try {
            return method.invoke(obj, param);
        } catch (IllegalAccessException ignored) {

        } catch (InvocationTargetException ignored) {

        }

        return null;
    }

    public static Object invokeStaticMethod(Method method, Object... param) {

        try {
            return method.invoke(null, param);
        } catch (IllegalAccessException ignored) {

        } catch (InvocationTargetException ignored) {

        }

        return null;
    }

    public static Class<?> getClassByPackageName(String packageName) {

        try {
            return Class.forName(packageName);
        } catch (ClassNotFoundException ignored) {

        }

        return null;
    }

    public static Method getMethodByName(Class<?> clazz, String methodName, Class<?>... paramClass) {

        try {
            return clazz.getDeclaredMethod(methodName, paramClass);
        } catch (NoSuchMethodException ignored) {

        }

        return null;
    }

}
