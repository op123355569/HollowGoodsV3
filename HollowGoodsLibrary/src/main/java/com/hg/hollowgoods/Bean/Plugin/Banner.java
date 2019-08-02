package com.hg.hollowgoods.Bean.Plugin;

import com.hg.hollowgoods.Util.FileChangeUtils.HGWebFile;

import java.util.ArrayList;

/**
 * Banner封装类
 * Created by Hollow Goods on 2019-04-16.
 */
public class Banner {

    private long id;
    private int linkType;
    private String httpUrl;
    private String appUrl;
    private ArrayList<HGWebFile> fileList;
    private Integer res;

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public ArrayList<HGWebFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<HGWebFile> fileList) {
        this.fileList = fileList;
    }
}
