package com.hg.hollowgoods.Util.FileChangeUtils;

import java.io.Serializable;

/**
 * 网络文件
 * Created by Hollow Goods on 2019-04-03.
 */
public class HGWebFile implements Serializable {

    /**
     * 文件本地名称
     */
    private String fileLocalName;

    /**
     * 文件保存名称（用于图片请求）
     */
    private String fileUrlName;

    public String getFileLocalName() {
        return fileLocalName;
    }

    public void setFileLocalName(String fileLocalName) {
        this.fileLocalName = fileLocalName;
    }

    public String getFileUrlName() {
        return fileUrlName;
    }

    public void setFileUrlName(String fileUrlName) {
        this.fileUrlName = fileUrlName;
    }
}
