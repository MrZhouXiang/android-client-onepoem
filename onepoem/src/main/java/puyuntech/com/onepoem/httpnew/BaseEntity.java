package puyuntech.com.onepoem.httpnew;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by zhoux on 2017/3/15.
 * 注释：
 */
@JsonIgnoreProperties(ignoreUnknown = true)//可忽略多余字段
public class BaseEntity<E> implements Serializable {
    private static final String KEY_SUCCESS = "2000";
    private String code;
    private String error;
    private E data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code.equalsIgnoreCase(KEY_SUCCESS);
    }

    public void showMsg(Context context) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}