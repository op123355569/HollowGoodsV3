package ${packageName};

import android.content.Context;

/**
 * ${activityTitle}数据层
 * <p>
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${modelName} implements ${contractName}.Model {

	private ${contractName}.View mView;
	private Context mContext;

    public ${modelName}(${contractName}.View mView, Context mContext) {
        this.mView = mView;
		this.mContext = mContext;
    }
	
	@Override
    public void detachView() {
        this.mView = null;
    }
	
	@Override
    public boolean isViewAttached() {
        return mView != null;
    }
	
}
