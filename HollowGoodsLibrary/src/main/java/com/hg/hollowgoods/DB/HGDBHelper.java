package com.hg.hollowgoods.DB;

import com.hg.hollowgoods.Util.XUtils.XDBUtils;

import org.xutils.ex.DbException;

import java.util.List;

public class HGDBHelper {

    public <T> T findFirst(Class<T> clazz) {

        try {
            return XDBUtils.getDbManager().findFirst(clazz);
        } catch (DbException e) {

        }

        return null;
    }

    public <T> List<T> findAll(Class<T> clazz) {

        try {
            return XDBUtils.getDbManager().findAll(clazz);
        } catch (DbException e) {

        }

        return null;
    }

    public void delete(Object obj) {
        try {
            XDBUtils.getDbManager().delete(obj);
        } catch (DbException e) {

        }
    }

    public void deleteAll(Class<?> clazz) {
        try {
            XDBUtils.getDbManager().delete(clazz);
        } catch (DbException e) {

        }
    }

    public void save(Object obj) {
        try {
            XDBUtils.getDbManager().save(obj);
        } catch (DbException e) {

        }
    }

    public void updateAll(Class<?> clazz, Object obj) {
        deleteAll(clazz);
        save(obj);
    }

}
