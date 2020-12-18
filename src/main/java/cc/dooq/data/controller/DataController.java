package cc.dooq.data.controller;

import cc.dooq.data.dto.*;
import cc.dooq.data.service.DataService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.DataInfoVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:19
 */
@RestController
@RequestMapping("/v1/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/get_data_list")
    public DataResult<Page<DataInfoVO>> getViewDataList(@RequestBody ColumnDataGetDTO param){
        return dataService.getViewDataList(param);
    }

    /**
     * 删除行数据，只能按行删，不能按数据删
     * @param rowIds
    */
    @PostMapping("/remove_row_data")
    public DataResult<Boolean> removeRowData(@RequestBody List<Long> rowIds){
        return dataService.removeRowData(rowIds);
    }

    /**
     * 修改数据，需要传入行id 以及 单元格id 以及单元格值
     */
    @PostMapping("/update_data")
    public DataResult<Boolean> updateData(@RequestBody DataUpdateDTO param){
        return dataService.updateData(param);
    }

    /**
     * 新增指定视图中的数据 需要校验每个值的类型是否正确
     *
     * 添加时需要增加一条数据行，然后将这里传入的字段id 和字段值全部都绑定到该行
     * 其中，需要获取该视图下的所有字段，并为所有字段创建列数据
     */
    @PostMapping("/create_data")
    public DataResult<Boolean> createData(@RequestBody DataCreateDTO param){
        return dataService.createData(param);
    }


}
