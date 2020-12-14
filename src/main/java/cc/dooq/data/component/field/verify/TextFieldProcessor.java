package cc.dooq.data.component.field.verify;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import cc.dooq.data.util.FieldProcessorUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description: 短文本只需要校验长短即可
 * @date 2020/12/11 11:51
 */
@Component("custom-text")
public class TextFieldProcessor implements FieldProcessor {

    @Override
    public DataResult verifyProperty(String val) {
        try{
            if(StringUtils.isNotBlank(val) &&
                    (val.length() > CommonConstants.SHORT_TEXT_MAX_LENGTH|| val.length() < CommonConstants.SHORT_TEXT_MIN_LENGTH)){
                return DataResult.createError(DataResultCode.FIELD_VALUE_TEXT_FORMAT_ERROR);
            }
            return DataResult.createSuccess();
        }catch (Exception e){
            return DataResult.createError(DataResultCode.FIELD_VALUE_TEXT_FORMAT_ERROR);
        }
    }

    @Override
    public DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition) {
        return TextareaFieldProcessor.textAssemblySelectCondition(condition);
    }



}
