package com.hg.hollowgoods.Bean.Example;

import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItem;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemFileMaxCount;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemNumberPicker;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItemSwitch;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListAutoNumber;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent1;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent2;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent3;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListContent4;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListDelete;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListEdit;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListFlag;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListImgUrl;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.List.FastListTitle;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.StringUtils;

/**
 * 示例28
 * Created by HG on 2018-06-13.
 */
@FastListAutoNumber()
public class Ex28 extends CommonBean {

    @FastListTitle
    private String title;

    @FastListContent1(label = "姓名")
    @FastItem(sortNumber = 1, label = "姓名", isNotEmpty = true, mode = FastItemMode.Input)
    private String name;

    @FastListContent2(label = "年龄")
    @FastItem(sortNumber = 2, label = "年龄")
    @FastItemNumberPicker(min = "0", max = "10", dif = "1", type = Integer.class)
    private int age;

    @FastListContent3(label = "性别", itemsName = "SEX")
    @FastItem(sortNumber = 3, label = "性别", itemsName = "SEX", visible = "isShowSex", mode = FastItemMode.Choose)
    private int sex;

    @FastListContent4(label = "爱好")
    @FastItem(sortNumber = 4, label = "爱好", visible = "isShowHobby")
    private String hobby;

    @FastItem(sortNumber = 5, label = "生活照", mode = FastItemMode.File, marginTop = 10)
    @FastItemFileMaxCount(maxCount = HGSystemConfig.HG_PHOTO_MAX_COUNT)
    private String photo;

    @FastItem(sortNumber = 6, label = "是否已婚", marginTop = 10, itemsName = "MARRY")
    @FastItemSwitch
    private boolean isMarry;

    @FastItem(sortNumber = 7, label = "出生日期", marginTop = 300, isDate = true, dateFormatMode = StringUtils.DateFormatMode.Chinese_YMD)
    private long birthday;

    @FastListDelete()
    @FastListEdit()
    private boolean isNeedButton;

    @FastListImgUrl
    private String url;

    @FastListFlag
    private int flag = R.drawable.ic_love;

    private boolean isShowSex = true;
    private boolean isShowHobby = true;

    public Ex28(int itemType) {
        super(itemType);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public boolean isNeedButton() {
        return isNeedButton;
    }

    public void setNeedButton(boolean needButton) {
        isNeedButton = needButton;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShowSex() {
        return isShowSex;
    }

    public void setShowSex(boolean showSex) {
        isShowSex = showSex;
    }

    public boolean isShowHobby() {
        return isShowHobby;
    }

    public void setShowHobby(boolean showHobby) {
        isShowHobby = showHobby;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isMarry() {
        return isMarry;
    }

    public void setMarry(boolean marry) {
        isMarry = marry;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

}
