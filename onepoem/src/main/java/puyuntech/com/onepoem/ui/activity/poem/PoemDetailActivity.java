package puyuntech.com.onepoem.ui.activity.poem;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.model.PoemMod;
import puyuntech.com.onepoem.ui.activity.ShareActivity;

@ContentView(R.layout.activity_poem_detail)
public class PoemDetailActivity extends ActivityDirector {
    PoemMod mod;

    @ViewInject(R.id.icon_iv)
    ImageView icon_iv;
    @ViewInject(R.id.content_tv)
    TextView content_tv;
    @ViewInject(R.id.author_tv)
    TextView author_tv;

    @Override
    public Object getValue(Enum type) {
        return null;
    }

    @Override
    public void updateUI(Object params, Enum type) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewClickListener() {

    }


    @Override
    public void getDataLoc() {
        mod = (PoemMod) getIntent().getSerializableExtra("model");
    }

    @Override
    public void showTitle() {
        initTitle(mod.getTitle(), R.mipmap.ic_return, null, R.mipmap.ic_search);

    }

    @Override
    public void showView() {
        Glide.with(this).load(mod.getUrl()).crossFade().into(icon_iv);//图片展示
        content_tv.setText(StringUtils.getNotNullStr(mod.getContent()));
        author_tv.setText("----"+StringUtils.getNotNullStr(mod.getAuthor_name()));
    }

    @Override
    public void getDataNet() {

    }

    @Override
    public void rightImgClick() {
        ShareActivity.gotoShare(PoemDetailActivity.this, "1");
    }
}
