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
import com.hg.hollowgoods.Adapter.Main.FeedbackAdapter;
import com.hg.hollowgoods.Constant.Constants;
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
public class FeedbackActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView result;

    private FeedbackAdapter adapter;
    private ArrayList<ExceptionLog> data = new ArrayList<>();

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        result = findViewById(R.id.result);

        baseUI.setCommonTitleStyleAutoBackground(R.drawable.ic_arrow_back_white_24dp, R.string.title_activity_feed_back);

        refreshLayout.setColorSchemeColors(HGSystemConfig.REFRESH_COLORS);

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FeedbackAdapter(this, R.layout.item_feedback, data);
        result.setAdapter(adapter);

        getData();

        return null;
    }

    @Override
    public void setListener() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                }, 2 * 1000l);
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
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

        baseUI.baseDialog.showProgressDialog(R.string.reporting, Constants.DEFAULT_CODE);

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                baseUI.baseDialog.closeDialog(Constants.DEFAULT_CODE);

                data.get(position).setUploadStatus(true);
                adapter.refreshData(data, position);
            }
        }, 3 * 1000l);
    }

}
