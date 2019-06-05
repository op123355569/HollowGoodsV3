package com.hg.hollowgoods.Util.UpdateAPP;

/**
 * 版本更新控制器
 * Created by Hollow Goods on 2019-06-04.
 */
public interface UpdateAPPController {

    /**
     * 检查服务器的版本数据
     * 如需进行下一步操作，请调用{@link HGUpdateAPPUtils#showDialog(String)}
     */
    void checkServerData();

}
