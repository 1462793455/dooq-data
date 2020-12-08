package cc.dooq.data.service.impl;

import cc.dooq.data.dto.DataSourcePropertyCreateDTO;
import cc.dooq.data.dto.DataSourcePropertyUpdateDTO;
import cc.dooq.data.entity.DataSourcePropertyDO;
import cc.dooq.data.manager.DataSourcePropertyManager;
import cc.dooq.data.service.DataSourcePropertyService;
import cc.dooq.data.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/5 15:15
 */
@Service
public class DataSourcePropertyServiceImpl implements DataSourcePropertyService {

    @Autowired
    private DataSourcePropertyManager dataSourcePropertyManager;

    @Override
    public DataResult<List<DataSourcePropertyDO>> getDataSourcePropertyList(Long dataSourceId) {
        return dataSourcePropertyManager.getDataSourcePropertyList(dataSourceId);
    }

    @Override
    public DataResult<Boolean> createDataSourceProperty(DataSourcePropertyCreateDTO param) {
        if(param == null){
            param = new DataSourcePropertyCreateDTO();
        }
        DataSourcePropertyDO dataSourcePropertyDO = new DataSourcePropertyDO();
        dataSourcePropertyDO.setOrder(param.getOrder());
        dataSourcePropertyDO.setDataSourceId(param.getDataSourceId());
        dataSourcePropertyDO.setPropertyName(param.getPropertyName());
        if(param.getPropertyColorId() != null){
            dataSourcePropertyDO.setPropertyColorId(param.getPropertyColorId().getCode());
        }
        // 调用逻辑
        return dataSourcePropertyManager.createDataSourceProperty(dataSourcePropertyDO);
    }

    @Override
    public DataResult<Boolean> removeDataSourceProperty(Long propertyId) {
        return dataSourcePropertyManager.removeDataSourceProperty(propertyId);
    }

    @Override
    public DataResult<Boolean> updateDataSourceProperty(DataSourcePropertyUpdateDTO param) {
        if(param == null){
            param = new DataSourcePropertyUpdateDTO();
        }
        DataSourcePropertyDO dataSourcePropertyDO = new DataSourcePropertyDO();
        dataSourcePropertyDO.setId(param.getPropertyId());
        dataSourcePropertyDO.setOrder(param.getOrder());
        dataSourcePropertyDO.setDataSourceId(param.getDataSourceId());
        dataSourcePropertyDO.setPropertyName(param.getPropertyName());
        if(param.getPropertyColorId() != null){
            dataSourcePropertyDO.setPropertyColorId(param.getPropertyColorId().getCode());
        }
        // 调用逻辑
        return dataSourcePropertyManager.updateDataSourceProperty(dataSourcePropertyDO);
    }
}
