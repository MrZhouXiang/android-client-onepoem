package puyuntech.com.onepoem.httpnew.api;


import java.util.List;

import io.reactivex.Flowable;
import puyuntech.com.onepoem.httpnew.BaseEntity;
import puyuntech.com.onepoem.model.PoemMod;
import retrofit2.http.GET;

/**
 * Created by zhoux on 2017/3/15.
 * 注释：接口
 */

public interface PoemService {
    @GET("poem/getList")
    Flowable<BaseEntity<List<PoemMod>>> getList();

}
