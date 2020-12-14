package cc.dooq.data.vo;

import cc.dooq.data.entity.FieldDO;
import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/10 19:15
 */
@Data
public class FieldInfoVO extends FieldDO {

    /** 前端字段处理器名称 */
    private String rendererName;

}
