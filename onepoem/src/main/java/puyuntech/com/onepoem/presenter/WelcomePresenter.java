package puyuntech.com.onepoem.presenter;

import android.app.Activity;
import android.content.Context;

import com.nicodelee.utils.HandlerUtil;
import com.nicodelee.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.base.DynastyHttp;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.model.DynastyMod;
import puyuntech.com.onepoem.ui.activity.main.MainActivity;

/**
 * @作者 Administrator
 * @创建时间 2016-05-16 下午 13:41
 * @描述 首页主导器
 * @修改时间 2016-05-16 下午 13:41
 * @修改描述
 * @修改者 Administrator
 **/
public class WelcomePresenter extends BasePresenter {

    public WelcomePresenter(Context context) {
        super(context);
    }


    public enum ShowType {
    }

    /**
     * 获取网络基本数据数据
     */
    public void initNetData() {
        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDynastyList();
            }
        }, 1000);
    }

    /**
     * 获取朝代列表
     */
    private void getDynastyList() {
        try {
            HttpManager.getInstance().getHttpByMethod(DynastyHttp.class).getDynastyList("0", "0", new HttpAfterExpand() {
                @Override
                public void afferHttp() {
                    //                                showProgress(false);
                }

                @Override
                public void afterSuccess(Result resultBean) {
                    //todo 成功，
                    //解析数据，保存到本地
                    Map data = resultBean.getResult();
                    DynastyMod[] modelNet = JsonUtils
                            .getObjectMapper().convertValue(
                                    data.get("list"),
                                    DynastyMod[].class);
                    ArrayList list = new ArrayList();
                    list.addAll(Arrays.asList(modelNet));
                    AppDataUtils.dynasty = list;
                    skipIntent(MainActivity.class, true);
                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                @Override
                public void afterFail(Result resultBean) {


                }

                @Override
                public void afterError(Result resultBean) {
                }
            });
        } catch (HttpFactory.NotInterFaceException e) {
            e.printStackTrace();
        }
    }
}
