package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.HeaderItemDecoration;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.CallBack.OnRcvScrollListener;
import com.hg.hollowgoods.Adapter.Example.Ex12.Ex12Adapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.Example.Ex12;
import com.hg.hollowgoods.Bean.ResponseInfo;
import com.hg.hollowgoods.Constant.CommonResource;
import com.hg.hollowgoods.Constant.Constants;
import com.hg.hollowgoods.Constant.HGInterfaceApi;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.Soap.SoapParam;
import com.hg.hollowgoods.Util.Soap.SoapUtils;

import java.util.ArrayList;

/**
 * RecyclerView示例界面
 * Created by HG
 */

public class Ex12Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SoapUtils.RequestListener {

    private RecyclerView result;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<CommonBean> data;
    private Ex12Adapter adapter;
    private int pageIndex = 1;
    private boolean isFirst = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean canLoadMore = true;
    private String searchKey = "";

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_12;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        result = findViewById(R.id.rv_result);
        refreshLayout = findViewById(R.id.swipeRefreshLayout);

        baseUI.setCommonTitleStyleAutoBackground(CommonResource.BACK_ICON, R.string.title_activity_ex12);

        refreshLayout.setColorSchemeColors(HGSystemConfig.REFRESH_COLORS);
        baseUI.initSearchView((CardView) result.getParent(), true);

        result.setLayoutManager(new LinearLayoutManager(this));
        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        HeaderItemDecoration.Builder builder = new HeaderItemDecoration.Builder(Constants.LIST_ITEM_TYPE_HEADER);
        result.addItemDecoration(builder.create());

        data = new ArrayList<>();
        adapter = new Ex12Adapter(this, data);
        result.setAdapter(adapter);

        baseUI.setDataMode(baseUI.DATA_MODE_LOAD_DATA_CENTER);
        getData();

        return null;
    }

    @Override
    public void setListener() {

        refreshLayout.setOnRefreshListener(this);
        result.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                onLoadMore();
            }
        });
    }

    private void getData() {

        ArrayList<SoapParam> params = new ArrayList<>();
        params.add(new SoapParam("str1", searchKey));
        params.add(new SoapParam("str2", pageIndex));

        new SoapUtils(this, this).request(HGInterfaceApi.EXAMPLE_SN, HGInterfaceApi.EXAMPLE_MN, params);
    }

    @Override
    public void onRequestSuccess(String methodName, ResponseInfo result) {

        ArrayList<Ex12> temp = new Gson().fromJson(result.getData(), new TypeToken<ArrayList<Ex12>>() {
        }.getType());

        if (temp != null) {
            if (isLoadMore && temp.size() == 0) {
                canLoadMore = false;
            }

            if (isFirst || isRefresh) {
                data.clear();
                data.add(new CommonBean(Constants.LIST_ITEM_TYPE_HEADER));
            }

            int i = 0;
            for (Ex12 t : temp) {
                t.setItemType(i % 2 == 0 ? Constants.LIST_ITEM_TYPE_1 : Constants.LIST_ITEM_TYPE_2);
                data.add(t);
                i++;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.refreshData(data);
                }
            });
        } else {
            if (isLoadMore) {
                canLoadMore = false;
            }
        }
    }

    @Override
    public void onRequestFail(String methodName, String result) {
        t.showShortToast(R.string.network_error);
    }

    @Override
    public void onRequestFinish() {

        if (isFirst) {
            baseUI.setDataMode(baseUI.DATA_MODE_HAS_DATA);
        }

        isFirst = false;
        isRefresh = false;
        isLoadMore = false;
        refreshLayout.setRefreshing(false);
        baseUI.setLoadDataBottomMode(false);
    }

    @Override
    public void onRefresh() {

        if (!isRefresh && !isFirst && !isLoadMore) {
            pageIndex = 1;
            searchKey = "";
            isRefresh = true;
            canLoadMore = true;
            getData();
        } else {
            refreshLayout.setRefreshing(false);
        }
    }

    private void onLoadMore() {

        if (canLoadMore && !isLoadMore && !isFirst && !isRefresh) {
            pageIndex++;
            isLoadMore = true;
            baseUI.setLoadDataBottomMode(true);

            getData();
        }
    }

    @Override
    public void onSearched(String searchKey) {

        this.searchKey = searchKey;
        isRefresh = true;
        pageIndex = 1;
        canLoadMore = true;
        getData();
    }

    @Override
    public void onSearchMenuItemClick(int id) {
        t.showShortToast("" + id);
    }
}
