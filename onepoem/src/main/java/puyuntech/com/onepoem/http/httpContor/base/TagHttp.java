package puyuntech.com.onepoem.http.httpContor.base;

import org.xutils.http.RequestParams;

/**
 * @作者 zx
 * @创建时间 2016-04-19 下午 14:11
 * @描述 登录相关接口
 * @修改时间 2016-04-19 下午 14:11
 * @修改描述
 * @修改者 zx
 **/
public interface TagHttp {
    /**
     * 缓存
     * 列表数据
     *
     * @param id   第一条id
     * @param size 大小
     * @return
     */
    RequestParams getTagList(String id, String size, final HttpAfterExpand afterHttp);

}
