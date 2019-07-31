package ${packageName};

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hg.hollowgoods.Util.IP.InterfaceConfig;
import com.hg.hollowgoods.Util.XUtils.GetHttpDataListener;
import com.hg.hollowgoods.Util.XUtils.XUtils;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Map;

/**
 * ${activityTitle}数据层
 *
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

	@Override
    public void getData(Map<String, Object> request) {

        RequestParams params = new RequestParams(InterfaceConfig.getNowIPConfig().getRequestUrl("URL"));

        XUtils xUtils = new XUtils();
        xUtils.setGetHttpDataListener(new GetHttpDataListener() {
            @Override
            public void onGetSuccess(String result) {
                if (isViewAttached()) {
                    new Thread(() -> {
                        // TODO 数据处理的逻辑
                        ArrayList<Object> tempData = new ArrayList<>();

                        new Handler(Looper.getMainLooper()).post(() -> {
                            mView.getDataSuccess(tempData);
                            mView.getDataFinish();
                        });
                    }).start();
                }
            }

            @Override
            public void onGetError(Throwable ex) {
                if (isViewAttached()) {
                    mView.getDataError();
                    mView.getDataFinish();
                }
            }

            @Override
            public void onGetFinish() {

            }
        });
        xUtils.getHttpData(HttpMethod.GET, params);
    }
}
