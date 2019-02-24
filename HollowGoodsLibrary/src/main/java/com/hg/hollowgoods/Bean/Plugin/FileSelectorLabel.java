package com.hg.hollowgoods.Bean.Plugin;

import java.io.File;

/**
 * @ClassName:
 * @Description:
 * @author: HollowGoods
 * @date: 2018年09月28日
 */
public class FileSelectorLabel {

    private File file;
    private int lastOffset;
    private int lastPosition;

    public FileSelectorLabel(File file, int lastOffset, int lastPosition) {
        this.file = file;
        this.lastOffset = lastOffset;
        this.lastPosition = lastPosition;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(int lastOffset) {
        this.lastOffset = lastOffset;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

}
