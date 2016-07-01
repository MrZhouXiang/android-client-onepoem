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
public class EditAdapter extends BaseMultiItemQuickAdapter<EditMod> {
    public EditAdapter(Context context, List data) {
        super(context, data);
        addItmeType(EditMod.TEXT, R.layout.item_edit_view);
        addItmeType(EditMod.IMG, R.layout.item_img_view);
        addItmeType(EditMod.HEADER, R.layout.publish_diy_poem_act_head_view);
    }

    int height = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(final BaseViewHolder helper, final EditMod item) {
        switch (helper.getItemViewType()) {
            case EditMod.TEXT:
//                helper.setText(R.id.content_inseart_et, "总长度：" + getItemCount() + "当前位置：" + helper.getAdapterPosition() + "");//文字展示
                if (helper.getAdapterPosition() + 1 == getItemCount()) {
                    //最后一行
                    helper.setVisible(R.id.del_iv, false);
                    // TODO: 2016/7/1 0001 如果是最后一行 且是从选择图片界面返回来的
                    if (flag) {
                        final EditText et = helper.getView(R.id.content_inseart_et);
                        et.setFocusable(true);
                        et.setFocusableInTouchMode(true);
                        et.requestFocus();
                        flag = false;
                    }
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
                helper.setImageUrl(R.id.img_show_iv, URLUtils.DEFAULT_DIY_PATH + item.getContent());//图片展示
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
                RadioGroup tags_rg = helper.getView(R.id.tags_rg);
                ;//标签
                int j = 0;
                for (int i = 0; i < ListUtils.getSize(AppDataUtils.tags); i++) {
                    if (Integer.valueOf(AppDataUtils.tags.get(i).getId()) <= 0) {
                        //删除“全部”,“精选”标签
                        continue;
                    }
                    RadioButton rb =
                            (RadioButton) mLayoutInflater.inflate(R.layout.radio_button_tag, null);
                    rb.setText(AppDataUtils.tags.get(i).getName());
                    rb.setWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp_60));
                    rb.setHeight(mContext.getResources().getDimensionPixelSize(R.dimen.dp_40));
                    if (!flag && j == 0) {
                        //默认选中第一个
                        rb.setChecked(true);
                        tag = AppDataUtils.tags.get(i).getName();
                    }
                    rb.setId(j);
                    j++;
                    tags_rg.addView(rb);
                }
                tags_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.getChildAt(checkedId);
                        tag = rb.getText().toString();
//                        T.showShort(mContext, "选中：" + rb.getText());
//                        group.getCheckedRadioButtonId();
                    }
                });
                break;
        }

    }

    String title = "";//
    String tag = "";//
    boolean flag = false;//是否需要focus到最后一个edittext

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTitleText() {
        return title;
    }

    public String getTag() {
        return tag;
    }
}
