package ${packageName}.Model;

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

	@Override
    public void getData(Map<String, Object> request) {

        RequestParams params = RequestParamsHelper.builderJsonBodyRequestParam(
                InterfaceConfig.getNowIPConfig().getRequestUrl("URL"),
                null,
                null
        );

        new XUtils2.BuilderGetHttpData().setGetHttpDataListener(new GetHttpDataListener() {
            @Override
            public void onGetSuccess(String result) {
                if (isViewAttached()) {
                    ThreadPoolUtils.getService().execute(() -> {
                        // TODO 数据处理的逻辑
                        ArrayList<Object> tempData = new ArrayList<>();

                        new Handler(Looper.getMainLooper()).post(() -> {
                            mView.getDataSuccess(tempData);
                            mView.getDataFinish();
                        });
                    });
                }
            }

            @Override
            public void onGetError(Throwable ex) {
                if (isViewAttached()) {
                    mView.getDataError(R.string.network_error);
                    mView.getDataFinish();
                }
            }

            @Override
            public void onGetFinish() {

            }
        }).getHttpData(HttpMethod.GET, params);
    }
}
