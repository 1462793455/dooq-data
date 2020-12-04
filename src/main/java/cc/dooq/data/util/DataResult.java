package cc.dooq.data.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:36
 */
public class DataResult<Data> {

    /** 是否成功 */
    @Getter
    @Setter
    private boolean success;
    /** 错误编号 */
    @Getter
    @Setter
    private int errCode;
    /** 错误提示 */
    @Getter
    @Setter
    private String message;
    /** 数据 */
    @Getter
    @Setter
    private Data data;

    public DataResult(boolean success, int errCode, String message, Data data) {
        this.success = success;
        this.errCode = errCode;
        this.message = message;
        this.data = data;
    }

    public static <Data> DataResult<Data> createSuccess(){
        return new DataResult(true, DataResultCode.SUCCESS.getErrCode(), DataResultCode.SUCCESS.getMessage(),null);
    }

    public static <Data> DataResult<Data> createSuccess(Data data){
        return new DataResult(true, DataResultCode.SUCCESS.getErrCode(), DataResultCode.SUCCESS.getMessage(),data);
    }

    public static <Data> DataResult<Data> createError(DataResultCode resultCode){
        return new DataResult(false,resultCode.getErrCode(),resultCode.getMessage(),null);
    }

    public static <Data> DataResult<Data> createError(DataResult result){
        return new DataResult(false,result.getErrCode(),result.getMessage(),null);
    }

    public static <Data> DataResult<Data> createDBError(){
        return new DataResult(false,DataResultCode.DATABASE_ERROR.getErrCode(),DataResultCode.DATABASE_ERROR.getMessage(),null);
    }

}
