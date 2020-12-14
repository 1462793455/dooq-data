package cc.dooq.data.service.impl;

import cc.dooq.data.dto.FieldCreateDTO;
import cc.dooq.data.dto.FieldDefaultDTO;
import cc.dooq.data.dto.FieldUpdateDTO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.manager.FieldManager;
import cc.dooq.data.service.FieldService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewFieldInfoVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/7 15:23
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldManager fieldManager;

    @Override
    public DataResult<Boolean> createViewField(FieldCreateDTO param) {
        FieldDO fieldDO = buildField(param);
        if(param.getFieldType() != null){
            fieldDO.setFieldType(param.getFieldType());
        }
        // 调用逻辑
        return fieldManager.createViewField(fieldDO);
    }

    @Override
    public DataResult<Boolean> removeViewField(Long fieldId) {
        return fieldManager.removeViewField(fieldId);
    }

    @Override
    public DataResult<Boolean> updateViewField(FieldUpdateDTO param) {
        FieldDO fieldDO = buildField(param);
        fieldDO.setId(param.getFieldId());
        return fieldManager.updateViewField(fieldDO);
    }

    /**
     * 组装 FieldDO
    */
    public FieldDO buildField(FieldDefaultDTO param){
        if(param == null){
            param = new FieldCreateDTO();
        }
        FieldDO fieldDO = new FieldDO();
        fieldDO.setFieldName(param.getFieldName());
        fieldDO.setFieldDesc(param.getFieldDesc());
        fieldDO.setFieldWidth(param.getFieldWidth());
        fieldDO.setViewId(param.getViewId());
        fieldDO.setDataSourceId(param.getDataSourceId());
        fieldDO.setOrderDesc(param.getOrderDesc());
        if(param.getFieldColor() != null){
            fieldDO.setFieldColor(param.getFieldColor().getCode());
        }
        if(param.getFixed() != null){
            fieldDO.setFixed(param.getFixed().getCode());
        }
        return fieldDO;
    }

    @Override
    public DataResult<FieldDO> getFieldInfo(Long fieldId) {
        return fieldManager.getFieldInfo(fieldId);
    }

    @Override
    public DataResult<ViewFieldInfoVO> getViewFieldInfo(Long viewId) {
        return fieldManager.getViewFieldInfo(viewId);
    }

}
