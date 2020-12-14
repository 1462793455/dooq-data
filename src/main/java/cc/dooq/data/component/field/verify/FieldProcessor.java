package cc.dooq.data.component.field.verify;

import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.util.DataResult;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:50
 */
public interface FieldProcessor {

    /**
     * 校验参数
     * @param val 校验的值
     * @return 校验结果
    */
    DataResult verifyProperty(String val);

    /**
     * 组装查询Sql
     * @param condition 该字段的查询条件
     * @return DataResult 结果
    */
    DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition);



}
