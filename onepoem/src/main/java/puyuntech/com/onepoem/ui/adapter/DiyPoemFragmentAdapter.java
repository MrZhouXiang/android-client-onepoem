package puyuntech.com.onepoem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nicodelee.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.model.DiyPoemMod;
import puyuntech.com.onepoem.model.EditMod;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DiyPoemFragmentAdapter extends BaseQuickAdapter<DiyPoemMod> {
    public DiyPoemFragmentAdapter(Context context) {
        super(context, R.layout.find_item, new ArrayList<DiyPoemMod>());
    }


    @Override
    protected void convert(BaseViewHolder helper, DiyPoemMod item) {
//        List<EditMod> contentList =  item.getContentList();
        //获取所有图片地址
        List<EditMod> contentImgList = item.getContentListByType(EditMod.IMG);
        helper.setText(R.id.tweetName, item.getTitle())
                .setVisible(R.id.content_imgs_rv, !ListUtils.isEmpty(contentImgList))
                .setImageUrl(R.id.tweetAvatar, URLUtils.DEFAULT_DIY_PATH + item.getUrl(), R.mipmap.ic_launcher);

        if (!ListUtils.isEmpty(contentImgList)) {
            //展示图片,列表
            RecyclerView mRecyclerView = helper.getView(R.id.content_imgs_rv);
            DiyPoemImgAdapter adapter = new DiyPoemImgAdapter(mContext, contentImgList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(adapter);

        }
    }


}
