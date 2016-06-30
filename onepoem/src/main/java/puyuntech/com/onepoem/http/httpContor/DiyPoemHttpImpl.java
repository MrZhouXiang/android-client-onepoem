package puyuntech.com.onepoem.http.httpContor;

import android.widget.Toast;

import com.nicodelee.utils.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import puyuntech.com.onepoem.http.httpContor.base.DiyPoemHttp;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.model.DiyPoemMod;


/**
 * @作者 周翔
 * @创建时间 2016/2/5 0005
 * @描述 诗词相关接口
 **/
public class DiyPoemHttpImpl extends BaseHttpImpl implements DiyPoemHttp {

    private static DiyPoemHttpImpl mHttpImpl = new DiyPoemHttpImpl();//单例的接口处理类

    private DiyPoemHttpImpl() {
    }

    /**
     * 获取接口处理类
     *
     * @return
     */
    public static DiyPoemHttpImpl getMHttpImpl() {
        return mHttpImpl;
    }


    @Override
    public RequestParams publishDiyPoem(DiyPoemMod mod, final HttpAfterExpand afterHttp) {
        RequestParams params = new RequestParams(URLUtils.PUBLISH_DIYPOEM);
        params.addBodyParameter("title",mod.getTitle());
        params.addBodyParameter("content",mod.getContent());
        params.addBodyParameter("url",mod.getUrl());
        params.addBodyParameter("user_id",mod.getUser_id());
        params.addBodyParameter("is_publish",mod.getIs_publish());
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
