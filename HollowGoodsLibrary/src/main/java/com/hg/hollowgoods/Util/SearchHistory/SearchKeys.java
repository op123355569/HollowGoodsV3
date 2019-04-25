package com.hg.hollowgoods.Util.SearchHistory;

import android.os.Parcel;

import com.hg.hollowgoods.Widget.FloatingSearchView.suggestions.model.SearchSuggestion;

/**
 * 搜索历史记录
 * Created by HG
 */

public class SearchKeys implements SearchSuggestion {

    private String mKey;
    private boolean mIsHistory = false;

    public SearchKeys(String suggestion) {
        this.mKey = suggestion.toLowerCase();
        this.mIsHistory = true;
    }

    public SearchKeys(Parcel source) {
        this.mKey = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mKey;
    }

    public static final Creator<SearchKeys> CREATOR = new Creator<SearchKeys>() {
        @Override
        public SearchKeys createFromParcel(Parcel in) {
            return new SearchKeys(in);
        }

        @Override
        public SearchKeys[] newArray(int size) {
            return new SearchKeys[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
