package com.hg.hollowgoods.Util.IP;

import com.hg.hollowgoods.Util.LogUtils;
import com.hg.hollowgoods.Util.XUtils.XDBUtils;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HG on 2017-07-11.
 */

public class IPDBHelper {

    public List<IPConfig> findAll() {

        try {
            List<IPConfig> result = XDBUtils.getDbManager().findAll(IPConfig.class);
            return result;
        } catch (DbException e) {
            LogUtils.Log(e.getMessage());
        }

        return null;
    }

    public void save(ArrayList<IPConfig> data) {

        try {
            delete();
            XDBUtils.getDbManager().save(data);
        } catch (DbException e) {
            LogUtils.Log(e.getMessage());
        }
    }

    public void delete() {

        try {
            XDBUtils.getDbManager().delete(IPConfig.class);
        } catch (DbException e) {
            LogUtils.Log(e.getMessage());
        }
    }

}
