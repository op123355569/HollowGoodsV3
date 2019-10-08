package com.hg.hollowgoods.Bean.Example

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.hg.hollowgoods.Widget.FloatingSearchView.suggestions.model.SearchSuggestion

/**
 * 示例14
 * Created by Hollow Goods 2018-03-22.
 */
class Ex14 : SearchSuggestion {

    private var mColorName: String? = null
    private var mIsHistory = false

    constructor(suggestion: String) {
        this.mColorName = suggestion.toLowerCase()
        this.mIsHistory = true
    }

    constructor(source: Parcel) {
        this.mColorName = source.readString()
        this.mIsHistory = source.readInt() != 0
    }

    fun setIsHistory(isHistory: Boolean) {
        this.mIsHistory = isHistory
    }

    fun getIsHistory(): Boolean {
        return this.mIsHistory
    }

    override fun getBody(): String? {
        return mColorName
    }

    @SuppressLint("ParcelCreator")
    val CREATOR: Parcelable.Creator<Ex14> = object : Parcelable.Creator<Ex14> {
        override fun createFromParcel(`in`: Parcel): Ex14 {
            return Ex14(`in`)
        }

        override fun newArray(size: Int): Array<Ex14?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mColorName)
        dest.writeInt(if (mIsHistory) 1 else 0)
    }

}