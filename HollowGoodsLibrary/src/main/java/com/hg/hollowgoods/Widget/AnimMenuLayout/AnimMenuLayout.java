package com.hg.hollowgoods.Widget.AnimMenuLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hg.hollowgoods.R;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.BaseViewAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.bouncing_entrances.BounceInAnimator;
import com.hg.hollowgoods.Util.AnimUtils.AndroidAnimations.specials.out.TakingOffAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * @ClassName:
 * @Description:
 * @author: HollowGoods
 * @date: 2019年02月20日
 */
public class AnimMenuLayout extends FrameLayout {

    private View menu;

    private int menuId;
    private boolean isInit = false;
    private boolean hasMeasured = false;
    private boolean isAnim = false;

    public AnimMenuLayout(@NonNull Context context) {
        this(context, null);
    }

    public AnimMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AnimMenuLayout, 0, 0);
        menuId = attributes.getResourceId(R.styleable.AnimMenuLayout_menuId, 0);
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!hasMeasured) {
            hasMeasured = true;

            menu = findViewById(menuId);

            if (menu != null) {
                isInit = true;
                menu.setVisibility(GONE);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void openMenu(boolean isNeedAnim) {
        if (isInit) {
            if (!isAnim) {
                if (menu.getVisibility() != View.VISIBLE) {
                    menu.setVisibility(View.VISIBLE);
                }

                if (isNeedAnim) {
                    isAnim = true;

                    BaseViewAnimator baseViewAnimator = new BounceInAnimator();
                    baseViewAnimator.setTarget(menu);
                    baseViewAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnim = false;
                        }
                    });
                    baseViewAnimator.start();
                }
            }
        }
    }

    public void closeMenu(boolean isNeedAnim) {
        if (isInit) {
            if (!isAnim) {
                if (isNeedAnim) {
                    isAnim = true;

                    BaseViewAnimator baseViewAnimator = new TakingOffAnimator();
                    baseViewAnimator.setTarget(menu);
                    baseViewAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (menu.getVisibility() != View.GONE) {
                                menu.setVisibility(View.GONE);
                            }
                            isAnim = false;
                        }
                    });
                    baseViewAnimator.start();
                } else {
                    if (menu.getVisibility() != View.GONE) {
                        menu.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public View getMenu() {
        return menu;
    }

}
