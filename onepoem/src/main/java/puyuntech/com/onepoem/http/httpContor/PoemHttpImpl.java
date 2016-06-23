package puyuntech.com.onepoem.http.httpContor;

import android.widget.Toast;

import com.nicodelee.utils.JsonUtils;
import com.nicodelee.utils.L;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.PoemHttp;


/**
 * @作者 周翔
 * @创建时间 2016/2/5 0005
 * @描述 诗词相关接口
 **/
public class PoemHttpImpl extends BaseHttpImpl implements PoemHttp {

    private static PoemHttpImpl mHttpImpl = new PoemHttpImpl();//单例的接口处理类

    private PoemHttpImpl() {
    }

    /**
     * 获取接口处理类
     *
     * @return
     */
    public static PoemHttpImpl getMHttpImpl() {
        return mHttpImpl;
    }

    @Override
    public RequestParams getPoemList(String id, String size, String page, String dynasty_id, final HttpAfterExpand afterHttp) {
//        if (isProxy()) {
        RequestParams params = new RequestParams(URLUtils.GET_POEMLIST);
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("size", size);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("dynasty_id", dynasty_id);
        // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
        params.setCacheMaxAge(1000 * 60);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                // 得到缓存数据, 缓存过期后不会进入这个方法.
                // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                //
                // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                //
                // * 如果信任该缓存返回 true, 将不再请求网络;
                //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                //
                L.v("login-catch");
                final Result resultBean = JsonUtils.readValue(result, Result.class);
                String code = resultBean.getCode();
                switch (Integer.valueOf(code)) {
                    case URLUtils.RESULT_SUCCESS:
                        afterHttp.afterSuccess(resultBean);
                        return true; // true: 信任缓存数据, 不再发起网络请求; false不信任缓存数据.
                    default:
                        return false;// 本地保存的数据不正确则都需要重新发送网络请求
                }
            }

            @Override
            public void onSuccess(String result) {
                // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
                // 但是 onFinished 总会被调用.
                afterHttp.afferHttp();
                afterSuccess(result, afterHttp);
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

            }
        });
//        } else {
//            Toast.makeText(x.app(), "请使用getProxy()获取代理类", Toast.LENGTH_LONG).show();
//            L.e("请使用getProxy()获取代理类");
//        }
        return params;

    }


}
