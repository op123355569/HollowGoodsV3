package com.hg.hollowgoods.Adapter.Plugin;

import android.content.Context;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.FileSelectorUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: HollowGoods
 * @date: 2018年09月28日
 */
public class FileAdapter extends CommonAdapter<File> {

    private HashMap<String, File> selectedFiles = null;

    private FileSelectorUtils fileSelectorUtils = new FileSelectorUtils();

    public FileAdapter(Context context, int layoutId, List<File> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, File item, int position) {

        viewHolder.setText(R.id.tv_filename, item.getName());
        viewHolder.setImageResource(R.id.iv_icon, fileSelectorUtils.getFileIcon(item));

        if (getSelectedFiles() == null) {
            viewHolder.setVisible(R.id.cb_flag, false);
        } else {
            if (item.isDirectory()) {
                viewHolder.setVisible2(R.id.cb_flag, false);
            } else {
                viewHolder.setVisible2(R.id.cb_flag, true);
                viewHolder.setChecked(R.id.cb_flag, getSelectedFiles().get(item.getAbsolutePath()) != null);
            }
        }
    }

    public HashMap<String, File> getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(HashMap<String, File> selectedFiles) {
        this.selectedFiles = selectedFiles;
    }

}
