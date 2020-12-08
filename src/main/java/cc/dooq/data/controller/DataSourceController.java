package cc.dooq.data.controller;

import cc.dooq.data.dto.*;
import cc.dooq.data.entity.DataSourceDO;
import cc.dooq.data.entity.DataSourcePropertyDO;
import cc.dooq.data.service.DataSourcePropertyService;
import cc.dooq.data.service.DataSourceService;
import cc.dooq.data.util.DataResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:32
 */
@RestController
@RequestMapping("/v1/api/data_source")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataSourcePropertyService dataSourcePropertyService;

    /**
     * 获取所有数据源列表，可通过 数据源名称筛选
     * TODO 分页还需要该一下
    */
    @GetMapping("/get_data_source_list")
    public DataResult<Page<DataSourceDO>> getDataSourceByName(DataSourceGetDTO param){
        return dataSourceService.getDataSourceByName(param);
    }

    /**
     * 新增一个数据源
     */
    @PostMapping("/create_data_source")
    public DataResult<Boolean> createDataSource(@RequestBody DataSourceCreateDTO param){
        return dataSourceService.createDataSource(param);
    }

    /**
     * 删除一个数据源
     */
    @PostMapping("/remove_data_source")
    public DataResult<Boolean> removeDataSource(@RequestParam(value = "dataSourceId") Long dataSourceId){
        return dataSourceService.removeDataSource(dataSourceId);
    }

    /**
     * 编辑数据源信息
     */
    @PostMapping("/update_data_source")
    public DataResult<Boolean> updateDataSource(@RequestBody DataSourceUpdateDTO param){
        return dataSourceService.updateDataSource(param);
    }

    /**
     * 查询数据源下的所有属性
     */
    @GetMapping("/get_data_source_property_list")
    public DataResult<List<DataSourcePropertyDO>> getDataSourcePropertyList(@RequestParam(value = "dataSourceId") Long dataSourceId){
        return DataResult.createSuccess();
    }

    /**
     * 向指定数据源添加一个属性
     */
    @PostMapping("/create_data_source_property")
    public DataResult<Boolean> createDataSourceProperty(@RequestBody DataSourcePropertyCreateDTO param){
        return DataResult.createSuccess();
    }

    /**
     * 修改数据源下某个属性的信息
     */
    @PostMapping("/update_data_source_property")
    public DataResult<Boolean> updateDataSourceProperty(@RequestBody DataSourcePropertyUpdateDTO param){
        return DataResult.createSuccess();
    }

    /**
     * 删除指定数据源下的某个属性
     */
    @PostMapping("/remove_data_source_property")
    public DataResult<Boolean> removeDataSourceProperty(@RequestParam("propertyDataId") Long propertyId){
        return DataResult.createSuccess();
    }

}
