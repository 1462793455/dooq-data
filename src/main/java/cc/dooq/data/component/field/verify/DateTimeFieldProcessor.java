package cc.dooq.data.component.field.verify;

import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:51
 */
@Component("custom-date-time")
public class DateTimeFieldProcessor implements FieldProcessor {

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
            return DataResult.createError(DataResultCode.FIELD_VALUE_DATE_TIME_FORMAT_ERROR);
        }
    }

    @Override
    public DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition) {
        return DateFieldProcessor.assemblyDateFieldSelectCondition(condition);
    }
}
