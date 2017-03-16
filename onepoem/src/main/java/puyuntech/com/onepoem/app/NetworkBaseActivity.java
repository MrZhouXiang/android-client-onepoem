package puyuntech.com.onepoem.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * Created by zhoux on 2017/3/15.
 * 注释：4. 定义网络请求Activity的公共基类
 */

public class NetworkBaseActivity extends BaseAct {
    public ProgressDialog pd;
    public final long RETRY_TIMES = 1;
    public boolean showLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void setLoadingFlag(boolean show) {
        showLoading = show;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * 添加等待框
     *
     * @return Transformer
     */
    public <T> FlowableTransformer<T, T> flowableLoading() {
        return upstream -> upstream.doOnSubscribe(subscription -> startLoading())
                .doOnComplete(this::stopLoading)
                .doOnError(throwable -> {
                    if (!(throwable instanceof BoBoCustomException)) {
                        NetworkBaseActivity.this.stopLoading();
                    }
                })
                .onErrorResumeNext(Flowable.empty());
    }

    public void startLoading() {
        if (pd == null) {
            pd = new ProgressDialog(this);
        }
        if (this.isFinishing() || pd.isShowing()) return;
        pd.show();
    }

    public void stopLoading() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    private ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            listCompositeDisposable.add(disposable);
        }
    }

    protected void reDisposable(Disposable disposable) {
        if (disposable != null) {
            listCompositeDisposable.remove(disposable);
        }
    }

    protected void clear() {
        if (!listCompositeDisposable.isDisposed()) {
            listCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();
    }
}