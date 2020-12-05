package cc.dooq.data.manager;

import cc.dooq.data.entity.DataSourcePropertyDO;
import cc.dooq.data.util.DataResult;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/5 15:16
 */
public interface DataSourcePropertyManager {

    /**
     * 查询指定数据源下的所有属性值
     * @param dataSourceId 数据源ID
     * @return DataResult<List<DataSourcePropertyDO>>
    */
    DataResult<List<DataSourcePropertyDO>> getDataSourcePropertyList(Long dataSourceId);

    /**
     * 为指定数据源创建一个属性值
     * @param param 参数
     * @return DataResult<Boolean>
    */
    DataResult<Boolean> createDataSourceProperty(DataSourcePropertyDO param);

    /**
     * 删除数据源属性ID所对应的属性
     * @param sourceDataId 数据源属性ID
     * @return DataResult<Boolean>
    */
    DataResult<Boolean> removeDataSourceProperty(Long sourceDataId);

    /**
     * 修改指定数据源下的属性值
     * @param param
     * @return DataResult<Boolean>
    */
    DataResult<Boolean> updateDataSourceProperty(DataSourcePropertyDO param);
}
