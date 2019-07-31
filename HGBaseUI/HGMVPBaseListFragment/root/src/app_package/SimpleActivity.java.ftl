package ${packageName};

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.CommonAdapter;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseMVPFragment;
import com.hg.hollowgoods.UI.Base.ListDataFinishHelper;
import com.hg.hollowgoods.UI.Base.ListDataHelper;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Widget.HGRefreshLayout;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * ${activityTitle}碎片
 *
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseMVPFragment<${presenterName}> implements ${contractName}.View, ListDataHelper {

	@ViewInject(value = R.id.hgRefreshLayout)
    private HGRefreshLayout refreshLayout;

    private CommonAdapter adapter;
    private ArrayList<Object> data = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean isSearch = false;
    private String searchKey = "";
    private int oldDataSize = 0;

	@Override
    public int bindLayout() {
        return R.layout.${layoutName};
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_fragment_${classToResource(activityClass)});
        baseUI.initSearchView(refreshLayout, true);

        // TODO 创建一个适配器

        refreshLayout.initRecyclerView();
        refreshLayout.setAdapter(adapter);

        return null;
    }

    @Override
    public void setListener() {

        refreshLayout.getRefreshLayout().setOnRefreshListener(refreshLayout -> doRefresh());

        refreshLayout.getRefreshLayout().setOnLoadMoreListener(refreshLayout -> doLoadMore());

        refreshLayout.getRefreshLayout().setOnMultiPurposeListener(new ListDataFinishHelper() {
            @Override
            public void onAnimFinish(boolean isSuccess) {
                if (isSuccess) {
                    if (data.size() < pageSize || data.size() % pageSize != 0 || oldDataSize == data.size()) {
                        refreshLayout.getRefreshLayout().setNoMoreData(true);
                    } else {
                        refreshLayout.getRefreshLayout().setNoMoreData(false);
                    }

                    isRefresh = false;
                    isLoadMore = false;
                    isSearch = false;

                    oldDataSize = data.size();
                }
            }
        });
    }

	@Override
    public void initViewDelay() {
        refreshLayout.getRefreshLayout().autoRefresh();
    }

	@Override
    public ${presenterName} createPresenter() {
        return new ${presenterName}(getActivity());
    }

	@Override
    public void onSearched(String searchKey) {
        doSearch(searchKey);
    }

    @Override
    public void doRefresh() {
        if (!isRefresh && !isLoadMore && !isSearch) {
            isRefresh = true;
            pageIndex = 1;
            getData();
        }
    }

    @Override
    public void doLoadMore() {
        if (!isRefresh && !isLoadMore && !isSearch) {
            isLoadMore = true;
            pageIndex++;
            getData();
        }
    }

    @Override
    public void doSearch(String searchKey) {
        if (!isRefresh && !isLoadMore && !isSearch) {
            isSearch = true;
            pageIndex = 1;
            this.searchKey = searchKey;
            refreshLayout.getRefreshLayout().autoRefreshAnimationOnly();
            getData();
        }
    }

    private void getData() {
        mPresenter.getData(pageIndex, pageSize, searchKey);
    }

    @Override
    public void getDataSuccess(ArrayList<Object> tempData) {

        if (isRefresh || isSearch) {
            data.clear();
        }

        if (tempData != null) {
            data.addAll(tempData);
        }

        adapter.refreshData(data);
    }

    @Override
    public void getDataError() {
        t.error(R.string.network_error);
    }

    @Override
    public void getDataFinish() {
        if (isRefresh) {
            refreshLayout.getRefreshLayout().finishRefresh();
        } else if (isLoadMore) {
            refreshLayout.getRefreshLayout().finishLoadMore();
        } else if (isSearch) {
            refreshLayout.getRefreshLayout().finishRefresh();
        }
    }
}
