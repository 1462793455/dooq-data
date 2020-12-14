package cc.dooq.data.component.field.verify;

import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import cc.dooq.data.util.FieldProcessorUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:51
 */
@Component("custom-date")
public class DateFieldProcessor implements FieldProcessor {

    /** 筛选条件 开始日期 Key */
    private static final String START_TIME_KEY = "startTime";

    /** 筛选条件 开始日期 Key */
    private static final String END_TIME_KEY = "entTime";

    @Override
    public DataResult verifyProperty(String val) {
        // 允许为空
        if(StringUtils.isBlank(val)){
            return DataResult.createSuccess();
        }
        try{
            new Date(Long.parseLong(val));
            return DataResult.createSuccess();
        }catch (Exception e){
            return DataResult.createError(DataResultCode.FIELD_VALUE_DATE_FORMAT_ERROR);
        }
    }

    @Override
    public DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition) {
        return assemblyDateFieldSelectCondition(condition);
    }

    /**
     * 该方法与 {@link DateTimeFieldProcessor} 共用
     * @param condition 查询条件
     * @return 条件 sql
    */
    public static DataResult<String> assemblyDateFieldSelectCondition(DataFilterConditionDTO condition) {
        // 校验参数是否合法
        DataResult verifyResult = FieldProcessorUtil.verifyAssemblySelectConditionParam(condition,START_TIME_KEY,END_TIME_KEY);
        if(!verifyResult.isSuccess()){
            return verifyResult;
        }
        // 返回结果
        return DataResult.createSuccess(String.format("value >= %s and value <= %s",
                FieldProcessorUtil.getConditionJsonProperty(condition,START_TIME_KEY),
                FieldProcessorUtil.getConditionJsonProperty(condition,END_TIME_KEY)));
    }
}
