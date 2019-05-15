package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemWordData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.SingleChoiceMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Util.HGFastDataUtils;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Dialog.ConfigInput;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.ReflectUtils;
import com.hg.hollowgoods.Util.XUtils.GetHttpDataListener;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 文字模板
 * Created by Hollow Goods on 2019-05-07.
 */
public class ItemHGFastItemWord extends BaseItemHGFastItem<CommonBean> {

    private final int DIALOG_CODE_PROGRESS = 1000;

    private BaseUI baseUI;
    private OnHGFastItemClickListener onHGFastItemClickListener;
    private HashMap<Integer, String[]> singleChoiceItemsTag = new HashMap<>();
    private HashMap<Integer, ArrayList<Object>> singleChoiceSourceTag = new HashMap<>();
    public int clickId;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hg_fast_item_word;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == ItemType.ItemWord.getValue();
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        if (item instanceof HGFastItemWordData) {
            HGFastItemWordData data = item.getData();

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
                    data.getLeftIconRes()
            );

            // 设置内容
            viewHolder.setText(R.id.tv_content, data.getContent());
            viewHolder.setTextHint(R.id.tv_content, data.getContentHint());
            viewHolder.setTextColorRes(R.id.tv_content, data.getContentTextColorRes());

            setRightIcon(viewHolder,
                    R.id.iv_rightIcon,
                    R.id.contentMargin,
                    data.getRightIconRes(),
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
                case Input:
                    if (data.isOnlyRead()) {
                        viewHolder.setOnClickListener(R.id.ll_itemView, null);
                        viewHolder.setOnClickListener(R.id.iv_rightIcon, null);
                    } else {
                        if (baseUI != null) {
                            OnViewClickListener onViewClickListener = new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {
                                    ConfigInput configInput = data.getConfigInput();
                                    if (configInput == null) {
                                        configInput = new ConfigInput();
                                        configInput.setTitle("请输入" + data.getLabel());
                                        configInput.setAutoShowKeyboard(true);
                                    }
                                    configInput.setText(data.getContent());
                                    baseUI.baseDialog.showInputDialog(configInput, data.getItemId());
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
                case SingleChoice:
                    if (data.isOnlyRead()) {
                        viewHolder.setOnClickListener(R.id.ll_itemView, null);
                        viewHolder.setOnClickListener(R.id.iv_rightIcon, null);
                    } else {
                        if (baseUI != null) {
                            OnViewClickListener onViewClickListener = new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {
                                    check2ShowDialog(data);
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

    private void check2ShowDialog(HGFastItemWordData data) {

        String title = "请选择" + data.getLabel();
        Object items;

        if (data.getSingleChoiceMode() == SingleChoiceMode.Local) {
            items = data.getSingleChoiceItem();

            int checkedPosition = HGFastDataUtils.getSingleChoiceItemCheckedPosition(items, data.getContent());
            baseUI.baseDialog.showSingleDialog(title, items, checkedPosition, data.getItemId());
        } else if (data.getSingleChoiceMode() == SingleChoiceMode.Network) {
            items = singleChoiceItemsTag.get(data.getItemId());

            if (items == null) {
                clickId = data.getItemId();
                getSingleChoiceItem(data);
            } else {
                data.setSingleChoiceItem(items);
                data.addObj(HGParamKey.ListData.getValue(), singleChoiceSourceTag.get(data.getItemId()));
                int checkedPosition = HGFastDataUtils.getSingleChoiceItemCheckedPosition(items, data.getContent());
                baseUI.baseDialog.showSingleDialog(title, items, checkedPosition, data.getItemId());
            }
        }
    }

    private void getSingleChoiceItem(HGFastItemWordData data) {

        baseUI.baseDialog.showProgressDialog(R.string.hg_fast_adapter_word_single_choice_net_loading, DIALOG_CODE_PROGRESS);

        RequestParams params = (RequestParams) data.getSingleChoiceNetRequestParam();

        XUtils xUtils = new XUtils();
        xUtils.setGetHttpDataListener(new GetHttpDataListener() {
            @Override
            public void onGetSuccess(String result) {

                try {
                    JSONObject jsonData = new JSONObject(result);
                    Object temp = jsonData.get(data.getSingleChoiceNetDataKeyName());

                    if (temp != null && !TextUtils.isEmpty(temp.toString())) {
                        ArrayList<Object> temp2 = new Gson().fromJson(temp.toString(), data.getSingleChoiceNetDataType());

                        if (temp2 != null) {
                            singleChoiceSourceTag.put(data.getItemId(), temp2);

                            String[] items = new String[temp2.size()];
                            String fieldName = data.getSingleChoiceNetDataValueName();
                            int i = 0;

                            for (Object t : temp2) {
                                items[i++] = ReflectUtils.getObjValue(t, fieldName) + "";
                            }

                            singleChoiceItemsTag.put(data.getItemId(), items);

                            check2ShowDialog(data);
                        } else {
                            t.warning(R.string.hg_fast_adapter_word_single_choice_net_no_data);
                        }
                    } else {
                        t.warning(R.string.hg_fast_adapter_word_single_choice_net_no_data);
                    }
                } catch (Exception e) {
                    t.warning(R.string.hg_fast_adapter_word_single_choice_net_no_data);
                }
            }

            @Override
            public void onGetError(Throwable ex) {
                t.error(R.string.network_error);
            }

            @Override
            public void onGetLoading(long total, long current) {

            }

            @Override
            public void onGetFinish() {
                baseUI.baseDialog.closeDialog(DIALOG_CODE_PROGRESS);
            }

            @Override
            public void onGetCancel(Callback.CancelledException cex) {

            }
        });
        xUtils.getHttpData(data.getHttpMethod(), params);
    }

    public void setBaseUI(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    public void setOnHGFastItemClickListener(OnHGFastItemClickListener onHGFastItemClickListener) {
        this.onHGFastItemClickListener = onHGFastItemClickListener;
    }
}
