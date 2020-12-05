package cc.dooq.data.manager.impl;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.entity.DataSourcePropertyDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.manager.DataSourcePropertyManager;
import cc.dooq.data.mapper.DataSourcePropertyMapper;
import cc.dooq.data.mapper.DataSourceMapper;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/5 15:16
 */
@Component
@Slf4j
public class DataSourcePropertyManagerImpl implements DataSourcePropertyManager {

    @Autowired
    private DataSourcePropertyMapper dataSourcePropertyMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public DataResult<List<DataSourcePropertyDO>> getDataSourcePropertyList(Long dataSourceId) {
        // 数据源ID 必填
        if(dataSourceId == null){
            return DataResult.createError();
        }
        try{
            return DataResult.createSuccess(
                    dataSourcePropertyMapper.selectList(new QueryWrapper<DataSourcePropertyDO>().eq("data_source_id", dataSourceId)));
        }catch (Exception e){
            log.error("DataSourcePropertyManagerImpl#getDataSourcePropertyList",e);
            return DataResult.createDBError();
        }
    }

    @Override
    public DataResult<Boolean> createDataSourceProperty(DataSourcePropertyDO param) {

        // 校验参数
        DataResult verifyResult = verifyCreateSourceDataParam(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 插入通用参数
            param.setCreateDate(new Date());
            param.setStatus(DataStatusEnum.VALID.getStatus());

            // 插入数据
            int insertCount = dataSourcePropertyMapper.insert(param);
            return DataResult.createSuccess(insertCount > 0);
        }catch (Exception e){
            log.error("DataSourcePropertyManagerImpl#createDataSourceProperty",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验创建数据源属性方法
     * @param param 校验参数
     * @return DataResult 校验结果
    */
    private DataResult verifyCreateSourceDataParam(DataSourcePropertyDO param) {

        // 参数压根不存在
        if(param == null){
            return DataResult.createError();
        }

        // 数据源ID ，校验是否存在
        Long dataSourceId = param.getDataSourceId();
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

        // 属性名称校验
        String propertyName = param.getPropertyName();
        // 为空时
        if(StringUtils.isBlank(propertyName)){
            return DataResult.createError(DataResultCode.);
        }
        // 属性名称长度校验
        int propertyNameLength = propertyName.length();
        if(propertyNameLength < CommonConstants.SHORT_TEXT_MIN_LENGTH
                || propertyNameLength > CommonConstants.SHORT_TEXT_MAX_LENGTH){
            return DataResult.createError(DataResultCode.);
        }

        // 检查是否存在相同的视图名称(这里只检查同数据源ID下相同，而不是全部中的相同)
        Integer samePropertyNameCount = dataSourcePropertyMapper.selectCount(
                new QueryWrapper<DataSourcePropertyDO>()
                        .eq("property_name", propertyName)
                        .eq("data_source_id",dataSourceId));
        if(samePropertyNameCount != null && samePropertyNameCount > 0){
            return DataResult.createError(DataResultCode.);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Boolean> removeDataSourceProperty(Long propertyId) {

        // 校验参数是否合法
        DataResult verifyResult = verifyRemoveDataSourceProperty(propertyId);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{
            // 删除
            int removeCount = dataSourcePropertyMapper.deleteById(propertyId);

            // 返回结果
            return DataResult.createSuccess(removeCount > 0);
        }catch (Exception e){
            log.error("DataSourcePropertyManagerImpl#removeDataSourceProperty",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验 proertyId
     * @param propertyId 属性ID
     * @return DataResult 校验结果
    */
    private DataResult verifyRemoveDataSourceProperty(Long propertyId) {

        // 属性ID不允许为空
        if(propertyId == null){
            return DataResult.createError();
        }

        // 检查数据是否存在
        DataSourcePropertyDO dataSourcePropertyInfo = dataSourcePropertyMapper.selectById(propertyId);
        if(dataSourcePropertyInfo == null){
            return DataResult.createError();
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Boolean> updateDataSourceProperty(DataSourcePropertyDO param) {

        // 校验 属性ID
        DataResult verifyPropertyIdResult = verifyRemoveDataSourceProperty(param.getDataSourceId());
        if(!verifyPropertyIdResult.isSuccess()){
            return DataResult.createError(verifyPropertyIdResult);
        }

        // 校验其他属性
        DataResult verifyResult = verifyCreateSourceDataParam(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 通用参数
            param.setUpdateDate(new Date());

            // 修改
            int updateCount = dataSourcePropertyMapper.updateById(param);

            // 返回结果
            return DataResult.createSuccess(updateCount > 0);
        } catch (Exception e){
            log.error("DataSourcePropertyManagerImpl#updateDataSourceProperty",e);
            return DataResult.createDBError();
        }
    }
}
