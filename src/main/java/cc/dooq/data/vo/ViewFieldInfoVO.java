package cc.dooq.data.vo;

import cc.dooq.data.entity.FieldDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:51
 */
@Data
public class ViewFieldInfoVO implements Serializable {

    /** 视图名称 */
    private String viewName;

    /** 视图ID */
    private Long viewId;

    /** 字段信息 */
    private List<FieldDO> fields;

}
