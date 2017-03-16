package puyuntech.com.onepoem.http.httpContor.base;

import org.xutils.http.RequestParams;

import puyuntech.com.onepoem.model.PageParamModel;

/**
 * @作者 zx
 * @创建时间 2016-04-19 下午 14:11
 * @描述 登录相关接口
 * @修改时间 2016-04-19 下午 14:11
 * @修改描述
 * @修改者 zx
 **/
public interface PoemHttp {
    /**
     * 缓存
     * 列表数据
     *
     * @param id   第一条id
     * @param size 大小
     * @param page 刷新加载更多控制 刷新 -1  首次0  更多1
     * @return
     */
//    RequestParams getPoemList(String id, String size, String page, String dynasty_id, final HttpAfterExpand afterHttp);

    RequestParams getPoemList(PageParamModel page,String dynasty_id , final HttpAfterExpand afterHttp);

}
