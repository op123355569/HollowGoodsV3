package com.hg.hollowgoods.Adapter.FastAdapter.Bean;

import java.io.File;
import java.io.Serializable;

/**
 * Created by HG on 2018-04-08.
 */
public class Media implements Serializable {

    /**** 原始链接 ****/
    private String oldUrl;
    /**** 原始名称 ****/
    private String oldName;
    /**** 完整链接 ****/
    private String url;
    /**** 本地文件 ****/
    private File file;
    /**** 是否可以删除 ****/
    private boolean canRemove = true;
    /**** Tag ****/
    private Object tag;

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldUrl() {
        return oldUrl;
    }

    public void setOldUrl(String oldUrl) {
        this.oldUrl = oldUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
