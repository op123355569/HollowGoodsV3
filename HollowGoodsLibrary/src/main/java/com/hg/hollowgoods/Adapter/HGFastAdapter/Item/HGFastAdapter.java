package com.hg.hollowgoods.Adapter.HGFastAdapter.Item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hg.hollowgoods.Adapter.BaseRecyclerView.MultiItemTypeAdapter;
import com.hg.hollowgoods.Adapter.FastAdapter.Bean.Media;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemCustomize;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemDate;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemFile;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemGroup;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemNumber;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Annotation.HGFastItemWord;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemCustomizeData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemDateData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemFileData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemGroupData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemNumberData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Bean.HGFastItemWordData;
import com.hg.hollowgoods.Adapter.HGFastAdapter.CallBack.OnHGFastItemClickListener;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.ItemType;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Type.SingleChoiceMode;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Util.HGFastDataUtils;
import com.hg.hollowgoods.Adapter.HGFastAdapter.Util.HGFastFileSelectorUtils;
import com.hg.hollowgoods.Bean.CommonBean.CommonBean;
import com.hg.hollowgoods.Bean.EventBus.Event;
import com.hg.hollowgoods.Bean.EventBus.HGEventActionCode;
import com.hg.hollowgoods.Constant.HGParamKey;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseUI;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;
import com.hg.hollowgoods.Util.FileUtils;
import com.hg.hollowgoods.Util.ReflectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * HG快速适配器
 * Created by Hollow Goods on 2019-05-07.
 */
public class HGFastAdapter extends MultiItemTypeAdapter<CommonBean> {

    private CommonBean bean;
    private HGFastDataUtils hgFastDataUtils;
    private Context context;

    private ItemHGFastItemWord itemHGFastItemWord;
    private ItemHGFastItemFile itemHGFastItemFile;
    private ItemHGFastItemDate itemHGFastItemDate;
    private ItemHGFastItemNumber itemHGFastItemNumber;
    private ItemHGFastItemGroup itemHGFastItemGroup;

    public HGFastAdapter(Context context, CommonBean data) {
        super(context, new ArrayList<>());
        bean = data;
        this.context = context;
    }

    /**
     * 初始化数据
     *
     * @param isOnlyRead 统一设置所有字段的只读性
     */
    public void initData(boolean isOnlyRead) {

        hgFastDataUtils = new HGFastDataUtils();
        ArrayList<CommonBean> temp = hgFastDataUtils.resolveHGFastItemData(bean);

        for (CommonBean t : temp) {
            bean.addOnlyReadItem(t.getItemId(), isOnlyRead);
            t.setOnlyRead(isOnlyRead);
        }

        hgFastDataUtils.removeOverItems(temp);
        refreshItemViewDelegate();

        refreshData(temp);
    }

    private void refreshItemViewDelegate() {

        Boolean hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemWord.class);
        if (hasItem != null && hasItem) {
            addItemViewDelegate(ItemType.ItemWord.getValue(), itemHGFastItemWord = new ItemHGFastItemWord());
        }

        hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemFile.class);
        if (hasItem != null && hasItem) {
            addItemViewDelegate(ItemType.ItemFile.getValue(), itemHGFastItemFile = new ItemHGFastItemFile());
        }

        hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemDate.class);
        if (hasItem != null && hasItem) {
            addItemViewDelegate(ItemType.ItemDate.getValue(), itemHGFastItemDate = new ItemHGFastItemDate());
        }

        hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemNumber.class);
        if (hasItem != null && hasItem) {
            addItemViewDelegate(ItemType.ItemNumber.getValue(), itemHGFastItemNumber = new ItemHGFastItemNumber());
            itemHGFastItemNumber.setOnHGFastItemNumberChangeListener((id, value) -> {

                HGFastItemNumberData item = hgFastDataUtils.getItemTag().get(id).getData();
                item.setContent(value + "");

                setBeanValue(hgFastDataUtils.getFieldTag().get(id), value + "");
            });
        }

        hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemCustomize.class);
        if (hasItem != null && hasItem) {
            for (HGFastItemCustomizeData t : hgFastDataUtils.getCustomizeItemTag()) {
                try {
                    addItemViewDelegate(t.getItemType(), t.getItemClass().newInstance());
                } catch (Exception ignored) {

                }
            }
        }

        hasItem = hgFastDataUtils.getAnnotationTag().get(HGFastItemGroup.class);
        if (hasItem != null && hasItem) {
            addItemViewDelegate(ItemType.ItemGroup.getValue(), itemHGFastItemGroup = new ItemHGFastItemGroup(bean, hgFastDataUtils.getFieldTag()));
        }
    }

    /**
     * 初始化数据
     * 内部调用，用户禁止调用
     *
     * @param temp temp
     */
    void initData(ArrayList<CommonBean> temp, HashMap<Integer, String> fieldTag) {

        hgFastDataUtils = new HGFastDataUtils();
        hgFastDataUtils.setFieldTag(fieldTag);

        for (CommonBean t : temp) {
            t.setOnlyRead(bean.getOnlyReadItem(t.getItemId()));
            hgFastDataUtils.getItemTag().put(t.getItemId(), t);

            if (t instanceof HGFastItemDateData) {
                hgFastDataUtils.getAnnotationTag().put(HGFastItemDate.class, true);
            } else if (t instanceof HGFastItemNumberData) {
                hgFastDataUtils.getAnnotationTag().put(HGFastItemNumber.class, true);
            } else if (t instanceof HGFastItemWordData) {
                hgFastDataUtils.getAnnotationTag().put(HGFastItemWord.class, true);
            } else if (t instanceof HGFastItemFileData) {
                hgFastDataUtils.getAnnotationTag().put(HGFastItemFile.class, true);
            } else if (t instanceof HGFastItemCustomizeData) {
                hgFastDataUtils.getAnnotationTag().put(HGFastItemCustomize.class, true);
            }
        }

        refreshItemViewDelegate();
        refreshData(temp);
    }

    public void setBaseUI(BaseUI baseUI) {
        if (itemHGFastItemWord != null) {
            itemHGFastItemWord.setBaseUI(baseUI);
        }
        if (itemHGFastItemFile != null) {
            itemHGFastItemFile.setBaseUI(baseUI);
        }
        if (itemHGFastItemDate != null) {
            itemHGFastItemDate.setBaseUI(baseUI);
        }
        if (itemHGFastItemNumber != null) {
            itemHGFastItemNumber.setBaseUI(baseUI);
        }
        if (itemHGFastItemGroup != null) {
            itemHGFastItemGroup.setBaseUI(baseUI);
        }
    }

    public void setOnHGFastItemClickListener(OnHGFastItemClickListener onHGFastItemClickListener) {
        if (itemHGFastItemWord != null) {
            itemHGFastItemWord.setOnHGFastItemClickListener(onHGFastItemClickListener);
        }
        if (itemHGFastItemFile != null) {
            itemHGFastItemFile.setOnHGFastItemClickListener(onHGFastItemClickListener);
        }
        if (itemHGFastItemDate != null) {
            itemHGFastItemDate.setOnHGFastItemClickListener(onHGFastItemClickListener);
        }
        if (itemHGFastItemGroup != null) {
            itemHGFastItemGroup.setOnHGFastItemClickListener(onHGFastItemClickListener);
        }
    }

    public void refreshHGFastItem(int id) {

        ArrayList<CommonBean> temp = hgFastDataUtils.resolveHGFastItemData(bean);
        hgFastDataUtils.removeOverItems(temp);
        int position = -1;
        int i = 0;

        for (CommonBean t : temp) {
            if (t instanceof HGFastItemGroupData) {
                HGFastItemGroupData groupData = t.getData();
                int[] ids = groupData.getGroupItemIds();
                boolean flag = false;
                for (int p : ids) {
                    if (p == id) {
                        position = i;
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    break;
                }
            } else {
                if (t.getItemId() == id) {
                    position = i;
                    break;
                }
            }

            i++;
        }

        if (position != -1) {
            refreshData(temp, position);
        } else {
            refreshData(temp);
        }
    }

    public void refreshHGFastItem() {
        ArrayList<CommonBean> temp = hgFastDataUtils.resolveHGFastItemData(bean);
        hgFastDataUtils.removeOverItems(temp);
        refreshData(temp);
    }

    /**
     * 解析对话框返回的数据
     *
     * @param code     code
     * @param backData backData
     */
    public void resolveOnDialogClick(int code, Bundle backData) {

        String fieldName = hgFastDataUtils.getFieldTag().get(code);

        if (!TextUtils.isEmpty(fieldName)) {
            int position = backData.getInt(HGParamKey.Position.getValue(), -100);
            long date = backData.getLong(HGParamKey.DateTimeInMillis.getValue(), -101L);
            String value = "";

            if (position != -100) {
                CommonBean item = hgFastDataUtils.getItemTag().get(code);

                if (item instanceof HGFastItemWordData) {
                    HGFastItemWordData temp = item.getData();

                    if (temp.getSingleChoiceMode() == SingleChoiceMode.Network) {
                        ArrayList<Object> tempList = (ArrayList<Object>) item.getObj(HGParamKey.ListData.getValue());

                        if (tempList != null) {
                            bean.addObj(temp.getItemId() + "", tempList.get(position));
                        }
                    }

                    value = HGFastDataUtils.getSingleChoiceItemCheckedValue(temp.getSingleChoiceItem(), position) + "";
                }
            } else if (date != -101L) {
                value = date + "";
            } else {
                value = backData.getString(HGParamKey.InputValue.getValue(), "");
            }

            setBeanValue(fieldName, value);
            refreshHGFastItem(code);
        }
    }

    /**
     * 解析界面跳转返回的数据
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param backData    backData
     */
    public void resolveOnActivityResult(Activity activity, int requestCode, int resultCode, Intent backData) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case HGFastFileSelectorUtils.REQUEST_CODE_OPEN_CAMERA:
                    if (itemHGFastItemFile.hgFastFileSelectorUtils.systemAppUtils.onActivityResultForTakePhoto(activity)) {
                        Media media = new Media();
                        media.setFile(itemHGFastItemFile.hgFastFileSelectorUtils.systemAppUtils.getCameraPhotoFile());

                        ArrayList<Media> medias = bean.getMedia().get(itemHGFastItemFile.clickItemId);
                        if (medias == null) {
                            medias = new ArrayList<>();
                        }
                        medias.add(media);

                        bean.getMedia().put(itemHGFastItemFile.clickItemId, medias);
                        refreshHGFastItem(itemHGFastItemFile.clickItemId);
                    }
                    break;
                case HGFastFileSelectorUtils.REQUEST_CODE_OPEN_ALBUM:
                    ArrayList<String> photos = itemHGFastItemFile.hgFastFileSelectorUtils.systemAppUtils.onActivityResultForCheckPhotos(backData);

                    if (photos != null) {
                        ArrayList<Media> medias = bean.getMedia().get(itemHGFastItemFile.clickItemId);
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

                        bean.getMedia().put(itemHGFastItemFile.clickItemId, medias);
                        refreshHGFastItem(itemHGFastItemFile.clickItemId);
                    }
                    break;
            }
        }
    }

    /**
     * 解析意图返回的数据
     *
     * @param event event
     */
    public void resolveOnEventUI(Event event) {
        switch (event.getEventActionCode()) {
            case HGEventActionCode.FILE_SELECTOR:
                File file = (File) event.getData().getSerializable(HGParamKey.ObjData.getValue());
                HashMap<String, File> files = (HashMap<String, File>) event.getData().getSerializable(HGParamKey.ListData.getValue());

                if (file != null) {
                    ArrayList<Media> medias = bean.getMedia().get(itemHGFastItemFile.clickItemId);
                    if (medias == null) {
                        medias = new ArrayList<>();
                    }

                    Media media = new Media();
                    media.setFile(file);
                    medias.add(media);

                    bean.getMedia().put(itemHGFastItemFile.clickItemId, medias);
                    refreshHGFastItem(itemHGFastItemFile.clickItemId);
                } else if (files != null) {
                    ArrayList<Media> medias = bean.getMedia().get(itemHGFastItemFile.clickItemId);
                    if (medias == null) {
                        medias = new ArrayList<>();
                    }

                    for (int i = 0; i < medias.size(); ) {
                        if (medias.get(i).getFile() != null) {
                            medias.remove(i);
                        } else {
                            i++;
                        }
                    }

                    Media media;
                    Set<String> keySet = files.keySet();
                    Iterator<String> keys = keySet.iterator();
                    String key;

                    while (keys.hasNext()) {
                        key = keys.next();

                        media = new Media();
                        media.setFile(files.get(key));

                        medias.add(media);
                    }

                    bean.getMedia().put(itemHGFastItemFile.clickItemId, medias);
                    refreshHGFastItem(itemHGFastItemFile.clickItemId);
                }
                break;
            case HGEventActionCode.REMOVE_IMAGE:
                int position = event.getData().getInt(HGParamKey.Position.getValue(), -1);
                if (position > -1 && position < bean.getMedia().get(itemHGFastItemFile.clickItemId).size()) {
                    bean.getMedia().get(itemHGFastItemFile.clickItemId).remove(position);
                    refreshHGFastItem(itemHGFastItemFile.clickItemId);
                }
                break;
        }
    }

    /**
     * 自动检查必填项
     *
     * @return true 检查通过，可进行下一步操作 false 检查不通过，有必填项未填
     */
    public boolean checkNotEmptyItem() {

        ArrayList<CommonBean> temp = new ArrayList<>(mData);
        boolean isNotEmpty;
        int contentCount;
        ItemMode itemMode;
        String label;

        for (CommonBean p : temp) {
            isNotEmpty = false;
            contentCount = 0;
            itemMode = null;
            label = "";

            if (p instanceof HGFastItemWordData) {
                HGFastItemWordData item = p.getData();
                isNotEmpty = item.isNotEmpty();
                contentCount = TextUtils.isEmpty(item.getContent()) ? 0 : item.getContent().length();
                itemMode = item.getItemMode();
                label = item.getLabel();
            } else if (p instanceof HGFastItemFileData) {
                HGFastItemFileData item = p.getData();
                isNotEmpty = item.isNotEmpty();
                contentCount = item.getItemMedia() == null ? 0 : item.getItemMedia().size();
                itemMode = item.getItemMode();
                label = item.getLabel();
            }

            if (isNotEmpty && contentCount == 0) {
                String tips;

                switch (itemMode) {
                    case Input:
                        tips = context.getString(R.string.please_input_content);
                        break;
                    case SingleChoice:
                        tips = context.getString(R.string.please_choose_content);
                        break;
                    case File:
                        tips = context.getString(R.string.please_get_content);
                        break;
                    default:
                        tips = context.getString(R.string.please_other_content);
                        break;
                }

                tips = String.format(tips, label);
                t.warning(tips);

                return false;
            }
        }

        return true;
    }

    private void setBeanValue(String fieldName, String value) {

        Class<?> type = ReflectUtils.getFieldType(bean, fieldName);

        if (!TextUtils.isEmpty(value)) {
            if (type == String.class) {
                ReflectUtils.setObjValue(bean, fieldName, value);
            } else if (type == int.class || type == Integer.class) {
                ReflectUtils.setObjValue(bean, fieldName, Integer.valueOf(value));
            } else if (type == long.class || type == Long.class) {
                ReflectUtils.setObjValue(bean, fieldName, Long.valueOf(value));
            }
        } else {
            ReflectUtils.setObjValue(bean, fieldName, value);
        }
    }

}
