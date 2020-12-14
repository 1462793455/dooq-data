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
 * @Description: 长文本校验
 * @date 2020/12/11 11:51
 */
@Component("custom-textarea")
public class TextareaFieldProcessor implements FieldProcessor {

    /** 文本 KEY */
    private static final String TEXT_KEY = "text";

    @Override
    public DataResult verifyProperty(String val) {
        try{
            if(StringUtils.isNotBlank(val) &&
                    (val.length() > CommonConstants.LONG_TEXT_MAX_LENGTH|| val.length() < CommonConstants.LONG_TEXT_MIN_LENGTH)){
                return DataResult.createError(DataResultCode.FIELD_VALUE_TEXTAREA_FORMAT_ERROR);
            }
            return DataResult.createSuccess();
        }catch (Exception e){
            return DataResult.createError(DataResultCode.FIELD_VALUE_TEXTAREA_FORMAT_ERROR);
        }
    }

    @Override
    public DataResult<String> assemblySelectCondition(DataFilterConditionDTO condition) {
        return textAssemblySelectCondition(condition);
    }

    /**
     * text 方式 查询参数
    */
    public static DataResult<String> textAssemblySelectCondition(DataFilterConditionDTO condition){
        // 校验参数是否合法
        DataResult verifyResult = FieldProcessorUtil.verifyAssemblySelectConditionParam(condition,TEXT_KEY);
        if(!verifyResult.isSuccess()){
            return verifyResult;
        }
        // 返回结果
        return DataResult.createSuccess("value like '%" + FieldProcessorUtil.getConditionJsonProperty(condition,TEXT_KEY) + "%'");
    }

    public static void main(String[] args) {


        String a= "value like '%" + "a" + "%'";
        System.out.println(a);

    }
}
