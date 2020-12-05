package cc.dooq.data.enums;

/**
 * @author 蛋清
 * @Description: 字段类型枚举
 * @date 2020/12/3 18:05
 */
public enum FieldTypeEnum {

    RADIO("单选项",1,true),
    CHECKBOX("多选项",2,true),
    NUMBER("数字项",3),
    TEXT("文本项",4),
    TEXTAREA("文本域",5),
    URL("超链接",6),
    DATE("日期",7),
    DATE_TIME("日期时间",8),
    ;

    /**
     * 传入字段类型Code 获取相应的字段类型枚举对象
     * @param code 字段类型Code
     * @return FieldTypeEnum
    */
    public static FieldTypeEnum getFieldTypeEnum(Integer code){
        if(code == null){
            return null;
        }
        for (FieldTypeEnum item : FieldTypeEnum.values()){
            if(item.getFieldCode().intValue() == code.intValue()){
                return item;
            }
        }
        return null;
    }


    /** 字段类型名称 */
    private String fieldName;
    /** 字段类型Code */
    private Integer fieldCode;
    /** 是否为枚举类型 */
    private Boolean isEnum;

    FieldTypeEnum(String fieldName, Integer fieldCode, Boolean isEnum) {
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
        this.isEnum = isEnum;
    }

    FieldTypeEnum(String fieldName, Integer fieldCode) {
        this(fieldName,fieldCode,false);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(Integer fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Boolean getEnum() {
        return isEnum;
    }

    public void setEnum(Boolean anEnum) {
        isEnum = anEnum;
    }
}
