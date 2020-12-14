package cc.dooq.data.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 18:08
 */
@Data
public class DataInfoVO implements Serializable {

    /** 行ID */
    private Long rowId;

    /** 字段JSON  其中键是 字段名称 值是 字段值 */
    private JSONObject fieldInfo;

}
