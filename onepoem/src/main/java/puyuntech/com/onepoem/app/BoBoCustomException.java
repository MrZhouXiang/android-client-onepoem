package puyuntech.com.onepoem.app;

/**
 * Author    lingchen
 * Email     838878458@qq.com
 * Time      2016/12/19
 * Function  波波自定义错误 为了Rx出现异常的时候不会停止等待框
 */

public class BoBoCustomException extends Exception {
    public BoBoCustomException(String detailMessage) {
        super(detailMessage);
    }
}
