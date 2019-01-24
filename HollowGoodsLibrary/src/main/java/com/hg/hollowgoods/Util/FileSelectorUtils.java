package com.hg.hollowgoods.Util;

import com.hg.hollowgoods.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName:
 * @Description:
 * @author: 马禛
 * @date: 2018年09月28日
 */
public class FileSelectorUtils {

    private OnGetFileFinishListener onGetFileFinishListener;
    private GetFileThread getFileThread = null;
    private String mPath;
    private boolean mNeedSystemFile;
    private boolean mNeedSort;
    private ArrayList<String> mFileFilter;

    public interface OnGetFileFinishListener {
        void onGetFileFinish(ArrayList<File> result);
    }

    public void setOnGetFileFinishListener(OnGetFileFinishListener onGetFileFinishListener) {
        this.onGetFileFinishListener = onGetFileFinishListener;
    }

    /**
     * 获取指定路径下的文件
     *
     * @param path           路径
     * @param needSystemFile 是否获取需要系统文件 false 过滤.开头的文件
     */
    public void getFiles(String path, boolean needSystemFile, boolean needSort, ArrayList<String> fileFilter) {

        mPath = path;
        mNeedSystemFile = needSystemFile;
        mNeedSort = needSort;
        mFileFilter = fileFilter;

        if (getFileThread != null) {
            getFileThread.stopGetFile();
        }

        getFileThread = new GetFileThread();
        getFileThread.start();
    }

    private void sortFile(ArrayList<File> files) {

        File temp;

        for (int i = 1; i < files.size(); i++) {
            for (int j = 0; j < files.size() - i; j++) {
                if (files.get(j).getName().toLowerCase().compareTo(files.get(j + 1).getName().toLowerCase()) > 0) {
                    temp = files.get(j);
                    files.set(j, files.get(j + 1));
                    files.set(j + 1, temp);
                }
            }
        }
    }

    private HashMap<String, Integer> fileIcons = new HashMap<String, Integer>() {
        {
            // 文档类
            put("doc", R.drawable.ic_file_ex_doc);
            put("docx", R.drawable.ic_file_ex_docx);
            put("xls", R.drawable.ic_file_ex_xls);
            put("xlsx", R.drawable.ic_file_ex_xlsx);
            put("ppt", R.drawable.ic_file_ex_ppt);
            put("pptx", R.drawable.ic_file_ex_pptx);
            put("pdf", R.drawable.ic_file_ex_pdf);
            put("txt", R.drawable.ic_file_ex_txt);
            // 音/视频类
            put("avi", R.drawable.ic_file_ex_avi);
            put("mkv", R.drawable.ic_file_ex_mkv);
            put("rmvb", R.drawable.ic_file_ex_rmvb);
            put("wav", R.drawable.ic_file_ex_wav);
            put("swf", R.drawable.ic_file_ex_swf);
            put("mp4", R.drawable.ic_file_ex_mp4);
            put("mpg", R.drawable.ic_file_ex_mpg);
            put("mp3", R.drawable.ic_file_ex_mp3);
            // 图片类
            put("jpg", R.drawable.ic_file_ex_jpg);
            put("png", R.drawable.ic_file_ex_png);
            put("gif", R.drawable.ic_file_ex_gif);
            put("psd", R.drawable.ic_file_ex_psd);
            put("svg", R.drawable.ic_file_ex_svg);
            // Window类
            put("exe", R.drawable.ic_file_ex_exe);
            put("dll", R.drawable.ic_file_ex_dll);
            // 开发文件类
            put("html", R.drawable.ic_file_ex_html);
            put("java", R.drawable.ic_file_ex_java);
            // 压缩包类
            put("rar", R.drawable.ic_file_ex_rar);
            put("zip", R.drawable.ic_file_ex_zip);
            put("apk", R.drawable.ic_file_ex_apk);
            // 不支持的扩展名
            put("?", R.drawable.ic_file_ex_other);
            // 文件夹
            put("dir", R.drawable.ic_file_ex_folder);
        }
    };

    public Integer getFileIcon(File file) {

        String extensionName = file.getName().toLowerCase();

        if (file.isDirectory()) {// 文件夹
            return fileIcons.get("dir");
        }

        int index = extensionName.lastIndexOf(".");
        if (index == -1) {
            extensionName = "?";
        } else {
            extensionName = extensionName.substring(index + 1);
            if (fileIcons.get(extensionName) == null) {
                extensionName = "?";
            }
        }

        return fileIcons.get(extensionName);
    }

    private class GetFileThread extends Thread {

        private boolean canBackData = true;

        public void stopGetFile() {
            canBackData = false;
        }

        @Override
        public void run() {
            getFiles();
        }

        private void getFiles() {

            ArrayList<File> result = new ArrayList<>();

            File file = new File(mPath);
            File[] listFile;
            int index;

            if (file != null) {
                listFile = file.listFiles();

                if (listFile != null) {
                    for (File t : listFile) {
                        if (!mNeedSystemFile) {
                            if (t.getName().startsWith(".")) {
                                continue;
                            }

                        }

                        if (mFileFilter != null && mFileFilter.size() > 0) {
                            if (!t.isDirectory()) {
                                index = t.getName().lastIndexOf(".");

                                if (index == -1) {
                                    continue;
                                } else {
                                    if (mFileFilter.indexOf(t.getName().substring(index).toLowerCase()) == -1) {
                                        continue;
                                    }
                                }
                            }
                        }

                        result.add(t);
                    }
                }
            }

            if (mNeedSort) {
                sortFile(result);
            }

            if (canBackData && onGetFileFinishListener != null) {
                onGetFileFinishListener.onGetFileFinish(result);
            }
        }
    }

}
