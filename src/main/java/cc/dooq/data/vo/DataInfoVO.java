package cc.dooq.data.vo;

import cc.dooq.data.entity.ColumnDataDO;
import cc.dooq.data.entity.RowDataDO;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 18:08
 */
@Data
public class DataInfoVO implements Serializable {

    /** 行ID */
    private Long rowId;

    /** 行详细信息 */
    private RowDataDO rowDataInfo;

    /** 字段JSON  其中键是 字段名称 值是 字段值 */
    private List<ColumnDataDO> fieldInfo;

}
