package puyuntech.com.onepoem.presenter.find;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.http.httpContor.base.UploadHttp;
import puyuntech.com.onepoem.presenter.BasePresenter;
import puyuntech.com.onepoem.presenter.DataServer;

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

    public void uploadFile(String imagePath) {
        try {
//            List l = new ArrayList();
//            l.add(imagePath);
            HttpManager.getInstance().getHttpByMethod(UploadHttp.class).uploadFile("diy_poem", imagePath, new HttpAfterExpand() {
                @Override
                public void afferHttp() {

                }

                @Override
                public void afterSuccess(Result resultBean) {
    //                Object data = resultBean.getData();
    //                Map map = JsonUtils
    //                        .getObjectMapper().convertValue(
    //                                data,
    //                                Map.class);
    //                String url = (String) map.get("image");
    //                AppDataUtils.user.avatar = url;
    //                Glide.with(getActivity()).load(path).into(image);
                    showToast("上传成功");

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
        CURRENT_PAGE_SIZE,//当前列表长度
    }

    public enum UpdateUIType {
        REFRESH,//刷新
        LOAD_MORE,//加载更多}
    }

    public PublishDiyPoemPresenter(Context context) {
        super(context);
    }

    public void refresh() {
        //刷新，获取空数据，以便演示无数据的情况
        mIUpdateUIListener.updateUI(DataServer.getSampleData(0), UpdateUIType.REFRESH);
    }

    public void loadMore() {
//        int currentPageSize = (int) mIUpdateUIListener.getValue(ValueGetType.CURRENT_PAGE_SIZE);
//        if (currentPageSize >= TOTAL_COUNTER) {
//            mIUpdateUIListener.updateUI(DataServer.getSampleData(5), UpdateUIType.LOAD_MORE);
//        } else {
//            mIUpdateUIListener.updateUI(DataServer.getSampleData(pageSize), UpdateUIType.LOAD_MORE);
//        }
    }


}