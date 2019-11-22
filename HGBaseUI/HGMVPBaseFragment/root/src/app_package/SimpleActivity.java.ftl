package ${packageName};

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hg.hollowgoods.Constant.HGCommonResource;
import com.hg.hollowgoods.UI.Base.BaseMVPFragment;

/**
 * ${activityTitle}碎片
 * <p>
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${activityClass} extends BaseMVPFragment<${presenterName}> implements ${contractName}.View {
	
	@Override
    public int bindLayout() {
        return R.layout.${layoutName};
    }

    @Nullable
    @Override
    public Object initView(View view, Bundle savedInstanceState) {

        baseUI.setCommonTitleStyleAutoBackground(HGCommonResource.BACK_ICON, R.string.title_fragment_${classToResource(activityClass)});

        return null;
    }

    @Override
    public void setListener() {
        
    }
	
	@Override
    public ${presenterName} createPresenter() {
        return new ${presenterName}(getActivity());
    }
	
}
