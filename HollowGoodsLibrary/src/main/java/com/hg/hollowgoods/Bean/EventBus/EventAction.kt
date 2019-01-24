package com.hg.hollowgoods.Bean.EventBus

/**
 * 意图
 * Created by HG on 2018-03-22.
 */
enum class EventAction {

    /**
     * 示例意图
     */
    ActionExample,
    /**
     * NFC扫描结果
     */
    NFCScanResult,
    /**
     * Tag扫描结果
     */
    TagScanResult,
    /**
     * 删除图片
     */
    RemoveImage,
    /**
     * 文件选择
     */
    FileSelector,

}