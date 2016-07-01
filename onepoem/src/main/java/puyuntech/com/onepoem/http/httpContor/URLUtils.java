package puyuntech.com.onepoem.http.httpContor;


/**
 * 作者 zx
 * 创建时间 2016/4/19 0019
 * 描述 请求地址
 * 修改时间 2016/4/19 0019
 * 修改描述 请求地址
 * 修改者 zx
 **/
public class URLUtils {

    /**
     * 请求成功
     */
    public final static int RESULT_SUCCESS = 2000;

    /**
     * 请求失败
     */
    public final static int RESULT_FAILED = 5000;

    /**
     * 重复登录
     */
    public final static int RESULT_LOGIN_AGAIN = 102;

    /**
     * 请求失败，服务器崩溃
     */
    public final static int RESULT_ERROR = 500;


    /**
     * 服务器地址
     */
    public static final String BASE_URL = "http://172.21.1.232:8080/";


    /**
     * 接口地址
     */
    public static final String BASE_URL_APP = BASE_URL + "/myshop/poem/";//接口地址

    /**
     * 资源存放路径
     */
    public static final String RES_PATH = BASE_URL + "/../res";
    /**
     * diypoem图片路径
     */
    public static final String DEFAULT_DIY_PATH = RES_PATH
            + "/assets/img/diypoem/";
    /**
     * 头像图片路径
     */
    public static final String DEFAULT_AVATAR_PATH = RES_PATH
            + "/assets/img/avatar/";

    /**
     * 诗图片路径
     */
    public static final String DEFAULT_MODEL_PATH = RES_PATH
            + "/assets/img/model/";

    /**
     * 诗人头像路径
     */
    public static final String DEFAULT_AUTHOR_PATH = RES_PATH
            + "/assets/img/author/";
    /**
     * 说明:登录
     * 入参:
     * 返回:
     */
    public static final String LOGIN = BASE_URL_APP + "login";//登录
    /**
     * 说明:获取诗词列表
     * 入参:
     * 返回:
     */
    public static final String GET_POEMLIST = BASE_URL_APP + "getPoemList";
    /**
     * 发现
     */
    public static final String GET_DIYPOEMLIST = BASE_URL_APP + "getDiyPoemList";
    /**
     * 说明:获取朝代列表
     * 入参:
     * 返回:
     */
    public static final String GET_DYNASTY_LIST = BASE_URL_APP + "getDynastyList";

    /**
     * 系统标签获取
     */
    public static final String GET_TAG_LIST = BASE_URL_APP + "getTagList";

    /**
     * 上传文件
     */
    public static final String UPLOAD = BASE_URL_APP + "/upload";

    /**
     *
     */
    public static final String PUBLISH_DIYPOEM = BASE_URL_APP + "/publishDiyPoem";


}