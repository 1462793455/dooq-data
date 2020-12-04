package cc.dooq.data.dto;

import cc.dooq.data.enums.FieldTypeEnum;
import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:24
 */
@Data
public class FieldCreateDTO extends FieldDefaultDTO {

    /** 字段类型 单选、多选等 */
    private FieldTypeEnum fieldType;

}
