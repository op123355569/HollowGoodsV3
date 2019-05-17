package ${packageName};

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Item.HGFastAdapter;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseMVPActivity;
import com.hg.hollowgoods.Widget.HGRefreshLayout;

/**
 * ${activityTitle}界面
 *
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseMVPActivity<${presenterName}> implements ${contractName}.View {

    @ViewInject(value = R.id.hgRefreshLayout)
    private HGRefreshLayout refreshLayout;

    private HGFastAdapter adapter;
    private CommonBean data;

	@Override
    public Activity addToExitGroup() {
        return this;
    }
	
	@Override
    public int bindLayout() {
        return R.layout.${layoutName};
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_${activityToLayout(activityClass)});

        adapter = new HGFastAdapter(baseUI.getBaseContext(), data);

        refreshLayout.initRecyclerView();
        refreshLayout.setAdapter(adapter);

        adapter.initData(false);
        adapter.setBaseUI(baseUI);

        return this;
    }

    @Override
    public void setListener() {

        adapter.setOnHGFastItemClickListener(new OnHGFastItemClickListener() {
            @Override
            public void onItemClick(int clickId) {

            }

            @Override
            public void onRightIconClick(int clickId) {

            }

            @Override
            public void onFilePreClick(int clickId) {

            }
        });

        baseUI.baseDialog.setOnDialogClickListener((code, result, backData) -> {
            if (result) {
                adapter.resolveOnDialogClick(code, backData);
            }
        });
    }
	
	@Override
    public ${presenterName} createPresenter() {
        return new ${presenterName}(this);
    }

    @Override
    public void onEventUI(Event event) {
        adapter.resolveOnEventUI(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.resolveOnActivityResult(baseUI.getBaseContext(), requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
	
}
