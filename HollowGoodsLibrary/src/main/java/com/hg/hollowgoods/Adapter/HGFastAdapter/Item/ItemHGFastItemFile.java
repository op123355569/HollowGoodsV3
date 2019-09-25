package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.hg.hollowgoods.Adapter.BaseRecyclerView.Base.ViewHolder;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemFileData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Util.HGFastFileSelectorUtils;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.FileSelectorUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.Glide.GlideOptions;

import java.util.ArrayList;

/**
 * 文件模板
 * Created by Hollow Goods on 2019-05-07.
 */
public class ItemHGFastItemFile extends BaseItemHGFastItem<CommonBean> {

    private BaseUI baseUI;
    private OnHGFastItemClickListener onHGFastItemClickListener;
    HGFastFileSelectorUtils hgFastFileSelectorUtils = new HGFastFileSelectorUtils();
    int clickItemId;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hg_fast_item_file;
    }

    @Override
    public boolean isForViewType(CommonBean item, int position) {
        return item.getItemType() == ItemType.ItemFile.getValue();
    }

    @Override
    public void convert(ViewHolder viewHolder, CommonBean item, int position) {

        if (item instanceof HGFastItemFileData) {
            HGFastItemFileData data = item.getData();

            setRunModeView(viewHolder,
                    R.id.tv_sortNumber_id,
                    data.getSortNumber(),
                    data.getItemId()
            );

            ArrayList<Media> media = data.getItemMedia();
            setLabel(viewHolder,
                    R.id.tv_notEmptyFlag,
                    R.id.tv_label,
                    data.isNotEmpty(),
                    data.getLabel(),
                    data.getLabelTextColorRes(),
                    media == null ? 0 : media.size()
            );

            setLeftIcon(viewHolder,
                    R.id.iv_leftIcon,
                    data.getLeftIconRes(),
                    data.getLeftIconResType(),
                    data.getLeftIconResName()
            );

            // 文件数量标签
            if (data.getMaxCount() < 1) {
                viewHolder.setText(R.id.tv_count, media == null ? "0" : media.size() + "");
            } else {
                viewHolder.setText(R.id.tv_count, (media == null ? "0" : media.size() + "") + "/" + data.getMaxCount());
            }

            // 文件预览图
            if (media == null || media.size() == 0) {
                ImageView img = viewHolder.getView(R.id.iv_imgPre);
                img.setPadding(0, 0, 0, 0);
                viewHolder.setImageResource(R.id.iv_imgPre, R.color.transparent);
            } else {
                Media m = media.get(media.size() - 1);
                if (m.getFile() == null && TextUtils.isEmpty(m.getUrl())) {
                    ImageView img = viewHolder.getView(R.id.iv_imgPre);
                    img.setPadding(0, 0, 0, 0);
                    viewHolder.setImageResource(R.id.iv_imgPre, R.color.transparent);
                } else {
                    String url;
                    if (m.getFile() == null) {
                        url = m.getUrl();
                    } else {
                        url = m.getFile().getAbsolutePath();
                    }

                    if (FileUtils.isImageFile(url)) {
                        ImageView img = viewHolder.getView(R.id.iv_imgPre);
                        img.setPadding(0, 0, 0, 0);

                        RequestOptions requestOptions = new RequestOptions().circleCrop();
                        GlideOptions glideOptions = new GlideOptions(url, null, GlideOptions.NO_FADE_IN, requestOptions);
                        glideOptions.setLoadType(HGConstants.IMG_LOAD_TYPE_FAST_ADAPTER_ITEM_SMALL);
                        viewHolder.setImageByUrl(R.id.iv_imgPre, glideOptions);
                    } else {
                        ImageView img = viewHolder.getView(R.id.iv_imgPre);
                        img.setPadding(16, 16, 16, 16);

                        int iconRes = new FileSelectorUtils().getFileIcon(url);
                        viewHolder.setImageResource(R.id.iv_imgPre, iconRes);
                    }
                }
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
                case File:
                    viewHolder.setOnClickListener(R.id.fl_count, new OnViewClickListener(false) {
                        @Override
                        public void onViewClick(View view, int id) {

                            clickItemId = data.getItemId();
                            ArrayList<Media> media = data.getItemMedia();

                            if (media != null) {
                                for (Media t : media) {
                                    t.setCanRemove(!data.isOnlyRead());
                                }
                            }

                            hgFastFileSelectorUtils.systemAppUtils.imagePre(viewHolder.getContext(), media, data.getLabel());
                        }
                    });

                    if (data.isOnlyRead()) {
                        viewHolder.setOnClickListener(R.id.ll_itemView, null);
                        viewHolder.setOnClickListener(R.id.iv_rightIcon, null);
                    } else {
                        if (baseUI != null) {
                            OnViewClickListener onViewClickListener = new OnViewClickListener(false) {
                                @Override
                                public void onViewClick(View view, int id) {

                                    clickItemId = data.getItemId();
                                    ArrayList<Media> media = data.getItemMedia();

                                    if (media == null || data.getMaxCount() < 1 || media.size() < data.getMaxCount()) {
                                        hgFastFileSelectorUtils.showFileSelectorWindow(baseUI.getBaseContext(), view, data.getFileMode(), media, data.getMaxCount(), data.getQuality(), data.getFileFilter());
                                    } else {
                                        String tip = baseUI.getBaseContext().getString(R.string.is_had_max_count);
                                        tip = String.format(tip, data.getMaxCount() + "");
                                        t.error(tip);
                                    }
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
                case SingleChoice:
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
                    viewHolder.setOnClickListener(R.id.fl_count, new OnViewClickListener(false) {
                        @Override
                        public void onViewClick(View view, int id) {
                            if (onHGFastItemClickListener != null) {
                                onHGFastItemClickListener.onFilePreClick(data.getItemId());
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
