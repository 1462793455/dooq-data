package cc.dooq.data.util;

import cc.dooq.data.component.field.verify.FieldProcessor;
import cc.dooq.data.dto.DataFilterConditionDTO;
import cc.dooq.data.enums.FieldTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Arrays;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/13 1:25
 */
public class FieldProcessorUtil {

    /**
     * 提供默认的校验 条件参数的方法
     * @param condition 条件
    */
    public static DataResult verifyAssemblySelectConditionParam(DataFilterConditionDTO condition, String ... keys){

        // 条件不存在时
        if(condition == null || condition.getConditionJson() == null){
            return DataResult.createError(DataResultCode.CONDITION_NOT_NULL_ERROR);
        }

        // 得到 查询条件
        JSONObject conditionJson = condition.getConditionJson();
        // 如果没有所需 key 时则直接返回成功
        if(keys.length <= 0){
            return DataResult.createSuccess();
        }

        // 循环检查是否 keys 中的值全部都包含
        long licitCount =
                Arrays.stream(keys).filter(key -> StringUtils.isNotBlank(conditionJson.getString(key))).count();

        // 如果不相同
        if(keys.length > licitCount){
            return DataResult.createError(DataResultCode.CONDITION_INCOMPLETE_ERROR);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 获取 condiion key 对应的数据
     * 注意：调用该方法需要自行保证 condition 存在
    */
    public static String getConditionJsonProperty(DataFilterConditionDTO condition,String key){
        return condition.getConditionJson().getString(key);
    }

    /**
     * 根据 fieldTypeCode 得到相应的  FieldProcessor
     * @param fieldTypeCode
     * @return 结果
    */
    public static DataResult<FieldProcessor> getFieldProcessor(Integer fieldTypeCode){
        // 得到字段类型枚举
        FieldTypeEnum fieldTypeEnum = FieldTypeEnum.getFieldTypeEnum(fieldTypeCode);
        // 字段类型错误
        if(fieldTypeEnum == null){
            return DataResult.createError(DataResultCode.FIELD_TYPE_IS_NULL);
        }

        // 根据字段信息得到相应的处理器
        FieldProcessor fieldProcessor = null;
        try{
            fieldProcessor = SpringUtils.getBean(fieldTypeEnum.getRendererName(), FieldProcessor.class);
        }catch (Exception e){}
        // 处理器获取失败
        if(fieldProcessor == null){
            return DataResult.createError(DataResultCode.FIELD_VERIFIER_IS_NULL);
        }
        // 返回结果
        return DataResult.createSuccess(fieldProcessor);
    }

}
