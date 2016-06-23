package puyuntech.com.onepoem.ui.activity;

import org.xutils.view.annotation.ContentView;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.presenter.WelcomePresenter;

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
//        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                ((WelcomePresenter)mPresenter).initNetData();
//            }
//        }, 1000);
    }
}
