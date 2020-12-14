package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/12 16:38
 */
@Data
public class ColumnDataGetDTO extends PaginationDTO implements Serializable {

    /** 视图ID */
    private Long viewId;

    /** 筛选条件 */
    private List<DataFilterConditionDTO> filterCondition;

//    private

}
