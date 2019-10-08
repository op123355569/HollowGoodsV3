package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.TextViewUtils;

/**
 * 多样化文字示例
 * Created by Hollow Goods 2018-03-21.
 */

public class Ex27Activity extends BaseActivity {

    private TextView text;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_27;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        text = findViewById(R.id.tv_text);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex27);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                t.info(R.string.tips_best);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };


        // 响应点击事件的话必须设置以下属性
        text.setMovementMethod(LinkMovementMethod.getInstance());

        TextViewUtils.getBuilder(this,"").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试").append("Url\n").setUrl("http://www.baidu.com")
                .append("列表项\n").setBullet(60, getResources().getColor(R.color.google_blue))
                .append("  测试引用\n").setQuoteColor(getResources().getColor(R.color.google_blue))
                .append("首行缩进\n").setLeadingMargin(30, 50)
                .append("测试").append("上标").setSuperscript().append("下标\n").setSubscript()
                .append("测试").append("点击事件\n").setClickSpan(clickableSpan)
                .append("测试").append("serif 字体\n").setFontFamily("serif")
                .append("测试").append("图片\n").setResourceId(R.drawable.ic_love)
                .append("测试").append("前景色").setForegroundColor(Color.GREEN).append("背景色\n").setBackgroundColor(getResources().getColor(R.color.google_blue))
                .append("测试").append("删除线").setStrikethrough().append("下划线\n").setUnderline()
                .append("测试").append("sans-serif 字体\n").setFontFamily("sans-serif")
                .append("测试").append("2倍字体\n").setProportion(2)
                .append("测试").append("monospace字体\n").setFontFamily("monospace")
                .append("测试").append("普通模糊效果字体\n").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .append("测试").append(" 粗体 ").setBold().append(" 斜体 ").setItalic().append(" 粗斜体 \n").setBoldItalic()
                .append("测试").append("横向2倍字体\n").setXProportion(2)
                .append("\n测试正常对齐\n").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试居中对齐\n").setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试相反对齐\n").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .into(text);

        return null;
    }

    @Override
    public void setListener() {

    }

}
