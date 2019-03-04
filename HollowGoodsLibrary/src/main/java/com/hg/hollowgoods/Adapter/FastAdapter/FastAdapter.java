package com.hg.hollowgoods.Adapter.FastAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;
import com.hg.hollowgoods.Adapter.FastAdapter.Annotation.Item.FastItem;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.FastItemData;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.FastAdapter.CallBack.OnFastClick;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.FastItemMode;
import com.hg.hollowgoods.Adapter.FastAdapter.Constant.ParamItem;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.Click.OnRecyclerViewItemClickListener;
import com.hg.hollowgoods.UI.Base.Click.OnViewClickListener;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.DensityUtils;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.PopupWinHelper;
import com.hg.hollowgoods.Util.SystemAppUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 快速适配器
 * Created by HG on 2018-06-13.
 */
public class FastAdapter extends MultiItemTypeAdapter<CommonBean> {

    public static final int ITEM_TYPE_LIST = 0;
    public static final int ITEM_TYPE_ITEM = 1;

    public static final int REQUEST_CODE_TAKE_PHOTO = 10;
    public static final int REQUEST_CODE_OPEN_ALBUM = 11;
    public static final int REQUEST_CODE_OPEN_ALBUM_2 = 12;

    private Context context;
    private ItemFastList itemFastList;
    private ItemFastItem itemFastItem;
    private OnFastClick onFastClick;

    public FastAdapter(Context context, List<CommonBean> datas, boolean openList, boolean openItem) {
        this(context, datas, openList, openItem, ParamItem.class);
    }

    public FastAdapter(Context context, List<CommonBean> datas, boolean openList, boolean openItem, Class<?> itemsNameClass) {

        super(context, datas);
        this.context = context;

        if (openList) {
            addItemViewDelegate(ITEM_TYPE_LIST, itemFastList = new ItemFastList(context));
            itemFastList.setItemsNameClass(itemsNameClass);
        }

        if (openItem) {
            addItemViewDelegate(ITEM_TYPE_ITEM, itemFastItem = new ItemFastItem(context));
            itemFastItem.setItemsNameClass(itemsNameClass);
        }

        setOnItemClickListener(new OnRecyclerViewItemClickListener(false) {
            @Override
            public void onRecyclerViewItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                if (onFastClick != null) {
                    onFastClick.onItemClick(view, viewHolder, position);
                }
            }
        });
    }

    /**
     * 设置点击监听
     *
     * @param onFastClick
     */
    public void setOnFastClick(OnFastClick onFastClick) {

        this.onFastClick = onFastClick;

        if (itemFastList != null) {
            itemFastList.setOnFastClick(onFastClick);
        }

        if (itemFastItem != null) {
            itemFastItem.setOnFastClick(onFastClick);
        }
    }

    public ArrayList<FastItemData> getDetailItemData(CommonBean bean) {

        if (itemFastItem != null) {
            return itemFastItem.getDetailItemData(bean);
        }

        return new ArrayList<>();
    }

    public static void setAllItemOnlyRead(CommonBean bean, boolean isOnlyRead) {

        Field[] allField = bean.getClass().getDeclaredFields();

        for (Field t : allField) {
            bean.addOnlyReadItem(t.getName(), isOnlyRead);
        }
    }

    public String getFieldNameBySortNumber(CommonBean bean, int sortNumber) {

        String result = "";

        if (itemFastItem != null) {
            ArrayList<Field> allField = itemFastItem.getAllField(bean);
            FastItem annotation;
            for (Field t : allField) {
                annotation = t.getAnnotation(FastItem.class);
                if (annotation.sortNumber() == sortNumber) {
                    result = t.getName();
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 刷新所有详情项
     */
    public void refreshFastItem(CommonBean bean) {

        if (itemFastItem != null) {
            ArrayList<FastItemData> temp = getDetailItemData(bean);
            ArrayList<CommonBean> data = new ArrayList<>();
            data.addAll(temp);

            refreshData(data);
        }
    }

    /**
     * 刷新单项
     *
     * @param bean
     * @param position
     */
    public void refreshFastItem(CommonBean bean, int position) {

        if (itemFastItem != null) {
            FastItemData temp = mDatas.get(position).getData();
            FastItemData newData = itemFastItem.getDetailItemData(bean, temp.sortNumber);

            mDatas.set(position, newData);
            refreshData(mDatas, position);
        }
    }

    private ArrayList<FastItemData> addItem = new ArrayList<>();

    public void addFastItem(CommonBean bean, int... sortNumber) {

        if (itemFastItem != null && sortNumber != null && sortNumber.length > 0) {
            addItem.clear();
            for (Integer t : sortNumber) {
                addItem.add(itemFastItem.getDetailItemData(bean, t));
            }

            addFastItem();
        }
    }

    private void addFastItem() {

        if (addItem.size() > 0) {
            int index = -1;
            FastItemData before;
            FastItemData next;

            for (int i = 0; i < mDatas.size() - 1; i++) {
                before = mDatas.get(i).getData();
                next = mDatas.get(i + 1).getData();
                if (addItem.get(0).sortNumber > before.sortNumber && addItem.get(0).sortNumber < next.sortNumber) {
                    index = i + 1;
                    break;
                }
            }

            if (index == -1) {
                mDatas.add(addItem.get(0));
            } else {
                mDatas.add(index, addItem.get(0));
            }

            addDatas(mDatas, index == -1 ? mDatas.size() - 1 : index, 1);
            addItem.remove(0);

            new Handler().postDelayed(() -> addFastItem(), 300);
        }
    }

    private ArrayList<FastItemData> removeItem = new ArrayList<>();

    public void removeFastItem(CommonBean bean, int... sortNumber) {

        if (itemFastItem != null && sortNumber != null && sortNumber.length > 0) {
            removeItem.clear();
            for (Integer t : sortNumber) {
                removeItem.add(itemFastItem.getDetailItemData(bean, t));
            }

            removeFastItem();
        }
    }

    private void removeFastItem() {

        if (removeItem.size() > 0) {
            int index = -1;
            FastItemData temp;

            for (int i = 0; i < mDatas.size(); i++) {
                temp = mDatas.get(i).getData();
                if (removeItem.get(0).sortNumber == temp.sortNumber) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                mDatas.remove(index);
                removeDatas(mDatas, index, 1);
            }

            removeItem.remove(0);

            new Handler().postDelayed(() -> removeFastItem(), 300);
        }
    }

    public boolean checkNotEmptyItem(CommonBean bean) {

        ArrayList<FastItemData> temp = getDetailItemData(bean);

        for (FastItemData p : temp) {
            if (p.isNotEmpty &&
                    (
                            (p.fastItemMode != FastItemMode.File && TextUtils.isEmpty(p.content.toString()))
                                    ||
                                    (p.fastItemMode == FastItemMode.File && (bean.getMedia().get(p.sortNumber) == null
                                            || bean.getMedia().get(p.sortNumber).size() == 0))
                    )
            ) {
                String tips;

                switch (p.fastItemMode) {
                    case Input:
                        tips = context.getString(R.string.please_input_content);
                        break;
                    case Choose:
                        tips = context.getString(R.string.please_choose_content);
                        break;
                    case File:
                        tips = context.getString(R.string.please_get_content);
                        break;
                    default:
                        tips = context.getString(R.string.please_other_content);
                        break;
                }

                tips = String.format(tips, p.label);
                t.warning(tips);

                return false;
            }
        }

        return true;
    }

    private PopupWinHelper helper;

    public void showImageWindow(View view, int position, int sortNumber) {

        this.tagView = view;
        this.clickPosition = position;
        this.clickSortNumber = sortNumber;

        if (helper == null) {
            helper = new PopupWinHelper(context, v -> {
                if (onFastClick != null) {
                    if (v.getId() == R.id.ll_takePhoto) {
                        onFastClick.onTakePhotoClick(tagView, clickPosition, clickSortNumber);
                    } else if (v.getId() == R.id.ll_openAlbum) {
                        onFastClick.onOpenAlbumClick(tagView, clickPosition, clickSortNumber);
                    }
                }

                helper.closePopupwin();
            });

            helper.init(
                    R.layout.popupwin_fast_image_choose,
                    Gravity.BOTTOM,
                    android.R.style.Animation_Toast,
                    "#AA000000",
                    new int[]{R.id.ll_takePhoto, R.id.ll_openAlbum},
                    new int[]{PopupWinHelper.TYPE_ON_CLICK, PopupWinHelper.TYPE_ON_CLICK},
                    false
            );
        }

        helper.showPopupwin(view);
    }

    private SystemAppUtils systemAppUtils = new SystemAppUtils();
    private int clickPosition;
    private int clickSortNumber;
    private View tagView;

    public void takePhoto(Activity activity, int clickPosition, int clickSortNumber) {
        this.clickPosition = clickPosition;
        this.clickSortNumber = clickSortNumber;
        systemAppUtils.takePhoto(activity, REQUEST_CODE_TAKE_PHOTO, 50, false);
    }

    public void openAlubm(Activity activity, int clickPosition, int clickSortNumber) {
        this.clickPosition = clickPosition;
        this.clickSortNumber = clickSortNumber;
        systemAppUtils.openAlbum(activity, REQUEST_CODE_OPEN_ALBUM);
    }

    public void openAlbum(Activity activity, CommonBean bean, int maxCount, int clickPosition, int clickSortNumber) {

        this.clickPosition = clickPosition;
        this.clickSortNumber = clickSortNumber;

        ArrayList<Media> medias = bean.getMedia().get(clickSortNumber);
        ArrayList<String> photos = new ArrayList<>();
        if (medias != null) {
            for (Media t : medias) {
                if (t.getFile() != null && FileUtils.isImageFile(t.getFile().getAbsolutePath())) {
                    photos.add(t.getFile().getAbsolutePath());
                }
            }
        }
        systemAppUtils.checkPhotos(activity, REQUEST_CODE_OPEN_ALBUM_2, maxCount, photos);
    }

    public void onActivityResultForImage(Activity activity, CommonBean bean, int requestCode, int resultCode, Intent backData) {

        if (resultCode == activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_OPEN_ALBUM:
                    if (systemAppUtils.onActivityResultForOpenAlbum(context, backData)) {
                        Media media = new Media();
                        media.setFile(new File(systemAppUtils.getAlbumPhotoPath()));

                        ArrayList<Media> medias = bean.getMedia().get(clickSortNumber);
                        if (medias == null) {
                            medias = new ArrayList<>();
                        }
                        medias.add(media);

                        bean.getMedia().put(clickSortNumber, medias);
                        refreshFastItem(bean, clickPosition);
                    }
                    break;
                case REQUEST_CODE_OPEN_ALBUM_2:
                    ArrayList<String> photos = systemAppUtils.onActivityResultForCheckPhotos(backData);

                    if (photos != null) {
                        ArrayList<Media> medias = bean.getMedia().get(clickSortNumber);
                        if (medias == null) {
                            medias = new ArrayList<>();
                        }

                        for (int i = 0; i < medias.size(); ) {
                            if (medias.get(i).getFile() != null && FileUtils.isImageFile(medias.get(i).getFile().getAbsolutePath())) {
                                medias.remove(i);
                            } else {
                                i++;
                            }
                        }

                        Media media;

                        for (String t : photos) {
                            media = new Media();
                            media.setFile(new File(t));

                            medias.add(media);
                        }

                        bean.getMedia().put(clickSortNumber, medias);
                        refreshFastItem(bean, clickPosition);
                    }
                    break;
                case REQUEST_CODE_TAKE_PHOTO:
                    if (systemAppUtils.onActivityResultForTakePhoto(activity)) {
                        Media media = new Media();
                        media.setFile(systemAppUtils.getCameraPhotoFile());

                        ArrayList<Media> medias = bean.getMedia().get(clickSortNumber);
                        if (medias == null) {
                            medias = new ArrayList<>();
                        }
                        medias.add(media);

                        bean.getMedia().put(clickSortNumber, medias);
                        refreshFastItem(bean, clickPosition);
                    }
                    break;
            }
        }
    }

    private FloatingActionButton submit;

    /**
     * 初始化提交按钮
     *
     * @param topView
     */
    public void initSubmitButton(View topView, RecyclerView recyclerView) {

        Object temp = topView.getParent();
        if (temp instanceof FrameLayout) {
            FrameLayout v = (FrameLayout) temp;

            submit = new FloatingActionButton(topView.getContext());
            submit.setId(R.id.submit);
            submit.setImageResource(R.drawable.ic_fast_submit);
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                    DensityUtils.dp2px(topView.getContext(), 50f),
                    DensityUtils.dp2px(topView.getContext(), 50f),
                    Gravity.RIGHT | Gravity.BOTTOM
            );
            flp.rightMargin = DensityUtils.dp2px(topView.getContext(), 16f);
            flp.bottomMargin = DensityUtils.dp2px(topView.getContext(), 16f);
            submit.setLayoutParams(flp);

            v.addView(submit);

            submit.setOnClickListener(new OnViewClickListener(false) {
                @Override
                public void onViewClick(View view, int id) {
                    if (onFastClick != null) {
                        onFastClick.onSubmitClick(view, id);
                    }
                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            showSubmitButton();
                            break;
                        case RecyclerView.SCROLL_STATE_SETTLING:
                            break;
                        case RecyclerView.SCROLL_STATE_IDLE:
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hideSubmitButton();
                                }
                            }, 3 * 1000);
                            break;
                    }

                    showSubmitButton();
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideSubmitButton();
                }
            }, 3 * 1000);
        }
    }

    public void setSubmitButtonRes(int res) {
        if (submit != null) {
            submit.setImageResource(res);
        }
    }

    private void showSubmitButton() {

        if (submit.getVisibility() != View.VISIBLE) {
            submit.setVisibility(View.VISIBLE);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(submit, "scaleX", 0f, 1f),
                    ObjectAnimator.ofFloat(submit, "scaleY", 0f, 1f)
            );
            set.setDuration(300).start();
        }
    }

    private void hideSubmitButton() {

        if (submit.getVisibility() != View.GONE) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(submit, "scaleX", 1f, 0f),
                    ObjectAnimator.ofFloat(submit, "scaleY", 1f, 0f)
            );
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    submit.setVisibility(View.GONE);
                }
            });
            set.setDuration(300).start();
        }
    }

}
