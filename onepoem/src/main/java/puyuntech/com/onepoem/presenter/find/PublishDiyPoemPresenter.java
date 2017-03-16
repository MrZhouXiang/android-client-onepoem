package puyuntech.com.onepoem.presenter.find;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.nicodelee.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.http.httpContor.base.DiyPoemHttp;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.http.httpContor.base.UploadHttp;
import puyuntech.com.onepoem.model.DiyPoemMod;
import puyuntech.com.onepoem.model.PageParamModel;
import puyuntech.com.onepoem.model.PoemMod;
import puyuntech.com.onepoem.presenter.BasePresenter;

/**
 * @作者 Administrator
 * @创建时间 2016-05-16 下午 13:41
 * @描述 列表主导器
 * @修改时间 2016-05-16 下午 13:41
 * @修改描述
 * @修改者 Administrator
 **/
public class PublishDiyPoemPresenter extends BasePresenter {
    private static final int TOTAL_COUNTER = 100;//最大页数

    public static final int CONTENT_UPLOAD = 0;
    public static final int TITLE_UPLOAD = 1;

    public void uploadFile(final String imagePath) {

//            List l = new ArrayList();
//            l.add(imagePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpManager.getInstance().getHttpByMethod(UploadHttp.class).uploadFile("7", imagePath, new HttpAfterExpand() {
                        @Override
                        public void afferHttp() {

                        }

                        @Override
                        public void afterSuccess(Result resultBean) {
                            //解析数据，保存到本地
                            Map data = resultBean.getResult();
                            String[] fileNameList = JsonUtils
                                    .getObjectMapper().convertValue(
                                            data.get("fileNameList"),
                                            String[].class);
                            mIUpdateUIListener.updateUI(fileNameList[0], UpdateUIType.UPLOADIMG_SUCCESS);

                        }

                        @Override
                        public void afterFail(Result resultBean) {
                            //                showToast("上传失败:" + resultBean.errormsg);

                        }

                        @Override
                        public void afterError(Result resultBean) {

                        }
                    });
                } catch (HttpFactory.NotInterFaceException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 发布诗文
     *
     * @param mod
     */
    public void publishDiyPoem(DiyPoemMod mod) {
        try {
            HttpManager.getInstance().getHttpByMethod(DiyPoemHttp.class).publishDiyPoem(mod, new HttpAfterExpand() {
                @Override
                public void afferHttp() {

                }

                @Override
                public void afterSuccess(Result resultBean) {
                    //解析数据，保存到本地
                    showToast("发布成功");
                    mIUpdateUIListener.updateUI(null, UpdateUIType.PUBLISH_SUCCESS);

                }

                @Override
                public void afterFail(Result resultBean) {
                    //                showToast("上传失败:" + resultBean.errormsg);

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
     * 数据获取类型
     */
    public enum ValueGetType {
        GET_LAST,//最后一条数据
        CURRENT_PAGE_SIZE,//当前列表长度
        GET_TAG,//获取标签
    }

    public enum UpdateUIType {
        UPLOADIMG_SUCCESS,
        PUBLISH_SUCCESS,
        REFRESH,//刷新
        LOAD_MORE,//加载更多}
    }

    public PublishDiyPoemPresenter(Context context) {
        super(context);
    }

    public PublishDiyPoemPresenter(Fragment context) {
        super(context);
    }

    public void refresh() {
        //刷新，获取空数据，以便演示无数据的情况
        getDateNet(UpdateUIType.REFRESH);
//        mIUpdateUIListener.updateUI(DataServer.getSampleData(pageSize), UpdateUIType.REFRESH);
    }

    public void loadMore() {
        getDateNet(UpdateUIType.LOAD_MORE);
    }

    private void getDateNet(final UpdateUIType type) {
        DiyPoemMod mod = ((DiyPoemMod) mIUpdateUIListener.getValue(ValueGetType.GET_LAST));
        String id = mod == null ? "-1" : ((DiyPoemMod) mIUpdateUIListener.getValue(ValueGetType.GET_LAST)).getId();
        int size = pageSize;
        int page = type.equals(UpdateUIType.REFRESH) ? 0 : 1;
        String tag = ((String) mIUpdateUIListener.getValue(ValueGetType.GET_TAG));
        PageParamModel pageParamModel = new PageParamModel();
//动态代理获取接口
        try {
            HttpManager.getInstance().getHttpByMethod(DiyPoemHttp.class).
                    getDiyPoemList(pageParamModel, tag, new HttpAfterExpand() {
                        @Override
                        public void afferHttp() {
                            //                                showProgress(false);
                        }

                        @Override
                        public void afterSuccess(Result resultBean) {
                            //todo 成功，
                            //解析数据，保存到本地
                            Map data = resultBean.getResult();
                            DiyPoemMod[] modelNet = JsonUtils
                                    .getObjectMapper().convertValue(
                                            data.get("list"),
                                            DiyPoemMod[].class);
                            List list = new ArrayList();
                            list.addAll(Arrays.asList(modelNet));
                            mIUpdateUIListener.updateUI(list, type);
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