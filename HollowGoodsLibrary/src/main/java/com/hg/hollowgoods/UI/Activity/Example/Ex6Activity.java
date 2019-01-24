package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.Example.Ex6Adapter;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnAdapterViewItemClickListener;
import com.hg.hollowgoods.Util.Glide.GlideCircleTransform;
import com.hg.hollowgoods.Util.Glide.GlideOptions;
import com.hg.hollowgoods.Util.Glide.GlideUtils;
import com.hg.hollowgoods.Util.SystemAppUtils;

import java.util.ArrayList;

/**
 * Glide库加载图片示例界面
 * Created by HG
 */

public class Ex6Activity extends BaseActivity {

    private ImageView testImg;
    private GridView imgs;

    private Ex6Adapter adapter;
    private ArrayList<String> data = new ArrayList<String>() {
        {
            add("http://img1.imgtn.bdimg.com/it/u=2887586268,2669956094&fm=27&gp=0.jpg");
            add("http://pic2.qnpic.com/doimg/shilitaosheng/ff409a72/");
            add("http://wwe.nubb.com/img/200811/1226371365-12200811118D_13.jpg");
            add("http://imgsrc.baidu.com/forum/w%3D580/sign=958c42576d81800a6ee58906813733d6/c5debd3eb13533fac65f28f6a9d3fd1f40345b07.jpg");
            add("http://img3.imgtn.bdimg.com/it/u=2699475896,2584618768&fm=27&gp=0.jpg");
            add("http://img1.imgtn.bdimg.com/it/u=1345661069,942995207&fm=27&gp=0.jpg");
            add("http://tv02.tgbusdata.cn/v2/thumb/jpg/ODAwRiw1MDAsMTAwLDQsMywxLC0xLDAscms1MA==/u/ol.tgbus.com/news/UploadFiles_2374/201410/20141023111615521.jpg");
            add("http://wwe.nubb.com/img/200811/1226371365-13200811118D_14.jpg");
            add("http://img1.replays.net/www.replays.net/uploads/body/2016/04/1461320323Sb2.jpg");
            add("http://wwe.nubb.com/img/200811/1226371365-8200811118D_09.jpg");
            add("http://imagscdn.3234.com/attaches/23/11/60643-0JaAYt.jpg");
            add("http://i-3.yxdown.com/2016/3/7/2dd085fe-cf3a-452a-a6ec-fe87dba732e9.jpg");
            add("http://img2.imgtn.bdimg.com/it/u=2819221633,1307577241&fm=21&gp=0.jpg");
            add("http://pic.66wz.com/0/00/35/41/354172_159507.jpg");
            add("http://img1.cache.netease.com/catchpic/D/DC/DC1BB4EE2B644059075F46843F75DFDA.jpg");
            add("http://n.sinaimg.cn/transform/20150417/s9Jf-awzuney3687386.jpg");
            add("https://ss2.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3828998,3027425220&fm=23&gp=0.jpg");
            add("http://img1.3lian.com/2015/a1/114/d/233.jpg");
            add("http://attach.s8bbs.com/attachments/Mon_1510/434_23278034_e690b81f9dc9101.jpg");
            add("http://www.hinews.cn/pic/0/14/00/58/14005879_707981.jpg");
            add("http://img2.imgtn.bdimg.com/it/u=1267217087,183804710&fm=214&gp=0.jpg");
            add("http://image.tianjimedia.com/uploadImages/2014/349/27/UAERX58T2GP9.png");
            add("http://himg2.huanqiu.com/attachment2010/2013/0720/20130720123504893.jpg");
            add("http://cdn0.hbimg.cn/store/snsthumbs/650_2000/album/201113/817FDCB8EFAD9BB083F7CDC74BAA84F0CA8320F3FF.jpg");
            add("http://img3.3lian.com/2006/004/19/009.jpg");
            add("http://img1.3lian.com/img013/v5/12/d/43.jpg");
            add("http://i6.hexunimg.cn/2012-01-05/136958740.jpg");
            add("http://new-img2.ol-img.com/985x695/79/72/liH9h6IqnX5c.jpg");
            add("http://img2.ph.126.net/oT-R2LApLsKEu5n0Muk_Jg==/3782460737138012306.jpg");
            add("http://img8.ph.126.net/8_MXhmmxN3nI9a6i0enetA==/2697374701835897238.jpg");
            add("http://t2.qpic.cn/mblogpic/b4a5b9478462b56d8c46/460");
            add("http://img.biud.com.cn/upload/news/2014-05-12/photos/middle/18suiweixiaohu37273420140512054328135084838100.jpg");
            add("http://img.biud.com.cn/upload/news/2014-05-12/photos/middle/18suiweixiaohu37273420140512054332993014280500.jpg");
            add("http://img3.imgtn.bdimg.com/it/u=1990540738,1942329741&fm=214&gp=0.jpg");
            add("http://thumb.takefoto.cn/wp-content/uploads/2015/11/201510312335246010-600x463.jpg");
            add("http://pic.yesky.com/uploadImages/2015/145/24/77I7773O0WGB.jpg");
        }
    };
    private SystemAppUtils systemAppUtils = new SystemAppUtils();

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_6;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        testImg = findViewById(R.id.iv_testImg);
        imgs = findViewById(R.id.gv_result);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex6);

        String url = "http://img31.mtime.cn/CMS/Gallery/2013/05/21/104932.72048947_900.jpg";
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(CommonResource.IMAGE_LOADING)
                .error(CommonResource.IMAGE_LOAD_ERROR)
                .transform(new GlideCircleTransform());
        GlideOptions glideOptions = new GlideOptions(url, testImg, GlideOptions.NORMAL_FADE_IN, options);

        GlideUtils.loadImg(this, glideOptions);

        adapter = new Ex6Adapter(this, R.layout.item_ex_6, data);
        imgs.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {
        imgs.setOnItemClickListener(new OnAdapterViewItemClickListener(false) {
            @Override
            public void onAdapterViewItemClick(AdapterView<?> parentView, View view, int position, long id) {
//                startMyActivity(ShowImageActivity.class, new String[]{Constants.PARAM_KEY_1}, new Object[]{data.get(position)});
                systemAppUtils.previewPhotos(Ex6Activity.this, data.get(position));
            }
        });
    }

}
