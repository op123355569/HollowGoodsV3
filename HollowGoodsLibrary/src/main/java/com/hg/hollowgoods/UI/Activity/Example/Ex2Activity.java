package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hg.hollowgoods.Adapter.Example.Ex2Adapter;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnAdapterViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.SystemAppUtils.SystemAppUtils;
import com.hg.hollowgoods.Util.XUtils.DownloadListener;
import com.hg.hollowgoods.Util.XUtils.LoadImageOptions;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;

/**
 * XUtils3.0示例界面
 * Created by HG
 */
public class Ex2Activity extends BaseActivity {

    private final int PERMISSION_CODE_INSTALL = 1000;

    private ImageView head;
    private GridView imgs;
    private ProgressBar bar;
    private TextView pro;

    private ArrayList<String> data;
    private SystemAppUtils systemAppUtils = new SystemAppUtils();

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_2;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex2);

        head = findViewById(R.id.iv_headImg);
        imgs = findViewById(R.id.gv_result);
        bar = findViewById(R.id.pb_downloadProgress);
        pro = findViewById(R.id.tv_progress);

        loadHeadImg();
        initUrl();

        Ex2Adapter adapter = new Ex2Adapter(this, data);
        imgs.setAdapter(adapter);

        pro.setText("0%");
        bar.setProgress(0);
        bar.setMax(100);

        return null;
    }

    @Override
    public void setListener() {

        head.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                if (systemAppUtils.canInstallAPK(baseUI.getBaseContext())) {
                    downloadAPP();
                } else {
                    systemAppUtils.requestInstallPermission(baseUI.getBaseContext(), PERMISSION_CODE_INSTALL);
                }
            }
        });
        imgs.setOnItemClickListener(new OnAdapterViewItemClickListener(false) {
            @Override
            public void onAdapterViewItemClick(AdapterView<?> parentView, View view, int position, long id) {
//                startMyActivity(ShowImageActivity.class, new String[]{Constants.PARAM_KEY_1}, new Object[]{data.get(position)});
                systemAppUtils.previewPhotos(Ex2Activity.this, data, position);
            }
        });
    }

    private void loadHeadImg() {

        XUtils xUtils = new XUtils();
        String headUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507874689650&di=e70be488a0c65b36090d7a9d6c51a052&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201509%2F02%2F20150902104405_PRM4E.jpeg";
        LoadImageOptions options = new LoadImageOptions(head, headUrl, HGCommonResource.NO_IMAGE_HEAD,
                HGCommonResource.NO_IMAGE_HEAD, 80, 80, 0, true, ScaleType.CENTER_CROP);
        xUtils.loadImageByUrl(options);
    }

    private void initUrl() {

        data = new ArrayList<>();
        data.add("http://img5.duitang.com/uploads/item/201408/12/20140812133247_zcLCB.jpeg");
        data.add("http://img1.gamersky.com/image2016/04/20160421_lc_40_5/gamersky_04small_08_20164211311765.jpg");
        data.add("http://s.qdcdn.com/cl/10008521,800,450.jpg");
        data.add("http://d.hiphotos.baidu.com/zhidao/pic/item/d01373f082025aaf3f871852f9edab64024f1af5.jpg");
        data.add("http://img1.gamersky.com/image2016/04/20160428_zcq_252_1/gamersky_02small_04_20164281738B65.jpg");
        data.add("http://pic0.qiyipic.com/image/20160224/d2/b5/a_100012835_m_601_m2_480_270.jpg");
        data.add("http://pic.baike.soso.com/p/20100209/20100209194645-507652227.jpg");
        data.add("http://imgsrc.baidu.com/forum/pic/item/a6efce1b9d16fdfa3c950faeb48f8c5495ee7bf1.jpg");
        data.add("http://f.hiphotos.baidu.com/zhidao/pic/item/c2cec3fdfc0392458b23a4af8794a4c27c1e25ff.jpg");
        data.add("http://tupian.enterdesk.com/2013/xll/008/06/8/12.jpg");
        data.add("http://qn.18touch.com/uploads/20150529/v1_1-8_1432895095365102.jpg");
        data.add("http://img0.pcgames.com.cn/pcgames/1104/23/2193236_dmxinwen2011042214.jpg");
        data.add("http://news.u17i.com/comic_wallpaper/3166/comic_3166_1403147673_2560x1600.jpg");
        data.add("http://www.shouyoutv.com/images/20150109/1420797844552452.jpg");
        data.add("http://news.u17i.com/comic_wallpaper/3166/comic_3166_1403147632_2560x1600.jpg");
        data.add("http://img.zcool.cn/community/01d102576c77e90000018c1b72d82e.jpg");
        data.add("http://i1.hdslb.com/video/f9/f9a4348fdb86f3f677ef7b02df67c8e0.jpg");
        data.add("http://ww1.sinaimg.cn/large/005FDPhtgw1eyc2lyz7cbj30fk078417.jpg");
        data.add("http://i2.hunantv.com/p1/20151015/1508381359.jpg");
        data.add("http://life.southmoney.com/yule/UploadFiles_8402/201508/2015081716034099.png");
        data.add("http://imgsrc.baidu.com/forum/w%3D580%3Bcp%3Dtieba%2C10%2C348%3Bap%3D%D3%C9%C0%F2%BD%B4%B0%C9%2C90%2C356/sign=0e6e43ba8501a18bf0eb1247ae146478/9a27931373f08202df1554a949fbfbedaa641b09.jpg");
        data.add("http://i3.letvimg.com/cms/201404/11/7fefcb4a376e49b3980bcb9d42e2c794.jpg");
        data.add("http://e.hiphotos.baidu.com/zhidao/pic/item/377adab44aed2e73f7a9275e8701a18b87d6fa04.jpg");
        data.add("http://www.xwqnews.com/uploads/allimg/150913/114359CH-0.jpg");
    }

    private void downloadAPP() {

        String url = "http://p.gdown.baidu.com/666fbdc01cbd4c539fa0e1f84defdb38d267ab5901ffca31f5bb683ac15c750d788b35349aa35d3b37c2a9d36ed7e4143324ce74b92e4f39c90c336f6d4499d5686b332fc710d6e6b3cfb168d46fa54b4355c6c6a5e50fd1503061470aedd53de6b8c93a34ae13fca356dec57e1700b77e4df5b356111296b36456e3e92e24f223ccfe7902473a5920c8f057382287d018224c0795104c43e1dcff87eb09615f2565b68e59c80ff15ad1d0c2af7daeec20fa0b2e0b22854b13809f8c37646b6bc1a8e7ae5cc41534da8c53450b556ad68bc028dd3eb6994189490957fbc72ad7d314e8e235cc2fa712681e5d7349b9712e4913c71d38289ff2bd4901ceec100f4eb6e337c84f2861b69fe628fc93c929";
        XUtils xUtils = new XUtils();
        xUtils.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                pro.setText("100%");
                bar.setProgress(100);

                t.showLongToast(file.getAbsolutePath());
                filepath = file.getAbsolutePath();
                installAPK();
            }

            @Override
            public void onDownloadError(Throwable ex) {

            }

            @Override
            public void onDownloadLoading(long total, long current) {
                int progress = (int) (current * 1F / total * 100);
                pro.setText(progress + "%");
                bar.setProgress(progress);
            }

            @Override
            public void onDownloadFinish() {

            }

            @Override
            public void onDownloadCancel(Callback.CancelledException cex) {

            }
        });
        RequestParams params = new RequestParams(url);
        xUtils.downloadFile(HttpMethod.GET, params);
    }

    private String filepath;

    private void installAPK() {
        systemAppUtils.installAPK(baseUI.getBaseContext(), filepath);
    }

}
