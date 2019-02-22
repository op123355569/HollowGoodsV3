package com.hg.hollowgoods.UI.Activity.Example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.hg.hollowgoods.Bean.Example.Ex14;
import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.Constant.HGConstants;
import com.hg.hollowgoods.R;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.UI.Base.Message.Toast.t;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 搜索框界面
 * Created by HG
 */

public class Ex14Activity extends BaseActivity {

    private FloatingSearchView mSearchView;

    private List<Ex14> keys = new ArrayList<>();
    private List<Ex14> cache = new ArrayList<Ex14>() {
        {
            add(new Ex14("aaa"));
            add(new Ex14("bbb"));
            add(new Ex14("ccc"));
        }
    };
    private String lastKey = "Search...";

    @Override
    public int bindLayout() {
        return R.layout.activity_ex_14;
    }

    @Override
    public Activity addToExitGroup() {
        return this;
    }

    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        mSearchView = findViewById(R.id.floating_search_view);

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_activity_ex14);

        return null;
    }

    @Override
    public void setListener() {

        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {

            // 输入监听
            // 输入框无内容则清空历史记录列表
            // 否则进行匹配
            if (!oldQuery.equals("") && newQuery.equals("")) {
                mSearchView.clearSuggestions();
            } else {
                mSearchView.showProgress();


                keys.clear();
                for (int i = 0; i < cache.size(); i++) {
                    if (cache.get(i).getBody().contains(newQuery)) {
                        keys.add(cache.get(i));
                    }
                }
                mSearchView.swapSuggestions(keys);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(() -> mSearchView.hideProgress());
                    }
                }, 500);
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {

            @Override
            public void onFocus() {

                mSearchView.setSearchBarTitle("");
                mSearchView.setSearchHint(lastKey);
                // 获取到焦点
                keys.clear();
                for (int i = 0; i < cache.size(); i++) {
                    keys.add(cache.get(i));
                }
                mSearchView.swapSuggestions(keys);
            }

            @Override
            public void onFocusCleared() {
                // 焦点消失
                mSearchView.setSearchBarTitle("");
                mSearchView.setSearchHint(lastKey);
            }
        });

        mSearchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
            // 历史列表绑定数据
            Ex14 ex14 = (Ex14) item;

            String textColor = "#323232";
            String textLight = "#00A0E9";

            if (ex14.getIsHistory()) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));

                Util.setIconColor(leftIcon, Color.parseColor(textColor));
                leftIcon.setAlpha(0.36f);
            } else {
                leftIcon.setAlpha(0.0f);
                leftIcon.setImageDrawable(null);
            }

            textView.setTextColor(Color.parseColor(textColor));
            String text = ex14.getBody()
                    .replaceFirst(mSearchView.getQuery(),
                            "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
            textView.setText(Html.fromHtml(text));
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                // 点击了历史记录列表
                Ex14 ex14 = (Ex14) searchSuggestion;
                lastKey = ex14.getBody();

                // 更新结果列表
                baseUI.baseDialog.showProgressDialog(null, "搜索中……", true, true, HGConstants.DEFAULT_CODE);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(() -> baseUI.baseDialog.closeDialog(HGConstants.DEFAULT_CODE));
                    }
                }, 2 * 1000);
            }

            @Override
            public void onSearchAction(String query) {

                if (!TextUtils.isEmpty(query)) {
                    // 按了回车
                    lastKey = query;

                    // 更新结果列表
                    baseUI.baseDialog.showProgressDialog(null, "搜索中……", true, true, HGConstants.DEFAULT_CODE);

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            runOnUiThread(() -> baseUI.baseDialog.closeDialog(HGConstants.DEFAULT_CODE));
                        }
                    }, 2 * 1000);
                }
            }
        });

        mSearchView.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.action_1) {
                t.showShortToast("1");
            } else if (item.getItemId() == R.id.action_2) {
                t.showShortToast("2");
            } else if (item.getItemId() == R.id.action_3) {
                t.showShortToast("3");
            }
        });

        mSearchView.setOnSuggestionsListHeightChanged(newHeight -> {

            // 历史记录列表长度变化
            // 结果列表位置也需变化
//                listView.setTranslationY(newHeight);
        });
    }

}
