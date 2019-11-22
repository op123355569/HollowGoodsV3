package ${packageName}.Contract;

import com.hg.hollowgoods.UI.Base.MVP.IBaseModel;
import com.hg.hollowgoods.UI.Base.MVP.IBaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * ${activityTitle}协议层
 * <p>
 * Created by Hollow Goods on ${.now?string["yyyy-MM-dd"]}
 */

public class ${contractName} {

	public interface Model extends IBaseModel {
        void getData(Map<String, Object> request);
    }

    public interface View extends IBaseView {

        void getDataSuccess(ArrayList<Object> tempData);

        void getDataError(Object msg);

        void getDataFinish();
    }

    public interface Presenter {
        void getData(int pageIndex, int pageSize, String searchKey);
    }
	
}
