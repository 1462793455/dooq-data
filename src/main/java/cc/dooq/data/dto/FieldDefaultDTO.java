package cc.dooq.data.dto;

import cc.dooq.data.enums.FieldFixedEnum;
import cc.dooq.data.enums.FieldTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:01
 */
@Data
public class FieldDefaultDTO implements Serializable {

    /** 视图ID */
    private Long viewId;

    /** 字段名称 */
    private String fieldName;

    /** 字段描述 */
    private String fieldDesc;

    /** 数据源id，只有枚举类型时才会必填 */
    private Integer dataSourceId;

    /** 排序 */
    private Integer order;

    /** 字段宽度 */
    private Integer fieldWidth;

    /** 是否固定 1:固定 0:不固定  默认不固定 */
    private FieldFixedEnum fixed;

}
