package cc.dooq.data.manager.impl;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.entity.ViewDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.enums.FieldTypeEnum;
import cc.dooq.data.manager.FieldManager;
import cc.dooq.data.mapper.DataSourceMapper;
import cc.dooq.data.mapper.FieldMapper;
import cc.dooq.data.mapper.ViewMapper;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 蛋清
 * @Description: a
 * @date 2020/12/5 17:00
 */
@Component
@Slf4j
public class FieldManagerImpl implements FieldManager {

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private ViewMapper viewMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public DataResult<Boolean> createViewField(FieldDO param) {

        // 校验参数
        DataResult verifyResult = verifyCreateField(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 插入通用字段
            param.setCreateDate(new Date());
            param.setStatus(DataStatusEnum.VALID.getStatus());

            // 插入数据
            int insertCount = fieldMapper.insert(param);
            return DataResult.createSuccess(insertCount > 0);
        } catch (Exception e){
            log.error("FieldManagerImpl#createViewField",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验字段必填参数
     * @param param 参数
     * @return DataResult 校验结果
    */
    private DataResult verifyCreateField(FieldDO param) {
        // 啥都没有
        if(param == null){
            return DataResult.createError();
        }

        // 视图ID不可为空，不可为不存在的视图
        Long viewId = param.getViewId();
        DataResult verifyViewResult = verifyView(viewId);
        if(!verifyViewResult.isSuccess()){
            return DataResult.createError(verifyViewResult);
        }

        // 字段名称为小文本限制
        DataResult verifyFieldName = verifyFieldName(param.getFieldName());
        if(!verifyFieldName.isSuccess()){
            return DataResult.createError(verifyFieldName);
        }
        // 字段描述为大文本限制
        DataResult verifyFieldDesc = verifyFieldDesc(param.getFieldDesc());
        if(!verifyFieldDesc.isSuccess()){
            return DataResult.createError(verifyFieldName);
        }

        // 字段类型为枚举值时 数据源ID必填(字段类型不用校验，在controller已经校验过了)
        FieldTypeEnum fieldTypeEnum = FieldTypeEnum.getFieldTypeEnum(param.getFieldType());
        // 枚举类型必填 数据源
        if(fieldTypeEnum.getEnum() != null && fieldTypeEnum.getEnum()){
            DataResult verifyDataSoueceResult = verifyDataSource(param.getDataSourceId());
            if(!verifyDataSoueceResult.isSuccess()){
                return DataResult.createError(verifyFieldName);
            }
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 校验数据源
     * @param dataSourceId 数据源ID
     * @return DataResult 校验结果
    */
    private DataResult verifyDataSource(Long dataSourceId) {
        // 数据源ID 不允许为空
        if(dataSourceId == null){
            return DataResult.createError(DataResultCode.DATA_SOURCE_ID_IS_NULL_ERROR);
        }

        // 检查该数据源是否存在
        DataSourceDO dataSourceInfo = dataSourceMapper.selectById(dataSourceId);
        // 数据源不存在，删除失败
        if(dataSourceInfo == null){
            return DataResult.createError(DataResultCode.DATA_SOURCE_IS_NULL_ERROR);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 检查字段描述
     * @param fieldDesc 字段描述
     * @return DataResult 校验结果
    */
    private DataResult verifyFieldDesc(String fieldDesc) {
        // 字段描述，允许为空，如不为空时需要在 LONG_TEXT 范围内
        if(StringUtils.isNotBlank(fieldDesc) && (fieldDesc.length() > CommonConstants.LONG_TEXT_MAX_LENGTH
                || fieldDesc.length() < CommonConstants.LONG_TEXT_MIN_LENGTH )){
            return DataResult.createError(DataResultCode.);
        }
        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 校验字段名称
     * @param fieldName 字段名称
     * @return DataResult 校验结果
    */
    private DataResult verifyFieldName(String fieldName) {
        // 为空时
        if(StringUtils.isBlank(fieldName)){
            return DataResult.createError(DataResultCode.);
        }
        // 字段名称长度校验
        int fieldNameLength = fieldName.length();
        if(fieldNameLength < CommonConstants.SHORT_TEXT_MIN_LENGTH
                || fieldNameLength > CommonConstants.SHORT_TEXT_MAX_LENGTH){
            return DataResult.createError(DataResultCode.);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 校验视图
     * @param viewId 视图ID
     * @return DataResult 校验结果
    */
    private DataResult verifyView(Long viewId) {
        // 视图ID 不允许为空
        if(viewId == null){
            return DataResult.createError(DataResultCode.VIEW_ID_IS_NULL_ERROR);
        }

        // 检查该视图是否存在
        ViewDO viewInfo = viewMapper.selectById(viewId);
        // 视图不存在，删除失败
        if(viewInfo == null){
            return DataResult.createError(DataResultCode.VIEW_IS_NULL_ERROR);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Boolean> removeViewField(Long fieldId) {

        // 校验 字段ID
        DataResult verifyResult = verifyFieldId(fieldId);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{
            // 删除
            int deleteCount = fieldMapper.deleteById(fieldId);

            // 返回结果
            return DataResult.createSuccess(deleteCount > 0);
        }catch (Exception e){
            log.error("FieldManagerImpl#removeViewField",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验字段ID
    */
    private DataResult verifyFieldId(Long fieldId) {
        // 字段 不允许为空
        if(fieldId == null){
            return DataResult.createError(DataResultCode.);
        }

        // 检查该字段是否存在
        FieldDO fieldInfo = fieldMapper.selectById(fieldId);
        // 数据源不存在，删除失败
        if(fieldInfo == null){
            return DataResult.createError(DataResultCode.);
        }

        // 校验成功
        return DataResult.createSuccess();

    }

    @Override
    public DataResult<Boolean> updateViewField(FieldDO param) {

        // 校验其他字段
        DataResult dataResult = verifyCreateField(param);
        if(!dataResult.isSuccess()){
            return DataResult.createError(dataResult);
        }

        // 校验字段ID
        DataResult verifyResult = verifyFieldId(param.getId());
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{
            // 设置通用字段
            param.setUpdateDate(new Date());

            // 修改
            int updateCount = fieldMapper.updateById(param);

            // 返回结果
            return DataResult.createSuccess(updateCount > 0);
        }catch (Exception e){
            log.error("FieldManagerImpl#updateViewField",e);
            return DataResult.createDBError();
        }
    }

    @Override
    public DataResult<FieldDO> getViewFieldInfo(Long fieldId) {
        if(fieldId == null){
            return DataResult.createError();
        }
        return DataResult.createSuccess(fieldMapper.selectById(fieldId));
    }
}
