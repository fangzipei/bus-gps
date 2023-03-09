package com.zonefun.backend.constant;

/**
 * @ClassName CommonConstant
 * @Description 常用常量
 * @Date 2022/10/18 9:38
 * @Author ZoneFang
 */
public class CommonConstant {
    /**
     * 直销平台角色
     */
    public static String ROLE_ADMIN = "管理员";

    /**
     * 经办人初始客户编号
     */
    public static String ORIGIN_CONT_ACCOUNT = "87000001";

    /**
     * 短信模板
     * 1. 注册经办人审核成功
     * 2. 审核失败
     * 3. 经办人变更审核成功
     * 4. 经办人注销审核成功
     */
    public static String SMS_REGISTER_CONTACT_SUCCESS = "尊敬的{}，您提交的国联证券资管直销平台的注册申请已通过。您的客户编码：{}，初始密码：{}，请在首次登陆后修改。【国联证券资管】";
    public static String SMS_REVIEW_FAIL = "尊敬的{}，您提交的业务申请未成功办理；如需继续办理，请重新提交业务申请或联系业务人员0510-85609723。感谢您的支持，祝您工作愉快！【国联证券资管】";
    public static String SMS_CONTACT_PHONE_CHANGE_SUCCESS = "尊敬的{}，您提交的用户手机号码变更申请已办理成功。【国联证券资管】";
    public static String SMS_CONTACT_CHANGE_SUCCESS = "尊敬的{}，您提交的用户变更申请业务办理成功。您的客户编码：{}，初始密码：{}，请在首次登陆后修改。【国联证券资管】";
    public static String SMS_CONTACT_WRITE_OFF_SUCCESS = "尊敬的{}，您提交的客户编号注销业务已办理成功。【国联证券资管】";

    /**
     * 经办人名单Excel模板文件名
     */
    public static String CONTACT_BLACKLIST_EXCEL_TEMPLATE = "经办人名单模板.xls";

    /**
     * 管理平台接口包名
     */
    public static String MNGR_CONTROLLER_PACKAGE_NAME = "com.glsc.dscp.controller.mngr";

    /**
     * 管理平台登录token前缀
     */
    public static String MNGR_LOGIN_TOKEN_PREFIX = "DsManagerLoginPrefix:";

    /**
     * 客户平台登录token前缀
     */
    public static String CUST_LOGIN_TOKEN_PREFIX = "DsCustLoginPrefix:";

    /**
     * redis令牌过期时间
     */
    public static long DS_TOKEN_REDIS_EXPIRE_SECOND = 60 * 60 * 2;
}
