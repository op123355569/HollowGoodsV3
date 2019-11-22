package ${packageName};

import android.content.Context;

import com.hg.hollowgoods.UI.Base.MVP.BasePresenter;

/**
 * ${activityTitle}管理层
 * <p>
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${presenterName} extends BasePresenter<${contractName}.View, ${contractName}.Model> implements ${contractName}.Presenter {

	public ${presenterName}(Context mContext) {
        super(mContext);
    }

    @Override
    public void afterAttachView() {
        mModel = new ${modelName}(mView, mContext);
    }
	
}
