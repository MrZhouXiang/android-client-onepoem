package puyuntech.com.onepoem.ui.activity.find;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.model.DiyPoemMod;
import puyuntech.com.onepoem.ui.activity.ShareActivity;
import puyuntech.com.onepoem.ui.adapter.ContentShowAdapter;

@ContentView(R.layout.activity_diy_poem_detail)
public class DiyPoemDetailActivity extends ActivityDirector {
    DiyPoemMod mod;
    @ViewInject(R.id.icon_iv)
    ImageView icon_iv;
    @ViewInject(R.id.content_rv)
    RecyclerView content_rv;
    @ViewInject(R.id.author_tv)
    TextView author_tv;

    ContentShowAdapter adapter;
    List contentList;

    @Override
    public Object getValue(Enum type) {
        return null;
    }

    @Override
    public void updateUI(Object params, Enum type) {

    }

    @Override
    public void initData() {
        contentList = new ArrayList();
    }

    @Override
    public void initView() {
        content_rv.setLayoutManager(new LinearLayoutManager(this));
        content_rv.setHasFixedSize(true);
    }

    @Override
    public void setViewClickListener() {

    }


    @Override
    public void getDataLoc() {
        mod = (DiyPoemMod) getIntent().getSerializableExtra("model");
        contentList = mod.getContentList();
        adapter = new ContentShowAdapter(this, contentList);
        content_rv.setAdapter(adapter);

    }

    @Override
    public void showTitle() {
        initTitle(mod.getTitle(), R.mipmap.ic_return, null, R.mipmap.ic_search);

    }

    @Override
    public void showView() {
        Glide.with(this).load( URLUtils.DEFAULT_DIY_PATH + mod.getUrl()).crossFade().into(icon_iv);//图片展示
        author_tv.setText("----" + StringUtils.getNotNullStr(mod.getPen_name()));
        //展示详情信息
        showContent();
    }

    private void showContent() {

    }

    @Override
    public void getDataNet() {

    }

    @Override
    public void rightImgClick() {
        ShareActivity.gotoShare(DiyPoemDetailActivity.this, "1");
    }
}
