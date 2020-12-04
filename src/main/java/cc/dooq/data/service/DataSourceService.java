package cc.dooq.data.service;

import cc.dooq.data.dto.DataSourceCreateDTO;
import cc.dooq.data.dto.DataSourceGetDTO;
import cc.dooq.data.dto.DataSourceUpdateDTO;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.util.DataResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:48
 */
public interface DataSourceService {

    /**
     * 批量获取数据源信息，支持分页以及 数据源名称筛选
     * @param param 查询参数
     * @return DataResult<List<DataSourceDO>>
    */
    DataResult<Page<DataSourceDO>> getDataSourceByName(DataSourceGetDTO param);

    /**
     * 创建一个数据源
     * @param param 参数
     * @return DataResult<Boolean>
    */
    DataResult<Boolean> createDataSource(DataSourceCreateDTO param);

    /**
     * 删除一个数据源，根据数据源ID
     * @param dataSourceId 数据源ID
     * @return DataResult<Boolean> 结果
    */
    DataResult<Boolean> removeDataSource(Long dataSourceId);

    /**
     * 修改一个数据源，根据数据源ID
     * @param param 修改参数
     * @return DataResult<Boolean>
    */
    DataResult<Boolean> updateDataSource(DataSourceUpdateDTO param);
}
