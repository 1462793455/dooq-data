package cc.dooq.data.service.impl;

import cc.dooq.data.dto.DataSourceCreateDTO;
import cc.dooq.data.dto.DataSourceGetDTO;
import cc.dooq.data.dto.DataSourceUpdateDTO;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.manager.DataSourceManager;
import cc.dooq.data.service.DataSourceService;
import cc.dooq.data.util.DataResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:48
 */
@Component
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceManager dataSourceManager;

    @Override
    public DataResult<Page<DataSourceDO>> getDataSourceByName(DataSourceGetDTO param) {
        return dataSourceManager.getDataSourceByName(param);
    }

    @Override
    public DataResult<Boolean> createDataSource(DataSourceCreateDTO param) {
        if(param == null){
            param = new DataSourceCreateDTO();
        }
        DataSourceDO dataSourceDO = new DataSourceDO();
        dataSourceDO.setSourceDesc(param.getSourceDesc());
        dataSourceDO.setSourceName(param.getSourceName());
        // 调用逻辑
        return dataSourceManager.createDataSource(dataSourceDO);
    }

    @Override
    public DataResult<Boolean> removeDataSource(Long dataSourceId) {
        return dataSourceManager.removeDataSource(dataSourceId);
    }

    @Override
    public DataResult<Boolean> updateDataSource(DataSourceUpdateDTO param) {
        if(param == null){
            param = new DataSourceUpdateDTO();
        }
        DataSourceDO dataSourceDO = new DataSourceDO();
        dataSourceDO.setId(param.getDataSourceId());
        dataSourceDO.setSourceDesc(param.getSourceDesc());
        dataSourceDO.setSourceName(param.getSourceName());
        // 调用逻辑
        return dataSourceManager.updateDataSource(dataSourceDO);
    }
}

