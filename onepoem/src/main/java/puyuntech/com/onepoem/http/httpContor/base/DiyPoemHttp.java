package puyuntech.com.onepoem.http.httpContor.base;

import org.xutils.http.RequestParams;

import puyuntech.com.onepoem.model.DiyPoemMod;

/**
 * @作者 zx
 * @创建时间 2016-04-19 下午 14:11
 * @描述 登录相关接口
 * @修改时间 2016-04-19 下午 14:11
 * @修改描述
 * @修改者 zx
 **/
public interface DiyPoemHttp {
    /**
     * 缓存
     * 列表数据
     *
     * @param afterHttp
     * @return
     */
    RequestParams publishDiyPoem(DiyPoemMod mod, final HttpAfterExpand afterHttp);


}
