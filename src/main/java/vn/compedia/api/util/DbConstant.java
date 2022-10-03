package vn.compedia.api.util;

public class DbConstant {

    // role table
    public static final int ROLE_ID_ADMIN = 1;
    public static final int ROLE_ID_USER = 3;
    public static final int ROLE_ID_STAFF = 2;
    public static final int ACTIVE_STATUS = 1;

    // trạng thái tài khoản
    public static final int ACCOUNT_ACTIVE = 1;
    public static final int NOT_ACTIVE_YET = 2;
    public static final int ACCOUNT_IN_ACTIVE = 3;

    // cấp lại mk
    public static final String PASSWORD = "123456a@A";


    // trạng thái shop
    public static final int SHOP_ACTIVE = 1;
    public static final int SHOP_INACTIVE = 2;
    public static final int SHOP_BLOCKED = 3;

    // Đối tượng sử dụng
    public static final int MALE = 1;
    public static final int WOMEN = 2;
    public static final int CHILDREN = 3;

    // Sản phẩm
    public static final int PRODUCT_ACTIVE = 1;
    public static final int PRODUCT_NOT_ACTIVE = 2;
    public static final int PRODUCT_ONE_TYPE = 1;
    public static final int PRODUCT_MANY_TYPE = 2;

    // Xuất buôn
    public static final int STATUS_WHOLESALE_WAIT_FOR_CONFIRMATION = 1;
    public static final int STATUS_WHOLESALE_FINISH = 2;
    public static final int STATUS_WHOLESALE_CANCELLED = 3;

    // Bán lẻ
    public static final int order_shop_status_cancelled = 3;
    public static final int order_shop_status_return_order = 4;

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 2;
    public static final int STATUS_DELETED = 3;

    //customer_role
    public static final long ROLE_CONSUMERS = 1;

    // Trạng thái mượn
    public static final int STATUS_WAITING = 0;
    public static final int STATUS_APPROVED  = 1;
    public static final int STATUS_REFUSE  = 2;

    //Trạng thái trả
    public static final int ACTION_EMPTY = 0;
    public static final int ACTION_ACTIVE = 1;
    public static final int ACTION_PAID  = 2;
    public static final int ACTION_TRANSGRESSION  = 3;

}
