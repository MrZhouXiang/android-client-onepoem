package puyuntech.com.onepoem.ui.activity.find;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicodelee.utils.HandlerUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.utils.UIHandler;
import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.Impl.ActivityDirector;
import puyuntech.com.onepoem.model.EditMod;
import puyuntech.com.onepoem.presenter.find.PublishDiyPoemPresenter;
import puyuntech.com.onepoem.ui.adapter.EditAdapter;

/**
 * 诗词发布
 */
@ContentView(R.layout.activity_publish_diy_poem)
public class PublishDiyPoemActivity extends ActivityDirector {

    @ViewInject(R.id.content_rv)
    RecyclerView content_rv;
    private EditAdapter mQuickAdapter;
    List<EditMod> list;


    @Event({R.id.img_add_iv})
    private void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.img_add_iv:
                addOnePic();
                break;
            default:
                break;
        }
    }

    /**
     * 添加一张图片
     */
    private void addOnePic() {
        showCameraChoose();
    }


    @Override
    public Object getValue(Enum type) {
        return null;
    }

    @Override
    public void updateUI(Object params, Enum type) {
        PublishDiyPoemPresenter.UpdateUIType type1 = (PublishDiyPoemPresenter.UpdateUIType) type;
        switch (type1) {
            case UPLOADIMG_SUCCESS:
                String path = (String) params;
                //增加本地图片展示
                List<EditMod> list_one = new ArrayList();
                EditMod mod3 = new EditMod();
                mod3.setContent(path);
                mod3.setItemType(EditMod.IMG);
                list_one.add(mod3);
                list_one.add(EditMod.MOD_TEXT);
                list.addAll(list_one);//本地数据更新
                mQuickAdapter.getData().addAll(list_one);//列表UI数据更新
                mQuickAdapter.notifyItemRangeChanged(mQuickAdapter.getItemCount() - 1, 2);
                HandlerUtil.getUIHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        content_rv.scrollToPosition(mQuickAdapter.getItemCount() - 1);//滑动到最低部
                    }
                },300);
                break;

            default:
                break;
        }


    }

    @Override
    public void initData() {
        mPresenter = new PublishDiyPoemPresenter(this);
        list = new ArrayList();
        list.add(EditMod.MOD_HEADER);//加一个头部
        list.add(EditMod.MOD_TEXT);//加一个文字编写
        initAdapter();

    }

    @Override
    public void initView() {
        content_rv.setLayoutManager(new LinearLayoutManager(this));
        content_rv.setAdapter(mQuickAdapter);
        content_rv.setHasFixedSize(true);

    }

    @Override
    public void setViewClickListener() {
        mQuickAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                EditMod mod = (EditMod) mQuickAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.del_iv:
                        list.remove(position);//删除数据
                        mQuickAdapter.remove(position);//删除UI
                        mQuickAdapter.notifyDataSetChanged();//跟新所有UI
                        break;
                }
            }
        });
    }


    @Override
    public void getDataLoc() {

    }

    @Override
    public void showTitle() {
        initTitle("发布诗文", R.mipmap.ic_return, "发布", -1);

    }

    @Override
    public void rightTextClick() {
        showShortToast("发布");
    }


    @Override
    public void showView() {
    }

    @Override
    public void getDataNet() {

    }

    private void initAdapter() {
        mQuickAdapter = new EditAdapter(this, list);
    }

    @Override
    public void afterSelect(String imagePath) {
        //上传照片
        ((PublishDiyPoemPresenter) mPresenter).uploadFile(imagePath);
    }
}
