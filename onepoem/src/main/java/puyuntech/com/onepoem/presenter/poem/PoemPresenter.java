package puyuntech.com.onepoem.presenter.poem;

import android.support.v4.app.Fragment;

import com.nicodelee.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import puyuntech.com.onepoem.http.httpContor.HttpManager;
import puyuntech.com.onepoem.http.httpContor.Result;
import puyuntech.com.onepoem.http.httpContor.base.HttpAfterExpand;
import puyuntech.com.onepoem.http.httpContor.base.HttpFactory;
import puyuntech.com.onepoem.http.httpContor.base.PoemHttp;
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
public class PoemPresenter extends BasePresenter {
    private static final int TOTAL_COUNTER = 100;//最大页数

    /**
     * 数据获取类型
     */
    public enum ValueGetType {
        GET_LAST,//最后一条数据
        CURRENT_PAGE_SIZE,//当前列表长度
        GET_DYNASTY_ID,//获取朝代id
    }

    public enum UpdateUIType {
        REFRESH,//刷新
        LOAD_MORE,//加载更多}
    }

    public PoemPresenter(Fragment context) {
        super(context);
    }

    public void refresh() {
        //刷新，获取空数据，以便演示无数据的情况
        getDateNet(UpdateUIType.REFRESH);
//        mIUpdateUIListener.updateUI(DataServer.getSampleData(pageSize), UpdateUIType.REFRESH);
    }

    public void loadMore() {
        getDateNet(UpdateUIType.LOAD_MORE);
//        int currentPageSize = (int) mIUpdateUIListener.getValue(ValueGetType.CURRENT_PAGE_SIZE);
//        if (currentPageSize >= TOTAL_COUNTER) {
//            mIUpdateUIListener.updateUI(DataServer.getSampleData(5), UpdateUIType.LOAD_MORE);
//        } else {
//            mIUpdateUIListener.updateUI(DataServer.getSampleData(pageSize), UpdateUIType.LOAD_MORE);
//        }
    }

    private void getDateNet(final UpdateUIType type) {
        PoemMod mod = ((PoemMod) mIUpdateUIListener.getValue(ValueGetType.GET_LAST));
        String id = mod == null ? "-1" : ((PoemMod) mIUpdateUIListener.getValue(ValueGetType.GET_LAST)).getId();
        int size = pageSize;
        int page = type.equals(UpdateUIType.REFRESH) ? 0 : 1;
        String dynasty_id = ((String) mIUpdateUIListener.getValue(ValueGetType.GET_DYNASTY_ID));
        //动态代理获取接口
        try {
            HttpManager.getInstance().getHttpByMethod(PoemHttp.class).
                    getPoemList(id, size + "", page + "", dynasty_id, new HttpAfterExpand() {
                        @Override
                        public void afferHttp() {
                            //                                showProgress(false);
                        }

                        @Override
                        public void afterSuccess(Result resultBean) {
                            //todo 成功，
                            List<Object> rightDataTem;
                            //解析数据，保存到本地
                            Map data = resultBean.getResult();
                            PoemMod[] modelNet = JsonUtils
                                    .getObjectMapper().convertValue(
                                            data.get("list"),
                                            PoemMod[].class);
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