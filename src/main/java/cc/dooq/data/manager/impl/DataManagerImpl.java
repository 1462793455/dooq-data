package cc.dooq.data.manager.impl;

import cc.dooq.data.component.field.verify.FieldProcessor;
import cc.dooq.data.dto.*;
import cc.dooq.data.entity.ColumnDataDO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.entity.RowDataDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.enums.FieldTypeEnum;
import cc.dooq.data.manager.DataManager;
import cc.dooq.data.manager.FieldManager;
import cc.dooq.data.manager.ViewManager;
import cc.dooq.data.mapper.ColumnDataMapper;
import cc.dooq.data.mapper.FieldMapper;
import cc.dooq.data.mapper.RowDataMapper;
import cc.dooq.data.util.*;
import cc.dooq.data.vo.DataInfoVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionalProxy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:31
 */
@Slf4j
@Component
public class DataManagerImpl implements DataManager {

    @Autowired
    private ColumnDataMapper columnDataMapper;

    @Autowired
    private RowDataMapper rowDataMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private FieldManager fieldManager;

    @Autowired
    private ViewManager viewManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataResult<Boolean> createData(DataCreateDTO param) {

        // 1. 校验参数是否合法 (视图校验、排除无效字段值、校验字段值是否合法)
        DataResult verifyResult = verifyDataCreate(param);
        // 校验失败时
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        // 2. 首先创建一列 Row
        DataResult<Long> rowInsertResult = createRowData(param.getViewId());
        if(!rowInsertResult.isSuccess()){
            return DataResult.createError(rowInsertResult);
        }

        // 得到 行 ID
        Long rowId = rowInsertResult.getData();

        // 3. 获取该视图下所有可用字段并将数据插入 保存单元格数据
        DataResult columnInsertResult = createColumnData(param.getViewId(),rowId,param.getDataInfoList());
        if(!columnInsertResult.isSuccess()){
            return DataResult.createError(columnInsertResult);
        }

        // 返回结果
        return DataResult.createSuccess();
    }

    /**
     * 创建 Column 数据 并返回创建结果
    */
    private DataResult createColumnData(Long viewId, Long rowId, List<DataInfoDTO> dataInfoList) {

        // 获得该视图下所有合法的字段信息
        List<FieldDO> fieldList =
                fieldMapper.selectList(new QueryWrapper<FieldDO>().eq("view_id", viewId));

        // 该视图没有任何合法字段
        if(fieldList == null || fieldList.isEmpty()){
            return DataResult.createError(DataResultCode.VIEW_NOT_LICIT_FIELD);
        }

        // 将 DataInfoDTO 转换为 Map 进行操作
        Map<Long, String> dataInfoMap =
                dataInfoList.stream().collect(Collectors.toMap(DataInfoDTO::getFieldId, DataInfoDTO::getValue));

        // 循环生成 columnData 并插入
        for (FieldDO fieldInfo : fieldList) {

            // 不存在则
            if(fieldInfo == null){
                continue;
            }

            // 得到 字段值
            String value = dataInfoMap.get(fieldInfo.getId());

            // 创建
            ColumnDataDO columnDataInfo =
                    createColumnDataDO(viewId,rowId,fieldInfo.getId(),value);

            // 插入 column data
            int insertCount = columnDataMapper.insert(columnDataInfo);
            if(insertCount < 0){
                // 回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                // 插入失败
                return DataResult.createError(DataResultCode.DATABASE_ERROR);
            }
        }

        // 保存成功
        return DataResult.createSuccess();
    }

    private ColumnDataDO createColumnDataDO(Long viewId, Long rowId, Long fieldId, String value) {
        ColumnDataDO columnDataInfo = new ColumnDataDO();
        columnDataInfo.setRowId(rowId);
        columnDataInfo.setViewId(viewId);
        columnDataInfo.setFieldId(fieldId);
        columnDataInfo.setValue(value);
        columnDataInfo.setStatus(DataStatusEnum.VALID.getStatus());
        columnDataInfo.setCreateDate(new Date());
        return columnDataInfo;
    }

    /**
     * 创建 Row 数据  并返回 row id
     * @param viewId 视图ID
     * @return row id
    */
    private DataResult<Long> createRowData(Long viewId) {

        // 视图ID 不可为空
        if(viewId == null){
            return DataResult.createError(DataResultCode.VIEW_ID_IS_NULL_ERROR);
        }

        // 创建 行数据
        RowDataDO rowDataInfo = new RowDataDO();
        rowDataInfo.setStatus(DataStatusEnum.VALID.getStatus());
        rowDataInfo.setViewId(viewId);
        rowDataInfo.setCreateDate(new Date());

        // 插入
        int insertCount = rowDataMapper.insert(rowDataInfo);
        if(insertCount <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 插入失败
            return DataResult.createError(DataResultCode.DATABASE_ERROR);
        }

        // 插入成功
        return DataResult.createSuccess(rowDataInfo.getId());
    }

    /**
     * 校验 DataCreateDTO 数据
     * @param param 校验对象
     * @return DataResult 校验结果
    */
    private DataResult verifyDataCreate(DataCreateDTO param) {

        // 如果参数没有传
        if(param == null){
            return DataResult.createError(DataResultCode.PARAM_ERROR);
        }

        // 获得 视图ID 并校验
        Long viewId = param.getViewId();
        DataResult verifyViewValidResult = viewManager.verifyViewValid(viewId);
        if(!verifyViewValidResult.isSuccess()){
            return DataResult.createError(verifyViewValidResult);
        }

        //  传入的 创建字段信息 List
        List<DataInfoDTO> dataInfoList = param.getDataInfoList();
        // 允许创建空列
        if(dataInfoList == null || dataInfoList.isEmpty()){
            return DataResult.createSuccess();
        }

        // 迭代器方式进行循环
        Iterator<DataInfoDTO> iterator = dataInfoList.iterator();
        while(iterator.hasNext()){

            // 当前元素
            DataInfoDTO dataInfo = iterator.next();

            // 必要参数不存在时，删除该元素并进行下一个
            if(dataInfo == null || dataInfo.getFieldId() == null){
                iterator.remove();
                continue;
            }

            // 获取信息，不存在时删除该元素并进行下一个
            FieldDO fieldInfo = fieldMapper.selectById(dataInfo.getFieldId());
            if(fieldInfo == null){
                iterator.remove();
                continue;
            }

            // 得到字段处理器
            DataResult<FieldProcessor> fieldProcessorResult =
                    FieldProcessorUtil.getFieldProcessor(fieldInfo.getFieldType());
            if(!fieldProcessorResult.isSuccess()){
                return DataResult.createError(fieldProcessorResult);
            }

            // 得到处理器实例
            FieldProcessor fieldProcessor = fieldProcessorResult.getData();

            // 校验结果
            DataResult verifyResult = fieldProcessor.verifyProperty(dataInfo.getValue());
            if(!verifyResult.isSuccess()){
                return DataResult.createError(verifyResult);
            }
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    /**
     * 两种查询情况（都需要支持排序）
     * 1. 没有任何条件，只需要分页查询
     * 2. 有条件筛选，并且需要分页
     *
     * 暂时不支持排序
     *
     * select row_id from (
     * SELECT row_id,SUM((if(field_id=23 or field_id = 25,1,0) + if(value='123' or value = '123' ,1,0) )) as number,SUM(if(field_id = 24,value,0) + if(field_id = 25,value,0)) as filter
     * from t_column_data GROUP BY row_id order by filter desc) as column_data where number >= 4
     *
    */
    @Override
    public DataResult<Page<DataInfoVO>> getViewDataList(ColumnDataGetDTO param) {

        // 校验参数是否合法
        DataResult verifyResult = verifyColumnDataGet(param);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        // 筛选条件
        List<DataFilterConditionDTO> filterCondition = param.getFilterCondition();
        // 有筛选条件时
        if(filterCondition != null && !filterCondition.isEmpty()){
            return filterColumnData(param);
        // 没有筛选条件，则只需要查询全部并分页即可
        } else {
            return selectAllColumnData(param);
        }
    }

    /**
     * 查询全部数据并分页
    */
    private DataResult<Page<DataInfoVO>> selectAllColumnData(ColumnDataGetDTO param) {

        // 视图ID
        Long viewId = param.getViewId();

        // 首先根据分页参数获取该视图下行
        DataResult<Page<RowDataDO>> pageResult = PageUtil.page(param, new RowDataDO());
        if(!pageResult.isSuccess()){
            return DataResult.createError(pageResult);
        }

        // 获得分页参数
        Page<RowDataDO> pageInfo = pageResult.getData();

        // 查询分页 Row 数据
        Page<RowDataDO> rowInfoResult = rowDataMapper.selectPage(pageInfo, new QueryWrapper<RowDataDO>().eq("view_id", viewId));

        // 得到查询到的 数据结果
        List<RowDataDO> rowDataInfoList = rowInfoResult.getRecords();

        // 返回结果
        return getColumnDataByRowId(rowDataInfoList,pageInfo);
    }

    /**
     * 根据 rowId 查询匹配的 ColumnData
     * @param rowInfoList RowId
     * @param pageInfo 分页信息
     * @return 结果
    */
    private DataResult<Page<DataInfoVO>> getColumnDataByRowId(List<RowDataDO> rowInfoList,Page pageInfo){

        // pageInfo 不可为空，为空时后续流程无法走通
        if(pageInfo == null){
            return DataResult.createError(DataResultCode.PAGE_INFO_NOT_NULL_ERROR);
        }

        // 没有 rowInfoList 则返回空 page 即可
        if(rowInfoList == null || rowInfoList.isEmpty()){
            pageInfo.setRecords(new ArrayList());
            return DataResult.createSuccess(pageInfo);
        }

        // 行ID集合
        List<Long> rowIdList = rowInfoList.stream().map(RowDataDO::getId).collect(Collectors.toList());

        // 查询 rowId 所对应的所有 columnInfo
        List<ColumnDataDO> columnDataList =
                columnDataMapper.selectList(new QueryWrapper<ColumnDataDO>().in("row_id",rowIdList.toArray()));

        // rowInfo 转换 Map 存储
        Map<Long, RowDataDO> rowInfoMap =
                rowInfoList.stream().collect(Collectors.toMap(RowDataDO::getId, (value) -> value));

        // 结果集
        Map<Long,List<ColumnDataDO>> dataMapResult = new HashMap<>();
        columnDataList.forEach(item -> {
            // 先获取其中的集合
            List<ColumnDataDO> columnDataInnerList = dataMapResult.get(item.getRowId());
            if(columnDataInnerList == null){
                columnDataInnerList = new ArrayList<>();
            }
            columnDataInnerList.add(item);
            dataMapResult.put(item.getRowId(),columnDataInnerList);
        });


        // 将 Map 数据 转换 List
        List<DataInfoVO> dataList = dataMapResult.entrySet().stream().map((item) -> {
            DataInfoVO dataInfoVO = new DataInfoVO();
            dataInfoVO.setRowId(item.getKey());
            dataInfoVO.setFieldInfo(item.getValue());
            dataInfoVO.setRowDataInfo(rowInfoMap.get(item.getKey()));
            return dataInfoVO;
        }).collect(Collectors.toList());

        // 返回结果
        return DataResult.createSuccess(PageUtil.resetPageData(pageInfo,dataList));
    }

    /**
     * 数据筛选，查询完毕后返回 rowId 然后单独根据 rowId 去查询数据即可
     * 1. 组装条件
     * 2. 查询分页好的 rowId
     * 3. 查询 rowId 对应的列数据
     *
     * @param param 条件
    */
    private DataResult<Page<DataInfoVO>> filterColumnData(ColumnDataGetDTO param){

        // 没有合适的条件
        if(param == null || param.getFilterCondition().isEmpty()){
            return DataResult.createError(DataResultCode.FILTER_CONDITION_NOT_NULL_ERROR);
        }

        // 条件集合
        List<DataFilterConditionDTO> filterCondition = param.getFilterCondition();

        // 字段条件 List
        List<Long> fieldList = new ArrayList<>();
        // 值条件 List
        List<String> conditionList = new ArrayList<>();

        // 循环检查并生成查询条件
        for (DataFilterConditionDTO filterConditionItem : filterCondition) {
            // 得到字段ID
            Long fileId = filterConditionItem.getFileId();
            // 校验字段是否有效
            DataResult<FieldDO> verifyFieldResult = fieldManager.verifyFieldId(fileId);
            if(!verifyFieldResult.isSuccess()){
                return DataResult.createError(verifyFieldResult);
            }

            // 字段信息
            FieldDO fieldInfo = verifyFieldResult.getData();

            // 得到字段处理器
            DataResult<FieldProcessor> fieldProcessorResult =
                    FieldProcessorUtil.getFieldProcessor(fieldInfo.getFieldType());
            if(!fieldProcessorResult.isSuccess()){
                return DataResult.createError(fieldProcessorResult);
            }

            // 得到处理器实例
            FieldProcessor fieldProcessor = fieldProcessorResult.getData();

            // 添加字段条件
            fieldList.add(fileId);

            // 得到 字段条件 sql
            DataResult<String> conditionSqlResult = fieldProcessor.assemblySelectCondition(filterConditionItem);
            if(!conditionSqlResult.isSuccess()){
                return DataResult.createError(conditionSqlResult);
            }

            // 添加值条件
            conditionList.add(conditionSqlResult.getData());
        }

        // 返回结果
        return selectColumnDataRowIdList(fieldList, conditionList,param.getViewId(), param);
    }


    /**
     * 校验 ColumnDataGetDTO 参数是否合法
     * @param param 参数
     * @return DataResult 校验结果
    */
    private DataResult verifyColumnDataGet(ColumnDataGetDTO param) {

        // 参数不存在时
        if(param == null){
            return DataResult.createError(DataResultCode.PARAM_ERROR);
        }

        Long viewId = param.getViewId();
        // 校验视图是否合法
        DataResult verifyViewValid = viewManager.verifyViewValid(viewId);
        if(!verifyViewValid.isSuccess()){
            return DataResult.createError(verifyViewValid);
        }

        // 校验成功
        return DataResult.createSuccess();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataResult<Boolean> updateData(DataUpdateDTO param) {

        // 1. 校验参数是否合法 (视图校验、排除无效字段值、校验字段值是否合法)
        DataResult verifyResult = verifyDataCreate(param);
        // 校验失败时
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        // 校验行是否存在
        DataResult verifyRowResult = verifyRowId(param.getRowId());
        if(!verifyRowResult.isSuccess()){
            return DataResult.createError(verifyRowResult);
        }

        // 检查字段是否缺少，缺少则补齐 （如果行数据已经插入的情况下，添加了新字段，那么这行数据是没有的，所以需要补上 column 的数据）
        makeUpColumnFieldMissing(param.getViewId(),param.getRowId());

        // 获得字段集合
        List<DataInfoDTO> dataInfoList = param.getDataInfoList();
        // 无法修改
        if(dataInfoList == null || dataInfoList.isEmpty()){
            return DataResult.createError(DataResultCode.FIELD_NOT_UPDATE_DATE);
        }

        // 修改行ID
        Long rowId = param.getRowId();
        // 修改视图ID
        Long viewId = param.getViewId();

        // 循环修改
        long updateCount = dataInfoList.stream().filter(item -> updateColumnData(rowId, viewId, item)).count();
        if(updateCount != dataInfoList.size()){
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 修改失败
            return DataResult.createError(DataResultCode.DATABASE_ERROR);
        }

        // 修改成功
        return DataResult.createSuccess();
    }

    /**
     * 检查该行数据中，缺失的字段并补齐
     * 注：由于已经插入数据后进行添加新字段，所以旧的列中会缺少新字段的数据信息，所以需要补齐
    */
    private DataResult makeUpColumnFieldMissing(Long viewId, Long rowId) {

        // 必要参数不存在
        if(viewId == null || rowId == null){
            return DataResult.createError(DataResultCode.PARAM_ERROR);
        }

        // 1. 得到 viewId 中所有字段
        List<FieldDO> fieldList = fieldMapper.selectList(new QueryWrapper<FieldDO>()
                .eq("view_id", viewId));
        // 2. 得到 rowId 中包含的所有字段
        List<ColumnDataDO> columnDataList = columnDataMapper.selectList(new QueryWrapper<ColumnDataDO>()
                .eq("row_id", rowId)
                .eq("view_id", viewId));

        // 将 ColumnDataList fieldId 转换 Map
        Map<Long, ColumnDataDO> fieldIdMap =
                columnDataList.stream().collect(Collectors.toMap(item -> item.getFieldId(), item -> item));

        // 3. 根据差异补齐
        List<FieldDO> differenceList = fieldList.stream().filter(item -> {
            // 检查
            ColumnDataDO columnDataInfo = fieldIdMap.get(item.getId());
            if (columnDataInfo != null) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        // 将  FieldDO 转换成 DataInfoDTO
        List<DataInfoDTO> dataInfoList = differenceList.stream().map(item -> {
            DataInfoDTO dataInfoInfo = new DataInfoDTO();
            dataInfoInfo.setFieldId(item.getId());
            dataInfoInfo.setValue("");
            return dataInfoInfo;
        }).collect(Collectors.toList());

        // 操作成功
        return createColumnData(viewId,rowId,dataInfoList);
    }

    /**
     * 修改相应的列值
     * @param rowId  修改的行ID
     * @param viewId 修改的视图ID
     * @param item   修改的字段信息
    */
    private boolean updateColumnData(Long rowId, Long viewId, DataInfoDTO item) {

        // 创建 update Item
        ColumnDataDO columnData = new ColumnDataDO();
        columnData.setValue(item.getValue());
        columnData.setUpdateDate(new Date());

        // 修改
        int updateCount = columnDataMapper.update(columnData,
                new QueryWrapper<ColumnDataDO>()
                        .eq("view_id", viewId)
                        .eq("row_id", rowId)
                        .eq("field_id", item.getFieldId()));

        // 结果
        return updateCount > 0;
    }

    /**
     * 校验 rowId 是否合法
     * @param rowId 行ID
     * @return 校验结果
    */
    private DataResult verifyRowId(Long rowId){
        // row ID 不存在
        if(rowId == null){
            return DataResult.createError(DataResultCode.ROW_ID_IS_NULL_ERROR);
        }

        // 检查数据是否存在
        RowDataDO rowDataInfo = rowDataMapper.selectById(rowId);
        if(rowDataInfo == null){
            return DataResult.createError(DataResultCode.ROW_NOT_EXIST_ERROR);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Page<DataInfoVO>> selectColumnDataRowIdList(List<Long> fieldList, List<String> conditionList,Long viewId, PaginationDTO pageInfo) {

        // 得到 rowId 总数
        Long rowCount = columnDataMapper.selectColumnDataRowIdCount(fieldList, conditionList,viewId);

        // 组装好的分页信息
        PaginationDTO paginationInfo = PageUtil.assemblyPage(pageInfo);

        // 得到分页好的 rowId
        List<Long> rowIdList = columnDataMapper.selectColumnDataRowIdList(fieldList, conditionList,viewId,paginationInfo);

        // 根据 rowId 查询结果
        List<RowDataDO> rowInfoList =
                rowDataMapper.selectList(new QueryWrapper<RowDataDO>().in("id", rowIdList.toArray()));

        // 构建 Page 并返回
        Page<DataInfoVO> pageResult = new Page<>();
        pageResult.setCurrent(paginationInfo.getPageNumber());
        pageResult.setTotal(rowCount);
        pageResult.setSize(rowIdList.size());

        // 返回结果
        return getColumnDataByRowId(rowInfoList,pageResult);
    }

    @Override
    public DataResult<Boolean> removeRowData(Long rowId) {

        // 校验 rowId 是否有效
        DataResult verifyResult = verifyRowId(rowId);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{

            // 删除
            int deleteCount = rowDataMapper.deleteById(rowId);

            // 返回结果
            return DataResult.createSuccess(deleteCount > 0);
        }catch (Exception e){
            log.error("DataManagerImpl#removeRowData",e);
            return DataResult.createDBError();
        }
    }
}
