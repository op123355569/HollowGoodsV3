package com.hg.hollowgoods.UI.Fragment.Plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hg.hollowgoods.Adapter.Plugin.BannerAdapter;
import com.hg.hollowgoods.Bean.Plugin.Banner;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseFragment;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.Util.DensityUtils;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 轮播图界面
 *
 * @author HG
 */

public abstract class HGBannerFragment extends BaseFragment implements BannerHelper {

    private DiscreteScrollView banner;
    private RadioGroup label;

    private InfiniteScrollAdapter infiniteScrollAdapter;
    private ArrayList<Banner> data = new ArrayList<>();
    private int[] banners;
    private int bannerPosition = 0;
    private Timer bannerTimer;
    private long bannerPlayDuration;

    @Override
    public int bindLayout() {
        return R.layout.fragment_hg_banner;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleViewVisibility(false);

        banner = baseUI.findViewById(R.id.dsv_banner);
        label = baseUI.findViewById(R.id.rg_label);

        int[] temp = defaultBannerRes();
        if (temp == null) {
            banners = new int[4];
            banners[0] = HGCommonResource.IMAGE_LOADING;
            banners[1] = HGCommonResource.IMAGE_LOADING;
            banners[2] = HGCommonResource.IMAGE_LOADING;
            banners[3] = HGCommonResource.IMAGE_LOADING;
        } else {
            banners = temp;
        }

        initBanner(null);
        doRefresh();

        return null;
    }

    @Override
    public void setListener() {

    }

    private void initBanner(ArrayList<Banner> webBanners) {

        // 方向
        banner.setOrientation(DSVOrientation.HORIZONTAL);
        // 滚动监听
        banner.addOnItemChangedListener((viewHolder, adapterPosition) -> {
            int position = infiniteScrollAdapter.getRealPosition(adapterPosition);
            onItemChanged(position);
        });

        FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) label.getLayoutParams();
        flp.topMargin = DensityUtils.dp2px(baseUI.getBaseContext(), indicatorMarginTopDp());
        flp.bottomMargin = DensityUtils.dp2px(baseUI.getBaseContext(), indicatorMarginBottomDp());
        flp.setMarginStart(DensityUtils.dp2px(baseUI.getBaseContext(), indicatorMarginStartDp()));
        flp.setMarginEnd(DensityUtils.dp2px(baseUI.getBaseContext(), indicatorMarginEndDp()));
        flp.gravity = indicatorGravity();
        label.setLayoutParams(flp);

        Banner item;
        RadioButton radioButton;
        RadioGroup.LayoutParams rlp = new RadioGroup.LayoutParams(25, 25);
        rlp.setMarginStart(8);
        rlp.setMarginEnd(8);

        data.clear();
        label.removeAllViews();

        if (webBanners == null) {
            for (int t : banners) {
                item = new Banner();
                item.setRes(t);
                data.add(item);

                radioButton = new RadioButton(baseUI.getBaseContext());
                radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                radioButton.setLayoutParams(rlp);
                radioButton.setBackgroundResource(R.drawable.selector_banner_label);
                label.addView(radioButton);
            }

            if (banners.length < 2) {
                label.setVisibility(View.GONE);
            }
        } else {
            for (Banner t : webBanners) {
                data.add(t);

                radioButton = new RadioButton(baseUI.getBaseContext());
                radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                radioButton.setLayoutParams(rlp);
                radioButton.setBackgroundResource(R.drawable.selector_banner_label);
                label.addView(radioButton);
            }

            if (webBanners.size() < 2) {
                label.setVisibility(View.GONE);
            }
        }

        // 创建普通适配器
        BannerAdapter adapter = new BannerAdapter(baseUI.getBaseContext(), bindBannerItem(), data);
        // 生成无限滚动适配器
        infiniteScrollAdapter = InfiniteScrollAdapter.wrap(adapter);
        // 把无限滚动适配器放入普通适配器，为了获取真实的Position
        adapter.setInfiniteAdapter(infiniteScrollAdapter);
        // 填充适配器
        banner.setAdapter(infiniteScrollAdapter);

        // 设置滚动动画时间
        banner.setItemTransitionTimeMillis(200);
        banner.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(minScale())
                .build());

        onItemChanged(bannerPosition);

        adapter.setOnBannerClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                onBannerClick(view, position);
            }
        });

        bannerPlayDuration = bannerPlayDuration();
    }

    private void onItemChanged(int position) {
        bannerPosition = position;
        ((RadioButton) label.getChildAt(bannerPosition)).setChecked(true);
    }

    private void startBanner() {

        if (bannerTimer != null) {
            bannerTimer.cancel();
            bannerTimer = null;
        }

        bannerTimer = new Timer();
        bannerTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                bannerPosition++;

                if (bannerPosition >= data.size()) {
                    bannerPosition = 0;
                }

                baseUI.getBaseContext().runOnUiThread(() -> {
                    int position = infiniteScrollAdapter.getClosestPosition(bannerPosition);
                    banner.smoothScrollToPosition(position);
                });
            }
        }, bannerPlayDuration, bannerPlayDuration);
    }

    private void stopBanner() {
        if (bannerTimer != null) {
            bannerTimer.cancel();
            bannerTimer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopBanner();
    }

    @Override
    public void onDestroy() {
        stopBanner();
        super.onDestroy();
    }

    private void doRefresh() {
        getData();
    }

    public void refreshData(ArrayList<Banner> tempData) {
        baseUI.getBaseContext().runOnUiThread(() -> {
            if (tempData != null && tempData.size() > 0) {
                bannerPosition = 0;
                initBanner(tempData);
            }
        });
    }

}
