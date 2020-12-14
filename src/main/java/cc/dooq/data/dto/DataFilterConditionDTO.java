package cc.dooq.data.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/12 16:39
 */
@Data
public class DataFilterConditionDTO {

    /** 字段ID */
    private Long fileId;

    /** 条件，每种字段的过滤都有对应的组件处理，所以这里使用 json */
    private JSONObject conditionJson;

}
