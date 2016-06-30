package puyuntech.com.onepoem.presenter.find;

import android.content.Context;

import com.nicodelee.utils.JsonUtils;

import java.util.Map;

import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.http.httpContor.base.DiyPoemHttp;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.http.httpContor.base.UploadHttp;
import puyuntech.com.onepoem.model.DiyPoemMod;
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
                            mIUpdateUIListener.updateUI(URLUtils.DEFAULT_DIY_PATH + fileNameList[0], UpdateUIType.UPLOADIMG_SUCCESS);

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
    }

    public enum UpdateUIType {
        UPLOADIMG_SUCCESS,
        PUBLISH_SUCCESS
    }

    public PublishDiyPoemPresenter(Context context) {
        super(context);
    }


}