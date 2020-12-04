package cc.dooq.data.util;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/11/30 15:26
 */
public enum DataResultCode {

    SUCCESS("返回成功", 0),

    DATABASE_ERROR("数据库错误",10001),
    PARAM_ERROR("参数错误",10002),
    VIEW_NAME_IS_NULL_ERROR("视图名称不允许为空",10003),
    VIEW_NAME_LENGTH_OUT_ERROR("视图名称长度超出限制，请设置在 3~20 位字符内",10004),
    VIEW_DESC_LENGTH_OUT_ERROR("视图描述超出限制，请设置在 6~200 位字符内",10005),
    VIEW_NAME_EXIST_SAME_ERROR("已存在相同视图名称",10006),
    VIEW_ID_IS_NULL_ERROR("视图ID不允许为空",10007),
    VIEW_IS_NULL_ERROR("该视图不存在",10008),
    DATA_SOURCE_NAME_IS_NULL_ERROR("数据源名称不允许为空",10009),
    DATA_SOURCE_NAME_LENGTH_OUT_ERROR("数据源名称超出限制，请设置在 3~20 位字符内",10010),
    DATA_SOURCE_NAME_EXIST_SAME_ERROR("已存在相同数据源名称",10011),
    DATA_SOURCE_DESC_LENGTH_OUT_ERROR("数据源描述超出限制，请设置在 6~200 位字符内",10012),
    DATA_SOURCE_IS_NULL_ERROR("该数据源不存在",10013),
    DATA_SOURCE_ID_IS_NULL_ERROR("数据源ID不允许为空",10014),
    ;

    /** 错误提示 */
    private String message;
    /** 错误code */
    private int errCode;

    DataResultCode(String message, int errCode) {
        this.message = message;
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
