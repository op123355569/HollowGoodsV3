package com.hg.hollowgoods.Constant;

/**
 * 传递数据键
 */
public enum HGParamKey {

    /**** 列表数据 ****/
    ListData("param.key.list.data"),
    /**** Obj数据 ****/
    ObjData("param.key.data"),
    /**** URL ****/
    URL("param.key.url"),
    /**** 位置 ****/
    Position("param.key.position"),
    /**** 多个位置 ****/
    Positions("param.key.positions"),
    /**** 标题 ****/
    Title("param.key.title"),
    /**** 最大数量 ****/
    MaxCount("param.key.max.count"),
    /**** 文件过滤格式 ****/
    FileFilter("param.key.file.filter"),
    /**** 已选中的文件 ****/
    SelectedFile("param.key.selected.file"),
    /**** 输入的内容 ****/
    InputValue("param.key.input.value"),
    /**** 年 ****/
    DateYear("param.key.date.year"),
    /**** 月 ****/
    DateMonth("param.key.date.month"),
    /**** 日 ****/
    DateDay("param.key.date.day"),
    /**** 时 ****/
    DateHour("param.key.date.hour"),
    /**** 分 ****/
    DateMinute("param.key.date.minute"),
    /**** 时间戳 ****/
    DateTimeInMillis("param.key.date.time.in.millis"),
    //
    ;

    private String value;

    HGParamKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
