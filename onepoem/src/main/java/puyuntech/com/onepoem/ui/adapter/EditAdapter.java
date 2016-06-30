package puyuntech.com.onepoem.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import puyuntech.com.onepoem.R;
import puyuntech.com.onepoem.model.EditMod;


/**
 * @作者 Administrator
 * @创建时间 2016/6/23 0023
 * @描述 用于编辑shic
 * @修改时间 2016/6/23 0023
 * @修改描述 用于编辑shic
 * @修改者 Administrator
 **/
public class EditAdapter extends BaseMultiItemQuickAdapter<EditMod> {
    public EditAdapter(Context context, List data) {
        super(context, data);
        addItmeType(EditMod.TEXT, R.layout.item_edit_view);
        addItmeType(EditMod.IMG, R.layout.item_img_view);
        addItmeType(EditMod.HEADER, R.layout.publish_diy_poem_act_head_view);
    }

    int height = 0;

    @Override
    protected void convert(final BaseViewHolder helper, final EditMod item) {
        switch (helper.getItemViewType()) {
            case EditMod.TEXT:
//                helper.setText(R.id.content_inseart_et, "总长度：" + getItemCount() + "当前位置：" + helper.getAdapterPosition() + "");//文字展示
                if (helper.getAdapterPosition() + 1 == getItemCount()) {
                    helper.setVisible(R.id.del_iv, false);
                    final EditText et = helper.getView(R.id.content_inseart_et);
                    et.setFocusable(true);
                    et.setFocusableInTouchMode(true);
                    et.requestFocus();
                } else {
                    helper.setVisible(R.id.del_iv, true);
                }
                helper.setOnClickListener(R.id.del_iv, new OnItemChildClickListener());
                EditText et0 = helper.getView(R.id.content_inseart_et);
                et0.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        item.setContent(s.toString());
                    }
                });
                break;
            case EditMod.IMG:
                helper.setImageUrl(R.id.img_show_iv, item.getContent());//图片展示
                if (helper.getAdapterPosition() + 1 == getItemCount()) {
                    helper.setVisible(R.id.del_iv, false);
                } else {
                    helper.setVisible(R.id.del_iv, true);
                }
                helper.setOnClickListener(R.id.del_iv, new OnItemChildClickListener());
                break;
            case EditMod.HEADER:
                helper.setOnClickListener(R.id.url_iv, new OnItemChildClickListener());
                EditText et = helper.getView(R.id.title_et);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        title = s.toString();
                    }
                });
                break;
        }

    }

    String title = "";//

    public String getTitleText() {
        return title;
    }
}
