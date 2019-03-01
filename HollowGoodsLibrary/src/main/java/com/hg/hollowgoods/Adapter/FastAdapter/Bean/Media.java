package com.hg.hollowgoods.Adapter.FastAdapter.Bean;

import java.io.File;
import java.io.Serializable;

/**
 * Created by HG on 2018-04-08.
 */
public class Media implements Serializable {

    private String oldUrl;
    private String oldName;
    private String url;
    private File file;
    private boolean canRemove = true;

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
}
