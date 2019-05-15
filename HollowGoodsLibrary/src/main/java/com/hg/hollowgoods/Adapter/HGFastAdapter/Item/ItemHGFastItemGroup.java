package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemGroupData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.Widget.HGRefreshLayout;

import java.util.HashMap;

/**
 * 文字模板
 * Created by Hollow Goods on 2019-05-07.
 */
public class ItemHGFastItemGroup extends BaseItemHGFastItem<CommonBean> {

    private BaseUI baseUI;
    private OnHGFastItemClickListener onHGFastItemClickListener;
    private CommonBean bean;
    private HashMap<Integer, String> fieldTag;
    private HashMap<Integer, Boolean> initTag = new HashMap<>();

    ItemHGFastItemGroup(CommonBean bean, HashMap<Integer, String> fieldTag) {
        this.bean = bean;
        this.fieldTag = fieldTag;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hg_fast_item_group;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == ItemType.ItemGroup.getValue();
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        if (item instanceof HGFastItemGroupData) {
            HGFastItemGroupData data = item.getData();

            setMargin(viewHolder,
                    R.id.ll_marginView,
                    data.getMarginTop(),
                    data.getMarginLeft(),
                    data.getMarginBottom(),
                    data.getMarginRight()
            );

            // 主体
            HGFastAdapter adapter = new HGFastAdapter(viewHolder.getContext(), bean);
            HGRefreshLayout refreshLayout = viewHolder.getView(R.id.hgRefreshLayout);
            if (initTag.get(data.getItemId()) == null) {
                refreshLayout.initRecyclerView();
                refreshLayout.setAdapter(adapter);
            } else {
                refreshLayout.getRecyclerView().setHasFixedSize(true);
                refreshLayout.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
                refreshLayout.getRecyclerView().setLayoutManager(new LinearLayoutManager(viewHolder.getContext()));
                refreshLayout.getRecyclerView().setAdapter(adapter);
            }

            adapter.initData(data.getGroupData(), fieldTag);
            adapter.setBaseUI(baseUI);
            adapter.setOnHGFastItemClickListener(onHGFastItemClickListener);

            initTag.put(data.getItemId(), true);
        }
    }

    public void setBaseUI(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    void setOnHGFastItemClickListener(OnHGFastItemClickListener onHGFastItemClickListener) {
        this.onHGFastItemClickListener = onHGFastItemClickListener;
    }

}
