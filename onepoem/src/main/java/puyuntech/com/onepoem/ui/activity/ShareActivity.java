/**
 * Project： PhotoPrint
 * Title: ShareActivity.java
 * Package: com.puyuntech.photoprint.ui.activity
 * Copyright: 2015 www.puyuntech.com Inc. All rights reserved.
 * version: V1.0
 */
package puyuntech.com.onepoem.ui.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.nicodelee.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.ShareConstant;

/**
 * 分享弹出框
 *
 * @author 周翔
 * @version [版本号, 2015-9-6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ContentView(R.layout.share_activity)
public class ShareActivity extends Activity implements PlatformActionListener, Callback {

    private static final int MSG_TOAST = 1;

    private static final int MSG_ACTION_CCALLBACK = 2;

    private static final int MSG_CANCEL_NOTIFY = 3;

    private String folderName;

    private String folderType;

    private String placeId;
    @ViewInject(R.id.share_weixin_friends_ll)
    protected LinearLayout share_weixin_friends_ll;//
    @ViewInject(R.id.share_weixin_friend_ll)
    protected LinearLayout share_weixin_friend_ll;//
//    @ViewInject(R.id.share_weibo_ll)
//    protected LinearLayout share_weibo_ll;//

    @ViewInject(R.id.share_qq_ll)
    protected LinearLayout share_qq_ll;//

    String goodsId;
    String image;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
        goodsId = getIntent().getStringExtra("noteId");
    }

//    @Event({R.id.share_weixin_friends_ll, R.id.share_weixin_friend_ll, R.id.share_weibo_ll, R.id.share_qq_ll})
//    public void clickEvent(View view) {
//        switch (view.getId()) {
//            case R.id.share_weixin_friends_ll:
//                shareWeixinFriends(view);
//                break;
//            case R.id.share_weixin_friend_ll:
//                shareWeixinFriend(view);
//                break;
//            case R.id.share_weibo_ll:
//                shareSina(view);
//                break;
//            case R.id.share_qq_ll:
//                shareQQ(view);
//                break;
//            default:
//                T.showShort(this, "id:" + view.getId());
//        }
//    }

    /**
     * 点击
     *
     * @param view
     */
    @Event({R.id.share_weixin_friends_ll, R.id.share_weixin_friend_ll,  R.id.share_qq_ll})
    private void clickEvent(final View view) {
        switch (view.getId()) {
            case R.id.share_weixin_friends_ll:
                shareWeixinFriends(view);
                break;
            case R.id.share_weixin_friend_ll:
                shareWeixinFriend(view);
                break;
//            case R.id.share_weibo_ll:
//                shareSina(view);
//                break;
            case R.id.share_qq_ll:
                shareQQ(view);
                break;
            default:
                T.showShort(this, "id:" + view.getId());
        }        finish();

    }

    /**
     * 分享盆友圈
     *
     * @param v
     * @see [类、类#方法、类#成员]
     */
    public void shareWeixinFriends(View v) {
        Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
        shareToThirdPlatform(plat);
    }

    /**
     * 分享好友
     *
     * @param v
     * @see [类、类#方法、类#成员]
     */
    public void shareWeixinFriend(View v) {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        shareToThirdPlatform(plat);
    }

    public void shareSina(View v) {
        Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
        shareToThirdPlatform(plat);
    }

    public void shareQQ(View v) {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        shareToThirdPlatform(plat);
    }

    /**
     * 空间
     *
     * @param v
     */
    public void shareQZone(View v) {
        Platform plat = ShareSDK.getPlatform(QZone.NAME);
        shareToThirdPlatform(plat);
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        overridePendingTransition(0, R.anim.activity_close);
    }


    /**
     * TODO 分享到第三方平台.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周翔date: 2015年6月4日 上午10:40:58
     *
     * @param plat
     */


    private void shareToThirdPlatform(Platform plat) {

//        if((goodsMod.getGoodsId()).equals(goodsId)){
//            title=goodsMod.getGoodsName();
//        }else{
//            title=ShareConstant.WECHATSHARETITLE;
//        }
//        if(goodsMod.equals(goodsId)){
//            image=goodsMod.getGoodsImage()[1].getMedium();
//        }
        if (plat == null) {
            return;
        }

        String name = plat.getName();
        if (plat.isValid()) {
            plat.removeAccount();
            ShareSDK.removeCookieOnAuthorize(true);
        }
        // if (name.equals("QQ"))
        // {
        // QQ.ShareParams qq = new QQ.ShareParams();
        // qq.title = ConstantEntity.WECHATSHARETITLE;
        // qq.titleUrl = "www.puyuntech.com";
        // plat.setPlatformActionListener(this);
        // // 关闭SSO授权
        // plat.SSOSetting(true);
        // plat.share(qq);
        // }
        ContentResolver resolver = ShareActivity.this.getContentResolver();
        if (name.equals("Wechat")) {//微信朋友
            Wechat.ShareParams Wechat = new Wechat.ShareParams();
            Wechat.title = ShareConstant.WECHATSHARETITLE;
            Wechat.text = ShareConstant.WECHATSHARETEXT;
            Wechat.shareType = Platform.SHARE_WEBPAGE;
            // 消息点击后打开的页面
            Wechat.url = ShareConstant.WECHATSHAREURL;
            Wechat.imageUrl = ShareConstant.WECHATSHAREIMAGEURL;
            plat.setPlatformActionListener(this);
            // 关闭SSO授权
            plat.SSOSetting(true);
            plat.share(Wechat);
        }
        if (name.equals("WechatMoments")) {//微信朋友圈
            Wechat.ShareParams wechatMoments = new Wechat.ShareParams();
            wechatMoments.title = ShareConstant.WECHATSHARETITLE;
            wechatMoments.text = ShareConstant.WECHATSHARETEXT;
            wechatMoments.shareType = Platform.SHARE_WEBPAGE;
            // 消息点击后打开的页面
            wechatMoments.url = ShareConstant.WECHATSHAREURL;
            wechatMoments.imageUrl = ShareConstant.WECHATSHAREIMAGEURL;
            // wechatMoments.imageData = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
            //  wechatMoments.imageData = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(imgarray.get(0).getMedium()));
            plat.setPlatformActionListener(this);
            // 关闭SSO授权
            plat.SSOSetting(true);
            plat.share(wechatMoments);
        }
        if (name.equals("SinaWeibo")) {
            SinaWeibo.ShareParams weibo = new SinaWeibo.ShareParams();
            weibo.text = ShareConstant.WECHATSHARETITLE;
            weibo.imagePath = TEST_IMAGE;
            // weibo.
            plat.setPlatformActionListener(this);
            // 关闭SSO授权
            plat.SSOSetting(true);
            plat.share(weibo);
        }
        if (name.equals("QZone")) {
            QZone.ShareParams qq = new QZone.ShareParams();
            qq.title = ShareConstant.WECHATSHARETITLE;
            qq.text = ShareConstant.WECHATSHARETEXT;
            qq.titleUrl = ShareConstant.WECHATSHAREURL;
            qq.imageUrl = ShareConstant.WECHATSHAREIMAGEURL;
            plat.setPlatformActionListener(this);
            // 关闭SSO授权
            plat.SSOSetting(true);
            plat.share(qq);
//            QZone.ShareParams qqsp = new QZone.ShareParams();
//            qqsp.title = "title";
//            qqsp.text = "share";
//            qqsp.titleUrl = "http://www.baidu.com";
//            Platform qqs = ShareSDK.getPlatform(ShareActivity.this, QQ.NAME);
//            qqs.setPlatformActionListener(this); // 设置分享事件回调
//            // 执行图文分享
//            qqs.share(qqsp);
        }
        if (name.equals("QQ")) {

            QQ.ShareParams qq = new QQ.ShareParams();
//            qq.title = ShareConstant.WECHATSHARETITLE;
//            qq.titleUrl = ShareConstant.WECHATSHAREURL;
//            qq.text = ShareConstant.WECHATSHARETEXT;
//            qq.imageUrl = ShareConstant.WECHATSHAREIMAGEURL;
            qq.setTitle(ShareConstant.WECHATSHARETITLE);
            qq.setTitleUrl(ShareConstant.WECHATSHAREURL);
            qq.setText(ShareConstant.WECHATSHARETITLE);
            qq.setImageUrl(ShareConstant.WECHATSHAREIMAGEURL);
//            qq.shareType = Platform.SHARE_WEBPAGE;
//            qq.imageUrl = imgarray.get(0).getMedium();
            plat.setPlatformActionListener(this);
            // 关闭SSO授权
            plat.SSOSetting(true);
            plat.share(qq);
        }
    }

    public static String TEST_IMAGE;// 本地图片地址

    /**
     * 保存并且获取图片地址
     *
     * @see [类、类#方法、类#成员]
     */
    private void initImagePath() {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists()) {
                TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/share_pic.jpg";
            } else {
                TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + "/share_pic.jpg";
            }
            // 创建图片文件夹
            File file = new File(TEST_IMAGE);
            if (!file.exists()) {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            TEST_IMAGE = null;
        }
    }

    String userId = "-1";

    String noteId;
    boolean isHost = false;
    boolean isForSale = false;


    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_TOAST: {
                String text = String.valueOf(msg.obj);
                T.showShort(this, text);
            }
            break;
            case MSG_ACTION_CCALLBACK: {
                switch (msg.arg1) {
                    case 1: { // 成功提示, successful notification
                        showNotification(2000, getString(R.string.share_completed));
                        T.showShort(this, "分享成功");
                        afterShare();
                    }
                    break;
                    case 2: { // 失败提示, fail notification
                        String expName = msg.obj.getClass().getSimpleName();
                        if ("WechatClientNotExistException".equals(expName)
                                || "WechatTimelineNotSupportedException".equals(expName)) {
                            showNotification(2000, getString(R.string.wechat_client_inavailable));
                            T.showShort(this, R.string.wechat_client_inavailable);
                        } else {
                            showNotification(2000, getString(R.string.share_failed));
                            T.showShort(this, R.string.share_failed);

                        }
                    }
                    break;
                    case 3: { // 取消提示, cancel notification
                        showNotification(2000, getString(R.string.share_canceled));
                        T.showShort(this, R.string.share_canceled);
                    }
                    break;
                }
            }
            break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) msg.obj;
                if (nm != null) {
                    nm.cancel(msg.arg1);
                }
            }
            break;

        }
        finish();
        return false;
    }

    @Override
    public void onCancel(Platform platform, int action) {
        // TODO Auto-generated method stub
        // 取消监听
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * <p/>
     * Title: onComplete.
     * </P>
     * <p/>
     * Description:
     * </P>
     *
     * @param arg2
     * @see cn.sharesdk.framework.PlatformActionListener#onComplete(cn.sharesdk.framework.Platform, int,
     * HashMap)
     */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
        // TODO Auto-generated method stub
        // 成功监听
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * <p/>
     * Title: onError.
     * </P>
     * <p/>
     * Description:
     * </P>
     *
     * @see cn.sharesdk.framework.PlatformActionListener#onError(cn.sharesdk.framework.Platform, int,
     * Throwable)
     */
    @Override
    public void onError(Platform platform, int action, Throwable t) {
        // TODO Auto-generated method stub
        // 打印错误信息
        t.printStackTrace();
        // 错误监听
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    // 在状态栏提示分享操作,the notification on the status bar
    private void showNotification(long cancelTime, String text) {
        try {
            Context app = this;
            NotificationManager nm = (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
            final int id = Integer.MAX_VALUE / 13 + 1;
            nm.cancel(id);

            long when = System.currentTimeMillis();
            Notification notification = new Notification(R.drawable.ic_launcher, text, when);
//            PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(), 0);
//            notification.setLatestEventInfo(app, "sharesdk test", text, pi);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            nm.notify(id, notification);

            if (cancelTime > 0) {
                Message msg = new Message();
                msg.what = MSG_CANCEL_NOTIFY;
                msg.obj = nm;
                msg.arg1 = id;
                UIHandler.sendMessageDelayed(msg, cancelTime, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p/>
     * Title: onBackPressed.
     * </P>
     * <p/>
     * Description:
     * </P>
     *
     * @see Activity#onBackPressed()
     */
//    @Override
//    public void onBackPressed() {
//        // TODO Auto-generated method stub
//        // Toast.makeText(getApplicationContext(), "分享完成后会自动关闭本页面", Toast.LENGTH_SHORT).show();
//        finish();
//    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);

    }

    /**
     * TODO 分享完成后返回.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周翔
     */
    private void afterShare() {
        // startActivity(intent);

    }

    /**
     * 跳转到分享页面
     *
     * @param con
     * @see [类、类#方法、类#成员]
     */
    public static void gotoShare(Activity con) {
        Intent i = new Intent(con, ShareActivity.class);
        con.startActivity(i);
        ((Activity) con).overridePendingTransition(R.anim.activity_open, 0);
    }

    /**
     * 跳转到分享页面
     *
     * @param con
     * @param notId
     * @see [类、类#方法、类#成员]
     */
    public static void gotoShare(Activity con, String notId) {
        Intent i = new Intent(con, ShareActivity.class);
        i.putExtra("noteId", notId);
        con.startActivity(i);
        ((Activity) con).overridePendingTransition(R.anim.activity_open, 0);
    }

    /**
     * 带帖子主人
     *
     * @param con
     * @param notId
     * @param noteUserId
     */
    public static void gotoShare(Activity con, String notId, String noteUserId) {
        Intent i = new Intent(con, ShareActivity.class);
        i.putExtra("noteId", notId);
        i.putExtra("noteUserId", noteUserId);
        con.startActivity(i);
        ((Activity) con).overridePendingTransition(R.anim.activity_open, 0);
    }

    public static void gotoShare(Activity con, String notId, String noteUserId, String isForSale) {
        Intent i = new Intent(con, ShareActivity.class);
        i.putExtra("noteId", notId);
        i.putExtra("noteUserId", noteUserId);
        i.putExtra("isForSale", isForSale);
        con.startActivity(i);
        ((Activity) con).overridePendingTransition(R.anim.activity_open, 0);
    }
}
