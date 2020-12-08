package cc.dooq.data.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/7 20:15
 */
@Data
public class FieLdTypeInfoVO implements Serializable {

    /** 字段名称 */
    private String fieldName;
    /** 字段Code */
    private Integer fieldCode;
    /** 是否为枚举类型 */
    private Boolean isEnum;

}
