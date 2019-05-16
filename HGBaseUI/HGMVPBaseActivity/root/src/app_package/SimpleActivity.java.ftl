package ${packageName};

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseMVPActivity;

/**
 * ${activityTitle}界面
 *
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseMVPActivity<${presenterName}> implements ${contractName}.View {

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
	
	@Override
    public ${presenterName} createPresenter() {
        return new ${presenterName}(this);
    }
	
}
