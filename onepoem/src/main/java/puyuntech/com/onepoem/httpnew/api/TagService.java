package puyuntech.com.onepoem.httpnew.api;


import java.util.List;

import io.reactivex.Flowable;
import puyuntech.com.onepoem.httpnew.BaseEntity;
import puyuntech.com.onepoem.model.PoemMod;
import puyuntech.com.onepoem.model.TagMod;
import retrofit2.http.GET;

/**
 * Created by zhoux on 2017/3/15.
 * 注释：接口
 */

public interface TagService {
    @GET("tag/getList")
    Flowable<BaseEntity<List<TagMod>>> getList();

}
