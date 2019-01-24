package com.hg.hollowgoods.Widget.BugView;

import android.content.Context;

import com.hg.hollowgoods.Util.AssetsUtils;
import com.hg.hollowgoods.Util.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HG on 2017-06-02.
 */

public class BugOtherHtml {

    public static String getCSS() {

        StringBuilder str = new StringBuilder("");

        str.append("<style>");

        str.append("body {\n" +
                "    width: 1000px;\n" +
                "    margin: 40px auto;\n" +
                "    font-family: 'trebuchet MS', 'Lucida sans', Arial;\n" +
                "    font-size: 14px;\n" +
                "    color: #444;\n" +
                "}");
        str.append("table {\n" +
                "    *border-collapse: collapse; /* IE7 and lower */\n" +
                "    border-spacing: 0;\n" +
                "    width: 100%;    \n" +
                "    margin-bottom: 40px;"+
                "}");
        str.append(".bordered {\n" +
                "    border: solid #ccc 1px;\n" +
                "    -moz-border-radius: 6px;\n" +
                "    -webkit-border-radius: 6px;\n" +
                "    border-radius: 6px;\n" +
                "    -webkit-box-shadow: 0 1px 1px #ccc; \n" +
                "    -moz-box-shadow: 0 1px 1px #ccc; \n" +
                "    box-shadow: 0 1px 1px #ccc;         \n" +
                "}");
        str.append(".bordered tr:hover {\n" +
                "    background: #fbf8e9;\n" +
                "    -o-transition: all 0.1s ease-in-out;\n" +
                "    -webkit-transition: all 0.1s ease-in-out;\n" +
                "    -moz-transition: all 0.1s ease-in-out;\n" +
                "    -ms-transition: all 0.1s ease-in-out;\n" +
                "    transition: all 0.1s ease-in-out;     \n" +
                "}    ");
        str.append(".bordered td, .bordered th {\n" +
                "    border-left: 1px solid #ccc;\n" +
                "    border-top: 1px solid #ccc;\n" +
                "    padding: 10px;\n" +
                "    text-align: left;    \n" +
                "}");
        str.append(".bordered th {\n" +
                "    background-color: #dce9f9;\n" +
                "    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));\n" +
                "    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);\n" +
                "    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);\n" +
                "    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);\n" +
                "    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);\n" +
                "    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);\n" +
                "    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; \n" +
                "    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  \n" +
                "    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        \n" +
                "    border-top: none;\n" +
                "    text-shadow: 0 1px 0 rgba(255,255,255,.5); \n" +
                "}");
        str.append(".bordered td:first-child, .bordered th:first-child {\n" +
                "    border-left: none;\n" +
                "}");
        str.append(".bordered th:first-child {\n" +
                "    -moz-border-radius: 6px 0 0 0;\n" +
                "    -webkit-border-radius: 6px 0 0 0;\n" +
                "    border-radius: 6px 0 0 0;\n" +
                "}");
        str.append(".bordered th:last-child {\n" +
                "    -moz-border-radius: 0 6px 0 0;\n" +
                "    -webkit-border-radius: 0 6px 0 0;\n" +
                "    border-radius: 0 6px 0 0;\n" +
                "}");
        str.append(".bordered th:only-child{\n" +
                "    -moz-border-radius: 6px 6px 0 0;\n" +
                "    -webkit-border-radius: 6px 6px 0 0;\n" +
                "    border-radius: 6px 6px 0 0;\n" +
                "}");
        str.append(".bordered tr:last-child td:first-child {\n" +
                "    -moz-border-radius: 0 0 0 6px;\n" +
                "    -webkit-border-radius: 0 0 0 6px;\n" +
                "    border-radius: 0 0 0 6px;\n" +
                "}");
        str.append(".bordered tr:last-child td:last-child {\n" +
                "    -moz-border-radius: 0 0 6px 0;\n" +
                "    -webkit-border-radius: 0 0 6px 0;\n" +
                "    border-radius: 0 0 6px 0;\n" +
                "}");
        str.append(".tgl {\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        .tgl, .tgl:after, .tgl:before, .tgl *, .tgl *:after, .tgl *:before, .tgl + .tgl-btn {\n" +
                "            -webkit-box-sizing: border-box;\n" +
                "            -moz-box-sizing: border-box;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        .tgl::-moz-selection, .tgl:after::-moz-selection, .tgl:before::-moz-selection, .tgl *::-moz-selection, .tgl\n" +
                "        *:after::-moz-selection, .tgl *:before::-moz-selection, .tgl + .tgl-btn::-moz-selection {\n" +
                "            background: none;\n" +
                "        }\n" +
                "\n" +
                "        .tgl::selection, .tgl:after::selection, .tgl:before::selection, .tgl *::selection, .tgl *:after::selection, .tgl\n" +
                "        *:before::selection, .tgl + .tgl-btn::selection {\n" +
                "            background: none;\n" +
                "        }\n" +
                "\n" +
                "        .tgl + .tgl-btn {\n" +
                "            outline: 0;\n" +
                "            display: block;\n" +
                "            width: 4em;\n" +
                "            height: 2em;\n" +
                "            position: relative;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "\n" +
                "        .tgl + .tgl-btn:after, .tgl + .tgl-btn:before {\n" +
                "            position: relative;\n" +
                "            display: block;\n" +
                "            content: \"\";\n" +
                "            width: 50%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .tgl + .tgl-btn:after {\n" +
                "            left: 0;\n" +
                "        }\n" +
                "\n" +
                "        .tgl + .tgl-btn:before {\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        .tgl:checked + .tgl-btn:after {\n" +
                "            left: 50%;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-light + .tgl-btn {\n" +
                "            background: #f0f0f0;\n" +
                "            border-radius: 2em;\n" +
                "            padding: 2px;\n" +
                "            -webkit-transition: all .4s ease;\n" +
                "            transition: all .4s ease;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-light + .tgl-btn:after {\n" +
                "            border-radius: 50%;\n" +
                "            background: #fff;\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-light:checked + .tgl-btn {\n" +
                "            background: #9FD6AE;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios + .tgl-btn {\n" +
                "            background: #fbfbfb;\n" +
                "            border-radius: 2em;\n" +
                "            padding: 2px;\n" +
                "            -webkit-transition: all .4s ease;\n" +
                "            transition: all .4s ease;\n" +
                "            border: 1px solid #e8eae9;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios + .tgl-btn:after {\n" +
                "            border-radius: 2em;\n" +
                "            background: #fbfbfb;\n" +
                "            -webkit-transition: left 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275), padding 0.3s ease, margin 0.3s ease;\n" +
                "            transition: left 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275), padding 0.3s ease, margin 0.3s ease;\n" +
                "            -webkit-box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1), 0 4px 0 rgba(0, 0, 0, 0.08);\n" +
                "            box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1), 0 4px 0 rgba(0, 0, 0, 0.08);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios + .tgl-btn:active {\n" +
                "            -webkit-box-shadow: inset 0 0 0 2em #e8eae9;\n" +
                "            box-shadow: inset 0 0 0 2em #e8eae9;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios + .tgl-btn:active:after {\n" +
                "            padding-right: .8em;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios:checked + .tgl-btn {\n" +
                "            background: #86d993;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios:checked + .tgl-btn:active {\n" +
                "            -webkit-box-shadow: none;\n" +
                "            box-shadow: none;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-ios:checked + .tgl-btn:active:after {\n" +
                "            margin-left: -.8em;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn {\n" +
                "            overflow: hidden;\n" +
                "            -webkit-transform: skew(-10deg);\n" +
                "            -ms-transform: skew(-10deg);\n" +
                "            transform: skew(-10deg);\n" +
                "            -webkit-backface-visibility: hidden;\n" +
                "            -ms-backface-visibility: hidden;\n" +
                "            backface-visibility: hidden;\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "            font-family: sans-serif;\n" +
                "            background: #888;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn:after, .tgl-skewed + .tgl-btn:before {\n" +
                "            -webkit-transform: skew(10deg);\n" +
                "            -ms-transform: skew(10deg);\n" +
                "            transform: skew(10deg);\n" +
                "            display: inline-block;\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "            position: absolute;\n" +
                "            line-height: 2em;\n" +
                "            font-weight: bold;\n" +
                "            color: #fff;\n" +
                "            text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn:after {\n" +
                "            left: 100%;\n" +
                "            content: attr(data-tg-on);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn:before {\n" +
                "            left: 0;\n" +
                "            content: attr(data-tg-off);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn:active {\n" +
                "            background: #888;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed + .tgl-btn:active:before {\n" +
                "            left: -10%;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed:checked + .tgl-btn {\n" +
                "            background: #86d993;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed:checked + .tgl-btn:before {\n" +
                "            left: -100%;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed:checked + .tgl-btn:after {\n" +
                "            left: 0;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-skewed:checked + .tgl-btn:active:after {\n" +
                "            left: 10%;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flat + .tgl-btn {\n" +
                "            padding: 2px;\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "            background: #fff;\n" +
                "            border: 4px solid #f2f2f2;\n" +
                "            border-radius: 2em;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flat + .tgl-btn:after {\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "            background: #f2f2f2;\n" +
                "            content: \"\";\n" +
                "            border-radius: 1em;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flat:checked + .tgl-btn {\n" +
                "            border: 4px solid #7FC6A6;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flat:checked + .tgl-btn:after {\n" +
                "            left: 50%;\n" +
                "            background: #7FC6A6;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip + .tgl-btn {\n" +
                "            padding: 2px;\n" +
                "            -webkit-transition: all .2s ease;\n" +
                "            transition: all .2s ease;\n" +
                "            font-family: sans-serif;\n" +
                "            -webkit-perspective: 100px;\n" +
                "            -ms-perspective: 100px;\n" +
                "            perspective: 100px;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip + .tgl-btn:after, .tgl-flip + .tgl-btn:before {\n" +
                "            display: inline-block;\n" +
                "            -webkit-transition: all .4s ease;\n" +
                "            transition: all .4s ease;\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "            position: absolute;\n" +
                "            line-height: 2em;\n" +
                "            font-weight: bold;\n" +
                "            color: #fff;\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            left: 0;\n" +
                "            -webkit-backface-visibility: hidden;\n" +
                "            -ms-backface-visibility: hidden;\n" +
                "            backface-visibility: hidden;\n" +
                "            border-radius: 4px;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip + .tgl-btn:after {\n" +
                "            content: attr(data-tg-on);\n" +
                "            background: #02C66F;\n" +
                "            -webkit-transform: rotateY(-180deg);\n" +
                "            -ms-transform: rotateY(-180deg);\n" +
                "            transform: rotateY(-180deg);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip + .tgl-btn:before {\n" +
                "            background: #FF3A19;\n" +
                "            content: attr(data-tg-off);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip + .tgl-btn:active:before {\n" +
                "            -webkit-transform: rotateY(-20deg);\n" +
                "            -ms-transform: rotateY(-20deg);\n" +
                "            transform: rotateY(-20deg);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip:checked + .tgl-btn:before {\n" +
                "            -webkit-transform: rotateY(180deg);\n" +
                "            -ms-transform: rotateY(180deg);\n" +
                "            transform: rotateY(180deg);\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip:checked + .tgl-btn:after {\n" +
                "            -webkit-transform: rotateY(0);\n" +
                "            -ms-transform: rotateY(0);\n" +
                "            transform: rotateY(0);\n" +
                "            left: 0;\n" +
                "            background: #7FC6A6;\n" +
                "        }\n" +
                "\n" +
                "        .tgl-flip:checked + .tgl-btn:active:after {\n" +
                "            -webkit-transform: rotateY(20deg);\n" +
                "            -ms-transform: rotateY(20deg);\n" +
                "            transform: rotateY(20deg);\n" +
                "        }");

        str.append(".button {\n" +
                "            width: 140px;\n" +
                "            line-height: 38px;\n" +
                "            text-align: center;\n" +
                "            font-weight: bold;\n" +
                "            color: #fff;\n" +
                "            text-shadow: 1px 1px 1px #333;\n" +
                "            border-radius: 5px;\n" +
                "            margin: 0 20px 20px 0;\n" +
                "            position: relative;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .button:nth-child(6n) {\n" +
                "            margin-right: 0;\n" +
                "        }\n" +
                "\n" +
                "        .button.gray {\n" +
                "            color: #8c96a0;\n" +
                "            text-shadow: 1px 1px 1px #fff;\n" +
                "            border: 1px solid #dce1e6;\n" +
                "            box-shadow: 0 1px 2px #fff inset, 0 -1px 0 #a8abae inset;\n" +
                "            background: -webkit-linear-gradient(top, #f2f3f7, #e4e8ec);\n" +
                "            background: -moz-linear-gradient(top, #f2f3f7, #e4e8ec);\n" +
                "            background: linear-gradient(top, #f2f3f7, #e4e8ec);\n" +
                "        }\n" +
                "\n" +
                "        .gray:hover {\n" +
                "            background: -webkit-linear-gradient(top, #fefefe, #ebeced);\n" +
                "            background: -moz-linear-gradient(top, #f2f3f7, #ebeced);\n" +
                "            background: linear-gradient(top, #f2f3f7, #ebeced);\n" +
                "        }\n" +
                "\n" +
                "        .gray:active {\n" +
                "            top: 1px;\n" +
                "            box-shadow: 0 1px 3px #a8abae inset, 0 3px 0 #fff;\n" +
                "            background: -webkit-linear-gradient(top, #e4e8ec, #e4e8ec);\n" +
                "            background: -moz-linear-gradient(top, #e4e8ec, #e4e8ec);\n" +
                "            background: linear-gradient(top, #e4e8ec, #e4e8ec);\n" +
                "        }");

        str.append("</style>");

        return str.toString();
    }

    public static String getScript(String filename) {

        StringBuilder builder = new StringBuilder("");

        builder.append("<script type=\"text/javascript\">\n" +
                "\n" +
                "        var bugData;\n" +
                "        var intervalId;\n" +
                "        var localData = getLocalData(\"" + filename + "\");\n" +
                "\n" +
                "        if (localData == null || localData == \"\") {\n" +
                "            intervalId = window.setInterval(function () {\n" +
                "                var table = document.getElementById('myTable');\n" +
                "                if (table != null) {\n" +
                "                    window.clearInterval(intervalId);\n" +
                "                    var count = table.rows.length - 1;\n" +
                "                    var temp = \"[\";\n" +
                "                    for (var i = 0; i < count; i++) {\n" +
                "                        temp = temp + \"{\\\"isRepair\\\":false}\";\n" +
                "                        if (i != count - 1) {\n" +
                "                            temp = temp + \",\";\n" +
                "                        }\n" +
                "                    }\n" +
                "                    temp = temp + \"]\";\n" +
                "                    bugData = JSON.parse(temp);\n" +
                "\n" +
                "                    setLocalData(\"" + filename + "\", temp);\n" +
                "                }\n" +
                "            }, 100);\n" +
                "        } else {\n" +
                "            bugData = JSON.parse(localData);\n" +
                "            initTable();\n" +
                "        }\n" +
                "\n" +
                "        function initTable() {\n" +
                "\n" +
                "            intervalId = window.setInterval(function () {\n" +
                "                var table = document.getElementById('myTable');\n" +
                "                if (table != null) {\n" +
                "                    window.clearInterval(intervalId);\n" +
                "\n" +
                "                    var cells;\n" +
                "                    var checkBox;\n" +
                "                    for (var i = 1; i < table.rows.length; i++) {\n" +
                "                        cells = table.rows[i].cells;\n" +
                "                        checkBox = cells[4].children[0];\n" +
                "                        checkBox.checked = bugData[i - 1].isRepair;\n" +
                "                    }\n" +
                "                }\n" +
                "            }, 100);\n" +
                "        }\n" +
                "\n" +
                "        function setLocalData(key, content) {\n" +
                "            localStorage.setItem(key, content);\n" +
                "        }\n" +
                "\n" +
                "        function getLocalData(key) {\n" +
                "            return localStorage.getItem(key);\n" +
                "        }\n" +
                "\n" +
                "        function alterStatus(position) {\n" +
                "            bugData[position].isRepair = !bugData[position].isRepair;\n" +
                "            setLocalData(\"" + filename + "\", JSON.stringify(bugData));\n" +
                "        }\n" +
                "\n" +
                "");

        builder.append("function clearLocalData() {\n" +
                "\n" +
                "            localStorage.removeItem(\"" + filename + "\");\n" +
                "\n" +
                "            var table = document.getElementById('myTable');\n" +
                "            if (table != null) {\n" +
                "                var cells;\n" +
                "                var checkBox;\n" +
                "                for (var i = 1; i < table.rows.length; i++) {\n" +
                "                    cells = table.rows[i].cells;\n" +
                "                    checkBox = cells[4].children[0];\n" +
                "                    bugData[i - 1].isRepair = false;\n" +
                "                    checkBox.checked = bugData[i - 1].isRepair;\n" +
                "                }\n" +
                "            }\n" +
                "        }");

        builder.append("</script>");

        builder.append("<link href=\"css/YLlightbox.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <script src=\"js/jquery-1.11.2.min.js\"></script>\n" +
                "    <script src=\"js/YLlightbox.js\"></script>");

        return builder.toString();
    }

    public static void checkFile(final Context context, String path, String name) {
        if (!FileUtils.checkFileExist2(path + name)) {
            saveFile(path, name, AssetsUtils.getInputStream("Bug/" + name, context));
        }
    }

    private static void saveFile(String path, String name, InputStream in) {

        FileUtils.checkFileExist(path);
        copyFile(in, path, name);
    }

    /**
     * 复制文件
     *
     * @param in                  需要保存的文件的流
     * @param destinationFilePath 目标路径
     * @param saveName            保存名
     */
    public static void copyFile(InputStream in, String destinationFilePath, String saveName) {

        File desFileDer = new File(destinationFilePath);

        if (!desFileDer.exists()) {
            desFileDer.mkdirs();
        }

        FileOutputStream out = null;
        BufferedInputStream buffIn = null;
        BufferedOutputStream buffOut = null;
        try {

            buffIn = new BufferedInputStream(in);
            File desFile = new File(desFileDer, saveName);
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
                buffIn.close();
                buffOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
