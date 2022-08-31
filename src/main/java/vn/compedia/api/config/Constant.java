package vn.compedia.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class Constant {

    public static final Integer LIMIT_PER_PAGE = 10;
    public static final String TIME_ZONE_DEFAULT = "Asia/Ho_Chi_Minh";
    // Role id
    public static int ACCOUNT_ROLE_ADMIN = 1;
    public static int ACCOUNT_ROLE_MC = 2;
    public static int ACCOUNT_ROLE_USER = 3;
    public static int ACCOUNT_ROLE_GUEST = 4;

    public static String STATIC_CONTEXT;

    public static String NO_IMAGE_PATH;

    public static int SUCCESS = 0;
    public static String MESSAGE_SUCCESS = "Thành công";

    // Define error code for bill api
    public static int ERROR_CODE_INVALID = 2;
    public static String MESSAGE_INVALID = "Tham số đầu vào không đúng";

    public static int ERROR_CODE_NOT_FOUND = 1;
    public static String MESSAGE_NOT_FOUND = "Không tồn tại";

    public static int ROLE_SHOP = 2;

    public static int VN = 1;
    public static int LAO = 2;

    public static String VN_TEXT = "vi";
    public static String LAO_TEXT = "lo";
    @Autowired
    private Environment env;

    @PostConstruct
    public void setUpConfigData() {
        NO_IMAGE_PATH = env.getProperty("vn.compedia.static.noImagePath");
        STATIC_CONTEXT = env.getProperty("vn.compedia.static.context");
    }
}
