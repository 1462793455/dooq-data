package cc.dooq.data.enums;


/**
 * @author 蛋清
 * @Description: 字段类型枚举
 * @date 2020/12/3 18:05
 */
public enum FieldTypeEnum {

    RADIO(1,"单选项",true),
    CHECKBOX(2,"多选项",true),
    NUMBER(3,"数字项","custom-number"),
//    TEXT(4,"文本项","custom-text"),
    TEXTAREA(5,"文本域","custom-textarea"),
//    URL(6,"超链接"),
//    DATE(7,"日期","custom-date"),
//    TIME(8,"时间","custom-time"),
    DATE_TIME(9,"日期","custom-date-time"),

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
    /** 前端 \ 后端 {@link FieldProcessor} 字段处理器名称 */
    private String rendererName;

    FieldTypeEnum(Integer fieldCode,String fieldName, Boolean isEnum, String rendererName) {
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
        this.isEnum = isEnum;
        this.rendererName = rendererName;
    }

    FieldTypeEnum(Integer fieldCode,String fieldName, String rendererName) {
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
        this.rendererName = rendererName;
        this.isEnum = false;
    }

    FieldTypeEnum(Integer fieldCode,String fieldName, Boolean isEnum) {
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
        this.isEnum = isEnum;
    }

    FieldTypeEnum(Integer fieldCode,String fieldName) {
        this(fieldCode,fieldName,false);
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

    public String getRendererName() {
        return rendererName;
    }

    public void setRendererName(String rendererName) {
        this.rendererName = rendererName;
    }


}
