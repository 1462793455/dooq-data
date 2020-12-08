package cc.dooq.data.manager.impl;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.dto.DataSourceCreateDTO;
import cc.dooq.data.dto.DataSourceGetDTO;
import cc.dooq.data.dto.PaginationDTO;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.entity.ViewDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.manager.DataSourceManager;
import cc.dooq.data.mapper.DataSourceMapper;
import cc.dooq.data.mapper.FieldMapper;
import cc.dooq.data.mapper.ViewMapper;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import cc.dooq.data.util.PageUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:50
 */
@Component
@Slf4j
public class DataSourceManagerImpl implements DataSourceManager {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public DataResult<Page<DataSourceDO>> getDataSourceByName(DataSourceGetDTO param) {

        // 提供默认值
        if(param == null){
            param = new DataSourceGetDTO();
        }

        param.setPageSize(1);

        // 获取分页信息
        DataResult<Page> pageInfoResult = PageUtil.page(param);
        if(!pageInfoResult.isSuccess()){
            return DataResult.createError(pageInfoResult);
        }
        // 分页查询
        Page page = dataSourceMapper.selectPage(
                pageInfoResult.getData(),
                new QueryWrapper<DataSourceDO>()
                .like(StringUtils.isNotBlank(param.getDataSourceName()),"source_name",param.getDataSourceName()));

        // 返回结果
        return DataResult.createSuccess(page);
    }

    @Override
    public DataResult<Boolean> createDataSource(DataSourceDO param) {

        // 参数校验
        DataResult verifyResult = verifyCreateDataSource(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 设置通用参数
            param.setCreateDate(new Date());
            param.setStatus(DataStatusEnum.VALID.getStatus());

            // 插入
            int insertCount = dataSourceMapper.insert(param);
            return DataResult.createSuccess(insertCount > 0);
        }catch (Exception e){
            log.error("DataSourceManagerImpl#createDataSource",e);
            return DataResult.createDBError();
        }
    }

    @Override
    public DataResult<Boolean> updateDataSource(DataSourceDO param) {

        // 校验 数据源ID
        DataResult verifyDataSourceIdResult = verifyRemoveDataSource(param.getId());
        if(!verifyDataSourceIdResult.isSuccess()){
            return DataResult.createError(verifyDataSourceIdResult);
        }
        // 校验其他参数
        DataResult verifyResult = verifyCreateDataSource(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 通用参数
            param.setUpdateDate(new Date());

            // 修改
            int updateCount = dataSourceMapper.updateById(param);

            // 返回结果
            return DataResult.createSuccess(updateCount > 0);
        }catch (Exception e){
            log.error("DataSourceManagerImpl#updateDataSource",e);
            return DataResult.createSuccess();
        }
    }

    /**
     * 校验 创建数据源方法参数
    */
    private DataResult verifyCreateDataSource(DataSourceDO param) {
        // 参数校验
        if(param == null){
            return DataResult.createError(DataResultCode.PARAM_ERROR);
        }

        // 数据源名称不允许为空
        String sourceName = param.getSourceName();
        // 为空时
        if(StringUtils.isBlank(sourceName)){
            return DataResult.createError(DataResultCode.DATA_SOURCE_NAME_IS_NULL_ERROR);
        }
        // 数据源名称长度校验
        int sourceNameLength = sourceName.length();
        if(sourceNameLength < CommonConstants.SHORT_TEXT_MIN_LENGTH
                || sourceNameLength > CommonConstants.SHORT_TEXT_MAX_LENGTH){
            return DataResult.createError(DataResultCode.DATA_SOURCE_NAME_LENGTH_OUT_ERROR);
        }

        // 检查是否存在相同的数据源名称
        Integer sameSourceNameCount = dataSourceMapper.selectCount(new QueryWrapper<DataSourceDO>().eq("source_name", sourceName));
        if(sameSourceNameCount != null && sameSourceNameCount > 0){
            return DataResult.createError(DataResultCode.DATA_SOURCE_NAME_EXIST_SAME_ERROR);
        }

        // 数据源描述，允许为空，如不为空时需要在 LONG_TEXT 范围内
        String sourceDesc = param.getSourceDesc();
        if(StringUtils.isNotBlank(sourceDesc) && (sourceDesc.length() > CommonConstants.LONG_TEXT_MAX_LENGTH
                || sourceDesc.length() < CommonConstants.LONG_TEXT_MIN_LENGTH )){
            return DataResult.createError(DataResultCode.DATA_SOURCE_DESC_LENGTH_OUT_ERROR);
        }

        // 返回校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Boolean> removeDataSource(Long dataSourceId) {

        // 参数校验
        DataResult verifyResult = verifyRemoveDataSource(dataSourceId);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 删除
            int deleteCount = dataSourceMapper.deleteById(dataSourceId);

            // 返回结果
            return DataResult.createSuccess(deleteCount > 0);
        }catch (Exception e){
            log.error("DataSourceManagerImpl#removeDataSource",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验 removeDataSource 方法参数
     * @param dataSourceId 视图ID
     * @return DataResult 校验结果
     */
    private DataResult verifyRemoveDataSource(Long dataSourceId) {
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

        // 检查当前是否有字段在用该数据源
        List<FieldDO> dataSourceUseFieldInfo  = fieldMapper.selectList(new QueryWrapper<FieldDO>().eq("data_source_id", dataSourceId));
        // 校验
        DataResult verifyDataSoueceUseResult = verifyDataSoueceUse(dataSourceUseFieldInfo);
        if(!verifyDataSoueceUseResult.isSuccess()){
            return DataResult.createError(verifyDataSoueceUseResult);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 构建使用数据源字段、视图信息提示
     * @param dataSourceUseFieldInfo
     * @return DataResult
    */
    private DataResult verifyDataSoueceUse(List<FieldDO> dataSourceUseFieldInfo) {
        // 没有正在使用的字段
        if(dataSourceUseFieldInfo == null || dataSourceUseFieldInfo.isEmpty()){
            return DataResult.createSuccess();
        }

        // 存在正在使用该数据源的字段时，需要提醒用户不可以删除  提示信息:  XX视图 》 XX字段 正在使用该数据源，无法删除！

        // 存储提示字符串 视图名 》》 字段名
        List<String> viewFieldInfo = new ArrayList<>();

        // 循环
        dataSourceUseFieldInfo.forEach(item -> {
            String fieldName = item.getFieldName();
            Long viewId = item.getViewId();
            if(viewId != null){
                ViewDO viewInfo = viewMapper.selectById(viewId);
                if(viewInfo != null){
                    String viewName = viewInfo.getViewName();
                    viewFieldInfo.add(String.format(CommonConstants.DATA_SOURCE_EXIST_USE_NOT_REMOVE_MESSAGE_ITEM,viewName,fieldName));
                }
            }
        });

        // 如果 viewFieldInfo 是空的 代表字段确实存在，但是字段对应的视图 不存在，所以
        if(!viewFieldInfo.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            viewFieldInfo.stream().forEach(item -> {
                stringBuffer.append(item + ",");
            });
            // 需要的格式
            String errorMsg = String.format(CommonConstants.DATA_SOURCE_EXIST_USE_NOT_REMOVE_MESSAGE,stringBuffer.substring(0, stringBuffer.length() - 1));
            return DataResult.createError(DataResultCode.PARAM_ERROR.getErrCode(),errorMsg);
        }

        // 校验成功
        return DataResult.createSuccess();
    }
}
