package puyuntech.com.onepoem.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.model.PoemMod;
import puyuntech.com.onepoem.model.Status;
import puyuntech.com.onepoem.presenter.DataServer;
import puyuntech.com.onepoem.utils.GlideCircleTransform;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PoemFragmentAdapter extends BaseQuickAdapter<PoemMod> {
    public PoemFragmentAdapter(Context context) {
        super(context, R.layout.main_item,new ArrayList<PoemMod>());
    }


    @Override
    protected void convert(BaseViewHolder helper, PoemMod item) {
        helper.setText(R.id.tweetName, item.getTitle())
                .setText(R.id.tweetText, item.getContent())
                .setText(R.id.tweetDate, item.getAuthorName())
//                .setImageUrl(R.id.tweetAvatar, item.getUrl(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext))
                .setImageUrl(R.id.tweetAvatar, item.getUrl(), R.mipmap.ic_launcher, new GlideCircleTransform(mContext));
//                .setVisible(R.id.tweetRT, item.isRetweet())
//                .setOnClickListener(R.id.tweetAvatar, new OnItemChildClickListener())
//                .setOnClickListener(R.id.tweetName, new OnItemChildClickListener())
//                .linkify(R.id.tweetText);
    }


}
