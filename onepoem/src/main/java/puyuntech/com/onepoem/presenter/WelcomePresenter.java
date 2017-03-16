package puyuntech.com.onepoem.presenter;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;

import com.nicodelee.utils.HandlerUtil;
import com.nicodelee.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.base.DynastyHttp;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.http.httpContor.base.TagHttp;
import puyuntech.com.onepoem.httpnew.BaseComApi;
import puyuntech.com.onepoem.httpnew.RetroFactory;
import puyuntech.com.onepoem.httpnew.api.TagService;
import puyuntech.com.onepoem.model.DynastyMod;
import puyuntech.com.onepoem.model.PageParamModel;
import puyuntech.com.onepoem.model.TagMod;
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
                getTagList();

            }
        }, 1000);
    }

//    private Disposable getTagList2() {
//        return RetroFactory.getInstance().create(TagService.class).getList().compose(BaseComApi.background()).doOnError(Throwable::printStackTrace)
//                .doOnNext(baseEntity -> {
//                    if (baseEntity.isSuccess()) {
//                        List<TagMod> list = baseEntity.getData();
////                        holder.getUserNameTv().setText("长度："+list.size());
//                    } else {
//                        baseEntity.showMsg(context);
//                    }
//                })
//                .compose(flowableLoading())//进度条展示
//                .subscribe();
//
//    }

    /**
     * 获取系统标签列表
     */
    private void getTagList() {
        try {
            PageParamModel pageParamModel = new PageParamModel();

            HttpManager.getInstance().getHttpByMethod(TagHttp.class).getTagList(pageParamModel, new HttpAfterExpand() {
                @Override
                public void afferHttp() {
                    //                                showProgress(false);
                }

                @Override
                public void afterSuccess(Result resultBean) {
                    //todo 成功，
                    //解析数据，保存到本地
                    Map data = resultBean.getResult();
                    TagMod[] modelNet = JsonUtils
                            .getObjectMapper().convertValue(
                                    data.get("list"),
                                    TagMod[].class);
                    ArrayList list = new ArrayList();
                    list.addAll(Arrays.asList(modelNet));
                    AppDataUtils.tags = list;
                    getDynastyList();
//                    skipIntent(MainActivity.class, true);
//                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
