package puyuntech.com.onepoem.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.model.EditMod;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DiyPoemImgAdapter extends BaseQuickAdapter<EditMod> {
    public DiyPoemImgAdapter(Context context, List<EditMod> list) {
        super(context, R.layout.find_img_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, EditMod item) {
        helper.setImageUrl(R.id.img_show_iv, URLUtils.DEFAULT_DIY_PATH + item.getContent(),
                R.mipmap.ic_launcher);

    }


}
