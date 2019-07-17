package com.hg.hollowgoods.UI.Activity.Main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Adapter.Main.HGFeedbackAdapter;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.Exception.ExceptionLog;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.Util.CacheUtils;

import java.util.ArrayList;

/**
 * 反馈中心界面
 * Created by HG on 2018-06-07.
 */
public class HGFeedbackActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView result;

    private HGFeedbackAdapter adapter;
    private ArrayList<ExceptionLog> data = new ArrayList<>();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_hg_feedback;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        result = findViewById(R.id.result);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_feed_back);

        refreshLayout.setColorSchemeColors(HGSystemConfig.REFRESH_COLORS);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HGFeedbackAdapter(this, R.layout.item_hg_feedback, data);
        result.setAdapter(adapter);

        getData();

        return null;
    }

    @Override
    public void setListener() {

        refreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(this::getData, 2 * 1000L));

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }

    @Override
    public boolean haveScroll() {
        return true;
    }

    private String name = "Exception.txt";

    private void getData() {

        ArrayList<ExceptionLog> temp = CacheUtils.create().load(name, new TypeToken<ArrayList<ExceptionLog>>() {
        }.getType());

        data.clear();
        if (temp != null) {
            data.addAll(temp);
        }
        adapter.refreshData(data);

        refreshLayout.setRefreshing(false);
    }

    public void uploadException(final int position) {

        baseUI.baseDialog.showProgressDialog(R.string.reporting, HGConstants.DEFAULT_CODE);

//        val params = RequestParams(InterfaceConfig.getRequestHeadHttp() + InterfaceApi.REPORTED)
//        params.addParameter("", Gson().toJson(data[position]))
//
//        val xUtils = XUtils();
//        xUtils.setGetHttpDataListener(object : GetHttpDataListener {
//
//            override fun onGetSuccess(result: String) {
//
//            }
//
//            override fun onGetError(ex: Throwable) {
//                showShortToast(R.string.network_error)
//            }
//
//            override fun onGetLoading(total: Long, current: Long) {
//
//            }
//
//            override fun onGetFinish() {
//                baseDialog.closeDialog(Constants.DEFAULT_CODE)
//            }
//
//            override fun onGetCancel(cex: Callback.CancelledException) {
//
//            }
//        })
//        xUtils.getHttpData(HttpMethod.POST, params)

        new Handler().postDelayed(() -> {

            baseUI.baseDialog.closeDialog(HGConstants.DEFAULT_CODE);

            data.get(position).setUploadStatus(true);
            adapter.refreshData(data, position);
        }, 3 * 1000L);
    }

}
