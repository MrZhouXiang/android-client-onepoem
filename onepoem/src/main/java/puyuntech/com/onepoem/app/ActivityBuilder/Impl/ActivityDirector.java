package puyuntech.com.onepoem.app.ActivityBuilder.Impl;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;
import com.nicodelee.utils.T;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ActivityBuilder.BuildHelper;
import puyuntech.com.onepoem.app.ActivityBuilder.CamareHelper;
import puyuntech.com.onepoem.app.ActivityBuilder.InitBuilder;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.app.BaseAct;
import puyuntech.com.onepoem.app.MyActivityManager;
import puyuntech.com.onepoem.app.NetBroadcastReceiver;
import puyuntech.com.onepoem.presenter.BasePresenter;
import puyuntech.com.onepoem.presenter.IUpdateUIListener;
import puyuntech.com.onepoem.utils.GetPathFromUri4kitkat;
import puyuntech.com.onepoem.utils.NetWorkUtils;


/**
 * 作者 zx
 * 创建时间 2016/4/21 0021
 * 描述 抽象activity【导演者】类，所有activity需要继承他
 * 修改时间 2016/4/21 0021
 * 修改描述 基础activity抽象类，所有activity需要实现它
 * 修改者 zx
 **/
public abstract class ActivityDirector extends BaseAct implements BuildHelper, IUpdateUIListener, CamareHelper {
    @ViewInject(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewInject(R.id.title_tv)
    protected TextView title_tv;//标题
    @ViewInject(R.id.right_iv)
    protected ImageView right_iv;//右边的image按钮
    @ViewInject(R.id.right_tv)
    protected TextView right_tv;//右边的文字按钮
    @ViewInject(R.id.left_rl)
    protected RelativeLayout left_rl;//标题自定义左边控件
    @ViewInject(R.id.left_iv)
    protected ImageView left_iv;//左边的image按钮
    protected boolean isHasMore = true;
    protected int pageSize = AppDataUtils.pageSize;
    private InitBuilder initBuilder;//建造者
    protected BasePresenter mPresenter;//主导器
    public static final int REFRESH_FLAG = 0;
    public static final int LOAD_MORE_FLAG = 1;

    @Event({R.id.right_iv, R.id.right_tv, R.id.left_iv})
    private void clickEvent(final View view) {
        switch (view.getId()) {
            case R.id.left_iv:
                leftImgClick();
                break;
            case R.id.right_iv:
                rightImgClick();
                break;
            case R.id.right_tv:
                rightTextClick();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBuilder = new InitBuilderImpl();//建造者实现类
        initBuilder.initAct(this);//初始化方式1:初始化一个activity
        //网络状态监听
        NetBroadcastReceiver.mListeners.add(this);
        //管理activity
        MyActivityManager.getInstance().pushOneActivity(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //返回建设置事件
                homeBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * @param loadType     刷新or加载更多
     * @param getList      加载数据
     * @param adapter      需要刷新的adapter
     * @param mSwipeLayout loading界面
     */
    @Override
    public void refreshPage(int loadType, List getList, BaseQuickAdapter adapter, SwipeRefreshLayout mSwipeLayout, RecyclerView recyclerView) {
        if (loadType == REFRESH_FLAG) {
            isHasMore = true;
            if (ListUtils.isEmpty(getList)) {//无数据
                isHasMore = false;
                adapter.setNewData(new ArrayList());
                adapter.notifyDataChangedAfterLoadMore(false);
            } else if (ListUtils.getSize(getList) < pageSize) {//当前页有数据，没有更多
                isHasMore = false;
                adapter.setNewData(getList);
                adapter.openLoadMore(pageSize, true);
                adapter.notifyDataChangedAfterLoadMore(false);
            } else {//有更多数据
                adapter.setNewData(getList);
                adapter.openLoadMore(pageSize, true);
            }
        } else if (loadType == LOAD_MORE_FLAG) {
            if (ListUtils.isEmpty(getList)) {
                showToast("全部加载完毕");
                isHasMore = false;
                adapter.notifyDataChangedAfterLoadMore(false);
            } else {
                //加载更多
                adapter.notifyDataChangedAfterLoadMore(getList, true);
                //全部加载完毕
                if (ListUtils.getSize(getList) < pageSize) {
                    showToast("全部加载完毕");
                    isHasMore = false;
                    adapter.notifyDataChangedAfterLoadMore(false);
                }
            }

        }
        if (mSwipeLayout != null)
            mSwipeLayout.setRefreshing(false);
    }


    /**
     * 初始化头部
     *
     * @param title
     * @param showLeft 是否显示左边按钮
     */
    @Override
    public void initTitle(String title, boolean showLeft) {
        //标题展示
        if (showLeft) {
            initTitle(title, R.mipmap.ic_return, null, -1);
        } else {
            initTitle(title, -1, null, -1);

        }
    }

    /**
     * 初始化头部
     *
     * @param title    标题
     * @param showLeft 是否显示左边按钮
     * @param leftId   左边按钮id
     */
//    @Override
//    public void initTitle(String title, boolean showLeft, int leftId) {
//        initTitle(title, showLeft, leftId, -1);
//    }

    /**
     * 自定义左边按钮
     *
     * @param title
     * @param leftId
     * @param rightStr
     * @param rightId
     */
    @Override
    public void initTitle(String title, int leftId, String rightStr, int rightId) {
//        initTitle(title, false, -1);
        //标题展示
        if (title_tv != null) {
            title_tv.setText(title);
        }
        if (leftId != -1) {
            left_rl.setVisibility(View.VISIBLE);
            left_iv.setImageResource(leftId);
        } else {
            left_rl.setVisibility(View.GONE);

        }
        //返回按钮展示
        if (toolbar == null)
            return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(StringUtils.getNotNullStr(title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);// // 给左上角图标的左边加上一个返回的图标 。
        getSupportActionBar().setDisplayShowHomeEnabled(false);//使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为Android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
//        if (false) {
//            //需要展示左边图标
//            if (leftId != -1) {
//                getSupportActionBar().setHomeAsUpIndicator(leftId);
//            } else {
//                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_return);
//            }
//        }
        //需要展示右边文字
        if (rightStr != null) {
            right_tv.setVisibility(View.VISIBLE);
            right_iv.setVisibility(View.GONE);
            right_tv.setText(StringUtils.getNotNullStr(rightStr));
        } else if (rightId != -1) {        //需要展示右边图标
            right_iv.setVisibility(View.VISIBLE);
            right_tv.setVisibility(View.GONE);
            right_iv.setImageResource(rightId);
        } else {
            right_tv.setVisibility(View.GONE);
            right_iv.setVisibility(View.GONE);
        }

    }

    public static final int SELECT_PICTURE = 2000;
    public static final int SELECT_CAMER = 3000;
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    private int afterSelectType = -1;//选择照片之后的操作

    private void showCameraChoose() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                        } else {
//                            String status = Environment.getExternalStorageState();
//                            if (status.equals(Environment.MEDIA_MOUNTED)) {//判断是否有SD卡
                            doTakePhoto();// 用户点击了从照相机获取
//                            } else {
//                                showToast("没有SD卡");
//                            }
                        }
                    }
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE: {// 调用Gallery返回的
                    Uri selectedImage = data.getData();
                    afterSelect(GetPathFromUri4kitkat.getPath(this, selectedImage));
                    break;
                }
                case SELECT_CAMER: {// 照相机程序返回的
//                    Uri selectedImage = data.getData();
                    afterSelect(mCurrentPhotoFile.getPath());
//                    doCropPhoto(mCurrentPhotoFile);//剪裁

                    break;
                }
                case PHOTO_PICKED_WITH_DATA: {// 剪裁返回的
                    final Bitmap photo = data.getParcelableExtra("data");
                    // 下面就是显示照片了
                    System.out.println(photo);
                    //缓存用户选择的图片
//                    img = getBitmapByte(photo);
//                    mEditor.setPhotoBitmap(photo);
                    System.out.println("set new photo");
                    break;
                }
            }

        } else {
            showShortToast("请重新选择图片");
        }

    }

    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);

        } catch (Exception e) {
            Toast.makeText(this, "照片没找到",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Constructs an intent for image cropping. 调用图片剪辑程序
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }

    /*拍照的照片存储位置*/
    private static File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/onepoem");
    private File mCurrentPhotoFile;//照相机拍照得到的图片

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists()) {
                PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/onepoem");
            } else {
                PHOTO_DIR = new File(getApplication().getFilesDir() + "/onepoem");
            }
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, SELECT_CAMER);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "照片未找到",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    public void rightImgClick() {

    }

    @Override
    public void rightTextClick() {

    }


    @Override
    public void leftImgClick() {
        finish();
    }

    @Override
    public void homeBack() {
        finish();
    }

    @Override
    public void onNetChange() {
        if (NetWorkUtils.getNetworkState(this) == NetWorkUtils.NETWORN_NONE) {
            T.showLong(this, "网络不可以使用,请链接网络");
        } else {
            T.showLong(this, "网络可以使用:" + NetWorkUtils.getNetworkStrByState(NetWorkUtils.getNetworkState(this)));
        }
    }

    @Override
    public void afterSelect(String imagePath) {
        showShortToast(imagePath);
    }

    public int getAfterSelectType() {
        return afterSelectType;
    }

    public void setAfterSelectType(int afterSelectType) {
        this.afterSelectType = afterSelectType;
    }

    /**
     * 添加一张图片
     */
    public void addOnePic(int type) {
        setAfterSelectType(type);//设置图片类型
        showCameraChoose();//
    }
}
