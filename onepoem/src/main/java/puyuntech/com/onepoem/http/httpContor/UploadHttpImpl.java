package puyuntech.com.onepoem.http.httpContor;

import android.widget.Toast;

import com.nicodelee.utils.BitmapHelper;
import com.nicodelee.utils.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.UploadHttp;


/**
 * @作者 周翔
 * @创建时间 2016/2/5 0005
 * @描述 诗词相关接口
 **/
public class UploadHttpImpl extends BaseHttpImpl implements UploadHttp {

    private static UploadHttpImpl mHttpImpl = new UploadHttpImpl();//单例的接口处理类

    private UploadHttpImpl() {
    }

    /**
     * 获取接口处理类
     *
     * @return
     */
    public static UploadHttpImpl getMHttpImpl() {
        return mHttpImpl;
    }


    @Override
    public RequestParams uploadFile(String type, String path, final HttpAfterExpand afterHttp) {
        RequestParams params = new RequestParams(URLUtils.UPLOAD);
        params.setMultipart(true);
        params.addBodyParameter("type", type);//必须用addBodyParameter，否则文件加不上
        params.addBodyParameter("photo_list", new File(BitmapHelper.compressBitmap(path, 400, 400, false)));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final Result resultBean = JsonUtils.readValue(result, Result.class);
                String code = resultBean.getCode();
                switch (Integer.valueOf(code)) {
                    case URLUtils.RESULT_SUCCESS:
                        afterHttp.afterSuccess(resultBean);
                    default:
                        afterHttp.afterError(null);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                afterHttp.afterError(null);
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                afterHttp.afterError(null);
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                afterHttp.afferHttp();

            }
        });
        return params;
    }


}
