<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/main_body_color">

    <com.hg.hollowgoods.Widget.CommonTitle.CommonTitleLayout
        android:id="@+id/commonTitleView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <com.hg.hollowgoods.Widget.HGStatusLayout
        android:id="@+id/hgStatusLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:id="@+id/view_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <!-- 主体内容在此填写 -->

        </LinearLayout>

        <com.hg.hollowgoods.Widget.FloatingSearchView.FloatingSearchView
            android:id="@+id/floating_search_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="gone"
            app:floatingSearch_close_search_on_keyboard_dismiss="true"
            app:floatingSearch_leftActionMode="showSearch"
            app:floatingSearch_menu="@menu/menu"
            app:floatingSearch_searchBarMarginLeft="16dp"
            app:floatingSearch_searchBarMarginRight="16dp"
            app:floatingSearch_searchBarMarginTop="16dp"
            app:floatingSearch_searchHint="@string/search"
            app:floatingSearch_showSearchKey="true"
            app:floatingSearch_suggestionsListAnimDuration="250"
            app:floatingSearch_viewTextColor="#323232" />

    </com.hg.hollowgoods.Widget.HGStatusLayout>

</android.support.design.widget.CoordinatorLayout>