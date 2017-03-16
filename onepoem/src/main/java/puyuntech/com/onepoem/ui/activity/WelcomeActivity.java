package puyuntech.com.onepoem.ui.activity;

import android.app.Activity;

import com.nicodelee.utils.HandlerUtil;
import com.nicodelee.utils.JsonUtils;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.httpnew.BaseComApi;
import puyuntech.com.onepoem.httpnew.RetroFactory;
import puyuntech.com.onepoem.httpnew.api.TagService;
import puyuntech.com.onepoem.model.TagMod;
import puyuntech.com.onepoem.presenter.WelcomePresenter;
import puyuntech.com.onepoem.ui.activity.main.MainActivity;

/**
 * 欢迎页
 */
@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends ActivityDirector {


    @Override
    public Object getValue(Enum type) {
        return null;
    }

    @Override
    public void updateUI(Object params, Enum type) {

    }

    @Override
    public void initData() {
        mPresenter = new WelcomePresenter(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewClickListener() {

    }

    @Override
    public void getDataLoc() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showView() {

    }

    @Override
    public void getDataNet() {
        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                ((WelcomePresenter)mPresenter).initNetData();
                getTagList2();
            }
        }, 1000);
    }

    private Disposable getTagList2() {
//        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getTagList2();
        return RetroFactory.getInstance().create(TagService.class).getList().compose(BaseComApi.background()).doOnError(Throwable::printStackTrace)
                .doOnNext(baseEntity -> {
                    if (baseEntity.isSuccess()) {
                        List<TagMod> list = baseEntity.getData();
                        AppDataUtils.tags.clear();
                        AppDataUtils.tags.addAll(list);
                        skipIntent(MainActivity.class, true);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        baseEntity.showMsg(WelcomeActivity.this);
                    }
                })
                .compose(flowableLoading())//进度条展示
                .subscribe();
    }
//        }, 1000);


//    }
}
