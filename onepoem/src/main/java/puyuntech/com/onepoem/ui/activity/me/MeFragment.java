package puyuntech.com.onepoem.ui.activity.me;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.FragmentDirector;
import puyuntech.com.onepoem.utils.GlideCircleTransform;
import puyuntech.com.onepoem.utils.MyUtils;

@ContentView(R.layout.fragment_me)
public class MeFragment extends FragmentDirector implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.avatar_iv)
    ImageView avatar_iv;

    @ViewInject(R.id.header_bg)
    ImageView header_bg;

    @Event({R.id.header_rl, R.id.sys_notice_iv, R.id.my_publish_tv, R.id.my_fav_tv, R.id.my_follow_tv, R.id.my_follow_tv, R.id.my_like_tv, R.id.shopping_tv})
    private void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.header_rl:
                showShortToast("个人中心");
                break;
            case R.id.sys_notice_iv:
                showShortToast("系统消息");
                break;
            case R.id.my_publish_tv:
                showShortToast("我的发布");
                break;
            case R.id.my_fav_tv:
                showShortToast("我的收藏");
                break;
            case R.id.my_follow_tv:
                showShortToast("我的关注");
                break;
            case R.id.my_like_tv:
                showShortToast("我的点赞");
                break;
            case R.id.shopping_tv:
                showShortToast("笔墨纸砚(商城)");
                break;
            default:
                break;
        }
    }

    public static MeFragment instantiation(int position) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }


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

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void showView() {
        //头像圆角
        Glide.with(context).load(R.drawable.default_avatar).crossFade().placeholder(R.drawable.default_img).transform(new GlideCircleTransform(getActivity())).into(avatar_iv);
        //高斯模糊背景
        MyUtils.blur(MyUtils.drawableToBitmap(getResources().getDrawable(R.drawable.default_avatar)), header_bg, 4);
    }

    @Override
    public void getDataNet() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onNetChange() {

    }

}
