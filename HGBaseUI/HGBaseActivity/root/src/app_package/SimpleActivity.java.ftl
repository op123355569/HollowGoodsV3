package ${packageName};

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseActivity;

/**
 * ${activityTitle}界面
 * <p>
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseActivity {

	@Override
    public Activity addToExitGroup() {
        return this;
    }
	
	@Override
    public int bindLayout() {
        return R.layout.${layoutName};
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_${activityToLayout(activityClass)});

        return null;
    }

    @Override
    public void setListener() {
        
    } 
	
}
