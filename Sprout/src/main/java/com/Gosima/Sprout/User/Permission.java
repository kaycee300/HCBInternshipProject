package com.Gosima.Sprout.User;

public class Permission {
    public static final String READ_PRODUCT = "READ_PRODUCT";

    public static final String MANAGE_PRODUCT = "MANAGE_PRODUCT";

    public static final String MANAGE_USER = "MANAGE_USER";

    public static final String VIEW_ORDER = "VIEW_ORDER";

    public static final String PLACE_ORDER = "PLACE_ORDER";


}
//| Role     | Authorities                                   |
//        | -------- | --------------------------------------------- |
//        | ADMIN    | MANAGE\_USERS, MANAGE\_PRODUCTS, VIEW\_ORDERS |
//        | VENDOR   | MANAGE\_PRODUCTS, VIEW\_ORDERS                |
//        | CUSTOMER | PLACE\_ORDER, VIEW\_PRODUCTS                  |





