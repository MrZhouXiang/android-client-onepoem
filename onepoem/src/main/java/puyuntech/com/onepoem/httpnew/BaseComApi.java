package puyuntech.com.onepoem.httpnew;

import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Author    lingchen
 * Email     838878458@qq.com
 * Time      2016/11/4
 * Function  通用接口基类
 * 封装线程调度
 */

public class BaseComApi {

    /**
     * 后台线程执行同步，主线程执行异步操作
     *
     * @param <T> 数据类型
     * @return Transformer
     */
    public static <T> FlowableTransformer<T, T> background() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())  // 网络请求切换在io线程中调用
                .unsubscribeOn(Schedulers.io())// 取消网络请求放在io线程
                .observeOn(AndroidSchedulers.mainThread());// 观察后放在主线程调用;
    }


    /**
     * 后台线程执行同步，主线程执行异步操作
     * 并且拦截所有错误，不让app崩溃
     *
     * @param <T> 数据类型
     * @return Transformer
     */
    public static <T> ObservableTransformer<T, T> backgroundObservable() {
        return upstream -> upstream.subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.<T>empty());
    }
}
