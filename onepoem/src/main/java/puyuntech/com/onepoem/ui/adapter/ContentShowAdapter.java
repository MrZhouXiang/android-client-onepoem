package puyuntech.com.onepoem.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nicodelee.utils.ListUtils;

import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.app.AppDataUtils;
import puyuntech.com.onepoem.http.httpContor.URLUtils;
import puyuntech.com.onepoem.model.EditMod;


/**
 * @作者 Administrator
 * @创建时间 2016/6/23 0023
 * @描述 用于编辑shic
 * @修改时间 2016/6/23 0023
 * @修改描述 用于编辑shic
 * @修改者 Administrator
 **/
public class ContentShowAdapter extends BaseMultiItemQuickAdapter<EditMod> {
    public ContentShowAdapter(Context context, List data) {
        super(context, data);
        addItmeType(EditMod.TEXT, R.layout.item_text_view);
        addItmeType(EditMod.IMG, R.layout.item_img_view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(final BaseViewHolder helper, final EditMod item) {
        switch (helper.getItemViewType()) {
            case EditMod.TEXT:
                helper.setText(R.id.content_inseart_tv, item.getContent());//文字展示
                break;
            case EditMod.IMG:
                helper.setImageUrl(R.id.img_show_iv, URLUtils.DEFAULT_DIY_PATH + item.getContent());//图片展示
                break;
        }

    }


}
