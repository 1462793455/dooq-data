package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 20:10
 */
@Data
public class DataListGetDTO implements Serializable {

    /** 视图ID */
    private Long viewId;

    // 筛选条件 字段id : 值 (值的筛选类型有多种)
    // 数值类型 为 数值范围筛选
    // 日期、时间日期 为 时间范围筛选
    // 文本、文本域 为 模糊查询
    // 单选、多选需要查询数据源的数据
}
