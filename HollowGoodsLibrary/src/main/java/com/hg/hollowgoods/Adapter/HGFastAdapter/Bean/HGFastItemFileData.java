package com.hg.hollowgoods.Adapter.HGFastAdapter.Bean;

import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.FileMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;

/**
 * 文件模板封装类
 * Created by Hollow Goods on 2019-05-09.
 */
public class HGFastItemFileData extends BaseHGFastItemData {

    private FileMode fileMode;
    private int maxCount;
    private int quality;
    private String fileFilter;

    public HGFastItemFileData() {
        super(ItemType.ItemFile.getValue());
    }

    public FileMode getFileMode() {
        return fileMode;
    }

    public void setFileMode(FileMode fileMode) {
        this.fileMode = fileMode;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(String fileFilter) {
        this.fileFilter = fileFilter;
    }
}
