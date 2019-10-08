package com.hg.hollowgoods.Widget.BugView;

/**
 * Created by Hollow Goods 2017-06-01.
 */

public class BugData {

    private String moduleName;
    private String imgPath;
    private String describe;
    private boolean isRepair;

    public BugData() {
        isRepair = false;
    }

    public BugData(String moduleName, String imgPath, String describe) {
        this.moduleName = moduleName;
        this.imgPath = imgPath;
        this.describe = describe;
        isRepair = false;
    }

    public boolean isRepair() {
        return isRepair;
    }

    public void setRepair(boolean repair) {
        isRepair = repair;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
