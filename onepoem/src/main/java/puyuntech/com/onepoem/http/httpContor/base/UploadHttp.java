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
public interface UploadHttp {
    /**
     * 缓存
     * 列表数据
     *
     * @param type      类型
     * @param afterHttp
     * @return
     */
    RequestParams uploadFile(String type, String path, final HttpAfterExpand afterHttp);


}
