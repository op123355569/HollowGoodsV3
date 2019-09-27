package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.view.View;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemDateData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Application.ApplicationBuilder;
import com.hg.hollowgoods.Application.IBaseApplication;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.HGDateTimeDialog;
import com.hg.hollowgoods.Util.RegexUtils;
import com.hg.hollowgoods.Util.StringUtils;

/**
 * 日期模板
 * Created by Hollow Goods on 2019-05-10.
 */
public class ItemHGFastItemDate extends BaseItemHGFastItem<CommonBean> {

    private BaseUI baseUI;
    private OnHGFastItemClickListener onHGFastItemClickListener;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hg_fast_item_date;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == ItemType.ItemDate.getValue();
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        if (item instanceof HGFastItemDateData) {
            HGFastItemDateData data = item.getData();

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
            viewHolder.setText(R.id.tv_content, StringUtils.getDateTimeString(data.getContent(), data.getDateFormatMode()));
            viewHolder.setTextHint(R.id.tv_content, data.getContentHint());
            viewHolder.setTextColorRes(R.id.tv_content, data.getContentTextColorRes());

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

            setPaddingTopAndBottom(viewHolder,
                    R.id.ll_itemView,
                    data.getPaddingTopAndBottom()
            );

            // 点击事件
            switch (data.getItemMode()) {
                case SingleChoice:
                    if (data.isOnlyRead()) {
                        viewHolder.setOnClickListener(R.id.ll_itemView, null);
                        viewHolder.setOnClickListener(R.id.iv_rightIcon, null);
                    } else {
                        if (baseUI != null) {
                            OnViewClickListener onViewClickListener = new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {

                                    long date;
                                    HGDateTimeDialog.DateTimeDialogType dateTimeDialogType;

                                    if (RegexUtils.isPositiveInteger(data.getContent())) {
                                        date = Long.valueOf(data.getContent());
                                    } else {
                                        IBaseApplication baseApplication = ApplicationBuilder.create();
                                        date = baseApplication.getNowTime();
                                    }

                                    switch (data.getDateFormatMode()) {
                                        case LINE_YMDHM:
                                        case POINT_YMDHM:
                                        case Chinese_YMDHM:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.YMD_HM;
                                            break;
                                        case LINE_YMD:
                                        case POINT_YMD:
                                        case Chinese_YMD:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.YMD;
                                            break;
                                        case LINE_YM:
                                        case POINT_YM:
                                        case Chinese_YM:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.YM;
                                            break;
                                        case Time_Y:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.Y;
                                            break;
                                        case Time_HM:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.HM;
                                            break;
                                        default:
                                            dateTimeDialogType = HGDateTimeDialog.DateTimeDialogType.YMD_HM;
                                            break;
                                    }

                                    baseUI.baseDialog.showDateTimeDialog(date, dateTimeDialogType, data.getItemId());
                                }
                            };
                            viewHolder.setOnClickListener(R.id.ll_itemView, onViewClickListener);
                            viewHolder.setOnClickListener(R.id.iv_rightIcon, onViewClickListener);
                        } else {
                            viewHolder.setOnClickListener(R.id.ll_itemView, new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {
                                    if (onHGFastItemClickListener != null) {
                                        onHGFastItemClickListener.onItemClick(data.getItemId());
                                    }
                                }
                            });
                            viewHolder.setOnClickListener(R.id.iv_rightIcon, new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {
                                    if (onHGFastItemClickListener != null) {
                                        onHGFastItemClickListener.onRightIconClick(data.getItemId());
                                    }
                                }
                            });
                        }
                    }
                    break;
                case Input:
                case File:
                case Customize:
                default:
                    viewHolder.setOnClickListener(R.id.ll_itemView, new OnViewClickListener(false) {
                        @Override
                        public void onViewClick(View view, int id) {
                            if (onHGFastItemClickListener != null) {
                                onHGFastItemClickListener.onItemClick(data.getItemId());
                            }
                        }
                    });
                    viewHolder.setOnClickListener(R.id.iv_rightIcon, new OnViewClickListener(false) {
                        @Override
                        public void onViewClick(View view, int id) {
                            if (onHGFastItemClickListener != null) {
                                onHGFastItemClickListener.onRightIconClick(data.getItemId());
                            }
                        }
                    });
                    break;
            }
        }
    }

    public void setBaseUI(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    void setOnHGFastItemClickListener(OnHGFastItemClickListener onHGFastItemClickListener) {
        this.onHGFastItemClickListener = onHGFastItemClickListener;
    }
}
