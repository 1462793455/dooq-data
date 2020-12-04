package cc.dooq.data.enums;

/**
 * @author 蛋清
 * @Description: 字段是否固定
 * @date 2020/12/3 18:17
 */
public enum FieldFixedEnum {

    /** 固定 */
    FIXED(1),
    /** 不固定 */
    NO_FIXED(0);

    /**
     * 传入字段固定Code 获取相应的字段固定枚举对象
     * @param code 字段固定Code
     * @return FieldFixedEnum
     */
    public static FieldFixedEnum getFieldFixedEnum(Integer code){
        if(code == null){
            return null;
        }
        for (FieldFixedEnum item : FieldFixedEnum.values()){
            if(item.getCode().intValue() == code.intValue()){
                return item;
            }
        }
        return null;
    }

    /** 字段固定Code */
    private Integer code;

    FieldFixedEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
