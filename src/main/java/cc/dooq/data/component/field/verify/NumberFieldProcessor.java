package cc.dooq.data.component.field.verify;

import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import cc.dooq.data.util.FieldProcessorUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:51
 */
@Component("custom-number")
public class NumberFieldProcessor implements FieldProcessor {

    /** 数值 范围查询 开始 KEY */
    private final String NUMBER_RANGE_START_KEY = "startRange";

    /** 数值 范围查询 结束 KEY */
    private final String NUMBER_RANGE_END_KEY = "endRange";

    @Override
    public DataResult verifyProperty(String val) {
        // 允许为空
        if(StringUtils.isBlank(val)){
            return DataResult.createSuccess();
        }
        try{
            Double.parseDouble(val);
            return DataResult.createSuccess();
        }catch (Exception e){
            return DataResult.createError(DataResultCode.FIELD_VALUE_NUMBER_FORMAT_ERROR);
        }
    }

    @Override
    public DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition) {
        // 校验参数是否合法
        DataResult verifyResult = FieldProcessorUtil.verifyAssemblySelectConditionParam(condition,NUMBER_RANGE_START_KEY,NUMBER_RANGE_END_KEY);
        if(!verifyResult.isSuccess()){
            return verifyResult;
        }
        // 返回结果
        return DataResult.createSuccess(String.format("value+0 >= %s and value+0 <= %s",
                FieldProcessorUtil.getConditionJsonProperty(condition,NUMBER_RANGE_START_KEY),
                FieldProcessorUtil.getConditionJsonProperty(condition,NUMBER_RANGE_END_KEY)));
    }
}
