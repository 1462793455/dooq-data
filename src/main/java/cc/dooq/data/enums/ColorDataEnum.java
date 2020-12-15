package cc.dooq.data.enums;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 蛋清
 * @Description: 支持使用的颜色
 * @date 2020/12/3 18:49
 */
public enum ColorDataEnum {

    // https://www.teambition.com/org-admin/5e6594f1dffa1d0001773c34/config/priority-manager

//    RED("#e62412",1),
//    ORANGE("#fa8c15",2),
//    GREEN("#15ad31",3),
//    BLUE("#1b9aee",4),
    GREY("#8c8c8c",5),
    LIGHTRED("#ffcccc",6),
    LIGHTORANGE("#ffd591",7),
    LIGHTGREEN("#cafac8",8),
    LIGHTBLUE("#ccecff",9),
    LIGHTGREY("#e5e5e5",10)
    ;

    /**
     * 根据 颜色Code 获取颜色信息
     * @param code
     * @return ColorDataEnum
    */
    public static ColorDataEnum getColorDataEnum(Integer code){
        if(code == null){
            return null;
        }
        for (ColorDataEnum item : ColorDataEnum.values()){
            if(item.getCode().intValue() == code.intValue()){
                return item;
            }
        }
        return null;
    }

    /** 16进制颜色 */
    private String color;
    /** 颜色编号 */
    private Integer code;

    ColorDataEnum(String color, Integer code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{\"color\":\"" + this.color + "\",\"code:\":\""+ this.code +"\"}";
    }
}
