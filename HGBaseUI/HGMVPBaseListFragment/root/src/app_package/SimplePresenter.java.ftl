package ${packageName};

import android.content.Context;

import com.hg.hollowgoods.UI.Base.MVP.BasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * ${activityTitle}管理层
 *
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

    @Override
    public void getData(int pageIndex, int pageSize, String searchKey) {

        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("searchKey", searchKey);

        mModel.getData(map);
    }
	
}
