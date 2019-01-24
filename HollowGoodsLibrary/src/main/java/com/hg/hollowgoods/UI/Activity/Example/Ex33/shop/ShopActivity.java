package com.hg.hollowgoods.UI.Activity.Example.Ex33.shop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class ShopActivity extends BaseActivity {

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;

    private List<Item> data;
    private Shop shop;
    private InfiniteScrollAdapter infiniteAdapter;

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_shop;
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        currentItemName = findViewById(R.id.item_name);
        currentItemPrice = findViewById(R.id.item_price);
        rateItemButton = findViewById(R.id.item_btn_rate);
        itemPicker = findViewById(R.id.item_picker);

        shop = Shop.get(this);
        data = shop.getData();

        // 方向
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        // 滚动监听
        itemPicker.addOnItemChangedListener((viewHolder, adapterPosition) -> {
            int positionInDataSet = infiniteAdapter.getRealPosition(adapterPosition);
            onItemChanged(data.get(positionInDataSet));
        });

        // 创建普通适配器
        ShopAdapter adapter = new ShopAdapter(this, R.layout.item_shop_card, data);
        // 生成无限滚动适配器
        infiniteAdapter = InfiniteScrollAdapter.wrap(adapter);
        // 把无限滚动适配器放入普通适配器，为了获取真实的Position
        adapter.setInfiniteAdapter(infiniteAdapter);
        // 填充适配器
        itemPicker.setAdapter(infiniteAdapter);

        // 设置滚动动画时间
        itemPicker.setItemTransitionTimeMillis(300);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        onItemChanged(data.get(0));

        return null;
    }

    @Override
    public void setListener() {

        rateItemButton.setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
                Item current = data.get(realPosition);
                shop.setRated(current.getId(), !shop.isRated(current.getId()));
                changeRateButtonState(current);
            }
        });

        findViewById(R.id.item_btn_buy).setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                t.warning("别点了，没用！");
            }
        });

        findViewById(R.id.item_btn_comment).setOnClickListener(new OnViewClickListener(false) {
            @Override
            public void onViewClick(View view, int id) {
                t.warning("别点了，没用！");
            }
        });
    }

    private void onItemChanged(Item item) {
        currentItemName.setText(item.getName());
        currentItemPrice.setText(item.getPrice());
        changeRateButtonState(item);
    }

    private void changeRateButtonState(Item item) {
        if (shop.isRated(item.getId())) {
            rateItemButton.setImageResource(R.drawable.ic_android_green_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
        } else {
            rateItemButton.setImageResource(R.drawable.ic_android_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
        }
    }

}
