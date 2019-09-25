package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemNumberData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemNumberChangeListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ConfigInput;
import com.hg.hollowgoods.Widget.StepperTouch.StepperTouch;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.Item.Validator;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.ValidatorFactory;
import com.hg.hollowgoods.Widget.ValidatorInput.Validator.ValidatorType;

import java.util.ArrayList;

/**
 * 数字模板
 * Created by Hollow Goods on 2019-05-13.
 */
public class ItemHGFastItemNumber extends BaseItemHGFastItem<CommonBean> {

    private BaseUI baseUI;
    private OnHGFastItemNumberChangeListener onHGFastItemNumberChangeListener;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hg_fast_item_number;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == ItemType.ItemNumber.getValue();
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        if (item instanceof HGFastItemNumberData) {
            HGFastItemNumberData data = item.getData();

            setRunModeView(viewHolder,
                    R.id.tv_sortNumber_id,
                    data.getSortNumber(),
                    data.getItemId()
            );

            setLabel(viewHolder,
                    R.id.tv_notEmptyFlag,
                    R.id.tv_label,
                    data.isNotEmpty(),
                    data.getLabel(),
                    data.getLabelTextColorRes(),
                    data.getContent().length()
            );

            setLeftIcon(viewHolder,
                    R.id.iv_leftIcon,
                    data.getLeftIconRes(),
                    data.getLeftIconResType(),
                    data.getLeftIconResName()
            );

            // 设置内容
            StepperTouch stepperTouch = viewHolder.getView(R.id.stepperTouch);
            if (data.isOnlyRead()) {
                stepperTouch.setSideTapEnabled(false);
                stepperTouch.allowNegative(false);
                stepperTouch.allowPositive(false);
            } else {
                stepperTouch.setSideTapEnabled(true);
                stepperTouch.setMinValue(data.getMinValue());
                stepperTouch.setMaxValue(data.getMaxValue());
                stepperTouch.setDifValue(data.getDifValue());
                stepperTouch.addStepCallback((value, positive) -> {
                    stepperTouch.allowNegative(value > stepperTouch.getMinValue());
                    stepperTouch.allowPositive(value < stepperTouch.getMaxValue());

                    if (onHGFastItemNumberChangeListener != null) {
                        onHGFastItemNumberChangeListener.onNumberChange(data.getItemId(), value);
                    }
                });
                stepperTouch.setCount(Integer.valueOf(data.getContent()));
            }

            setRightIcon(viewHolder,
                    R.id.iv_rightIcon,
                    R.id.contentMargin,
                    data.getRightIconRes(),
                    data.getRightIconResType(),
                    data.getRightIconResName(),
                    data.getItemMode(),
                    data.isOnlyRead()
            );

            setMargin(viewHolder,
                    R.id.ll_marginView,
                    data.getMarginTop(),
                    data.getMarginLeft(),
                    data.getMarginBottom(),
                    data.getMarginRight()
            );

            // 点击事件
            switch (data.getItemMode()) {
                case SingleChoice:
                case Input:
                case File:
                case Customize:
                default:
                    if (data.isOnlyRead()) {
                        viewHolder.setOnClickListener(R.id.ll_itemView, null);
                        viewHolder.setOnClickListener(R.id.iv_rightIcon, null);
                    } else {
                        if (baseUI != null) {
                            OnViewClickListener onViewClickListener = new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {
                                    ConfigInput configInput = new ConfigInput();
                                    configInput.setTitle("请输入" + data.getLabel());
                                    configInput.setAutoShowKeyboard(true);
                                    configInput.setInputType(HGConstants.INPUT_TYPE_NUMBER);
                                    configInput.setText(data.getContent());
                                    ArrayList<Validator> tempValidators = new ArrayList<>();
                                    if (data.getMinValue() != Integer.MIN_VALUE) {
                                        tempValidators.add(ValidatorFactory.getValidator(ValidatorType.MIN_VALUE, "最小值为：" + data.getMinValue(), data.getMinValue()));
                                    }
                                    if (data.getMaxValue() != Integer.MAX_VALUE) {
                                        tempValidators.add(ValidatorFactory.getValidator(ValidatorType.MAX_VALUE, "最大值为：" + data.getMaxValue(), data.getMaxValue()));
                                    }
                                    if (tempValidators.size() > 0) {
                                        Validator[] validators = new Validator[tempValidators.size()];
                                        for (int i = 0; i < validators.length; i++) {
                                            validators[i] = tempValidators.get(i);
                                        }
                                        configInput.setValidator(validators);
                                    }
                                    baseUI.baseDialog.showInputDialog(configInput, data.getItemId());
                                }
                            };
                            viewHolder.setOnClickListener(R.id.ll_itemView, onViewClickListener);
                            viewHolder.setOnClickListener(R.id.iv_rightIcon, onViewClickListener);
                        }
                    }
                    break;
            }
        }
    }

    public void setBaseUI(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    void setOnHGFastItemNumberChangeListener(OnHGFastItemNumberChangeListener onHGFastItemNumberChangeListener) {
        this.onHGFastItemNumberChangeListener = onHGFastItemNumberChangeListener;
    }
}
