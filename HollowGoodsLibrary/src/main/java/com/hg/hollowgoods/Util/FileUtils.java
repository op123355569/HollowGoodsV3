package com.hg.hollowgoods.Util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * Created by Hollow Goods on unknown.
 */
@SuppressLint("DefaultLocale")
public class FileUtils {

    private static String[] format = {
            ".3gp",
            ".avi",
            ".bmp",
            ".doc",
            ".gif",
            ".jpg",
            ".mp3",
            ".mp4",
            ".pdf",
            ".png",
            ".ppt",
            ".rar",
            ".rmvb",
            ".txt",
            ".zip",
    };

    /**
     * 复制文件
     */
    public static final String CODE_COPY = "copy";
    /**
     * 剪切文件（移动）
     */
    public static final String CODE_CUT = "cut";

    /**
     * 获取指定路径下的文件路径和文件名清单
     *
     * @param path     路径
     * @param filePath 获取结果文件路径
     * @param fileName 获取结果文件名
     */
    public static void getFilePathAndName(String path, List<String> filePath, List<String> fileName) {

        File file = new File(path);
        File[] listFile = file.listFiles();

        for (File f : listFile) {
            fileName.add(f.getName());
            filePath.add(f.getPath());
        }

        if (filePath != null) {
            sort(fileName);
            sort(filePath);
        }
    }

    /**
     * 获取指定路径下的文件
     *
     * @param path 路径
     */
    public static File[] getPathFile(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    /**
     * 排序，从大到小
     *
     * @param data
     */
    public static void sort(List<String> data) {

        String temp;

        for (int i = 1; i < data.size(); i++) {
            for (int j = 0; j < data.size() - i; j++) {
                if (data.get(j).toLowerCase().compareTo(data.get(j + 1).toLowerCase()) < 0) {
                    temp = data.get(j);
                    data.set(j, data.get(j + 1));
                    data.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * 获取文件格式
     *
     * @param filePath 文件路径
     * @return dir 文件夹 unknown 未知 其他返回格式带.
     */
    public static String getFileFormat(String filePath) {

        File file = new File(filePath);

        if (file.isDirectory()) {
            return "dir";
        }

        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            return "unknown";
        }

        return fileName.substring(index);
    }

    /**
     * 读取文件从SD卡
     *
     * @param path 如 sdcard/123.txt
     * @return 返回读取的数据
     */
    public static String loadFromSDCard(String path) {

        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        byte[] data = null;

        try {
            // 创建路径
            File myDiary = new File(path);
            // 创建文件输入流
            fis = new FileInputStream(myDiary);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
            int count;

            while ((count = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }

            data = baos.toByteArray();
        } catch (Exception ignored) {

        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException ignored) {

            }
        }

        if (data != null) {
            return new String(data);
        }

        return null;
    }

    /**
     * 保存txt文件到SD卡上
     *
     * @param path 如 sdcard/123.txt
     * @param text 文件内容
     */
    public static void saveToSD(String path, String text) {

        // 写入txt文件
        File file = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            fos.close();
        } catch (Exception ignored) {

        }
    }

    /**
     * 复制文件 从源路径到另一路径
     *
     * @param copyPath            源路径
     * @param destinationFilePath 目标路径
     * @param code                代码 复制/剪切 CODE_COPY/CODE_CUT
     */
    public static void copyFile(List<String> copyPath, String destinationFilePath, String code) {

        File desFileDer = new File(destinationFilePath);

        if (!desFileDer.exists()) {
            desFileDer.mkdirs();
        }

        for (int j = 0; j < copyPath.size(); j++) {
            File srcFile = new File(copyPath.get(j));

            if (!srcFile.isDirectory()) {
                FileInputStream in;
                FileOutputStream out;
                BufferedInputStream buffIn = null;
                BufferedOutputStream buffOut = null;

                try {
                    in = new FileInputStream(srcFile);

                    buffIn = new BufferedInputStream(in);
                    File desFile = new File(desFileDer, srcFile.getName());
                    out = new FileOutputStream(desFile);
                    buffOut = new BufferedOutputStream(out);
                    byte[] b = new byte[100];
                    int i = buffIn.read(b);
                    while (i > 0) {
                        buffOut.write(b, 0, i);
                        i = buffIn.read(b);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (buffIn != null) {
                            buffIn.close();
                        }
                        if (buffOut != null) {
                            buffOut.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                List<String> filePath = new ArrayList<>();
                List<String> fileName = new ArrayList<>();
                getFilePathAndName(srcFile.getPath(), filePath, fileName);
                String desPath = destinationFilePath + "/" + srcFile.getName();
                copyFile(filePath, desPath, code);
            }

        }

        if (code.equals(CODE_CUT)) {
            deleteFile(copyPath);
        }
    }

    /**
     * 删除文件 路径下的所有文件
     *
     * @param filePathList 文件路径
     */
    public static void deleteFile(List<String> filePathList) {

        for (int i = 0; i < filePathList.size(); i++) {
            File file = new File(filePathList.get(i));
            if (file.exists()) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    List<String> filePath = new ArrayList<>();
                    List<String> fileName = new ArrayList<>();
                    getFilePathAndName(file.getPath(), filePath, fileName);
                    deleteFile(filePath);
                    file.delete();
                }
            }
        }
    }

    /**
     * 新建文件/文件夹
     *
     * @param path 路径(一定要是文件夹)
     * @param name 文件名/文件夹名
     */
    public static void createFile(String path, String name) {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            if (name.indexOf(".") > 0) {
                new File(path, name).createNewFile();
            } else {
                new File(path + "/" + name).mkdirs();
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * 修改文件名
     *
     * @param path    路径(一定要是文件夹)
     * @param oldName 旧的文件名
     * @param newName 新的文件名
     * @return 是否改名成功
     */
    public static boolean updateFileName(String path, String oldName, String newName) {

        File oldFile = new File(path + "/" + oldName);
        File newFile = new File(path + "/" + newName);

        return oldFile.renameTo(newFile);
    }

    public static long allSize = 0;

    /**
     * 获取文件 路径下的所有文件总大小
     *
     * @param filePathList 文件路径
     */
    public static void getFileAllSize(List<String> filePathList) {

        for (int i = 0; i < filePathList.size(); i++) {
            File file = new File(filePathList.get(i));
            if (!file.exists()) {
                allSize += 0;
            } else if (!file.isDirectory()) {
                allSize += file.length();
            } else {
                List<String> filePath = new ArrayList<>();
                List<String> fileName = new ArrayList<>();
                getFilePathAndName(file.getPath(), filePath, fileName);
                getFileAllSize(filePath);
            }
        }
    }

    /**
     * 格式化文件大小
     *
     * @param value
     * @return
     */
    public static String renderSize(long value) {

        if (value == 0) {
            return "0B";
        }

        String[] unitArr = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        int index = 0;
        double srcSize = value;
        index = (int) Math.floor(Math.log(srcSize) / Math.log(1024));
        double size = srcSize / Math.pow(1024, index);
        //  保留2位小数
        size = FormatUtils.doNumberFormat(size, 2);

        return size + unitArr[index];
    }

    /**
     * 检查文件是否存在，不存在则创建
     *
     * @param path String
     */
    public static void checkFileExist(String path) {

        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param path String
     * @return 返回是否存在
     */
    public static boolean checkFileExist2(String path) {
        return new File(path).exists();
    }

    /**
     * 获取路径
     *
     * @return String
     */
    public static String getSDCardPath() {

        String sdDir = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
        }

        return sdDir;
    }

    public static boolean isImageFile(String file) {

        if (TextUtils.isEmpty(file)) {
            return false;
        }

        String str = file.toLowerCase();
        return str.endsWith(".png")
                || str.endsWith(".jpg")
                || str.endsWith(".jpeg")
                || str.endsWith(".gif")
                || str.endsWith(".bmp");
    }

    public static boolean isImageFileGif(String file) {

        if (TextUtils.isEmpty(file)) {
            return false;
        }

        String str = file.toLowerCase();
        return str.endsWith(".gif");
    }

    public static boolean isImageFilePng(String file) {

        if (TextUtils.isEmpty(file)) {
            return false;
        }

        String str = file.toLowerCase();
        return str.endsWith(".png");
    }

    public static boolean isOfficeFile(String file) {

        if (TextUtils.isEmpty(file)) {
            return false;
        }

        String str = file.toLowerCase();
        return str.endsWith(".doc")
                || str.endsWith(".docx")
                || str.endsWith(".xls")
                || str.endsWith(".xlsx")
                || str.endsWith(".pdf")
                || str.endsWith(".txt")
                || str.endsWith(".ppt")
                || str.endsWith(".pptx");
    }

}
