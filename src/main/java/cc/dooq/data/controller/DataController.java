package cc.dooq.data.controller;

import cc.dooq.data.util.DataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:19
 */
@RestController
@RequestMapping("/v1/api/data")
public class DataController {

    /**
     * 得到某视图下的数据，支持根据不同类型字段进行筛选 支持分页，多条件一起筛选
     *
     * 查询列id 然后根据列id去找到所有该列的字段数据
     *
     * 无筛选时：
     * 直接到列表查询分页即可
     * 有筛选时：
     * 首先查询筛选列匹配的字段，获取到其中的列id
     * 然后拿列id获取所有的字段值
     *
     * // 1. 查询到数据匹配的列
     * select row_id from column_data where view_id = xx视图 ((field_id = xx列 and value = xxx值) and (field_id = xx列 and value = xxx值))
     * // 2. 查询到 row_id 对应的所有数据，根据 row_id 组装数据
     * // 3. 根据指定的排序字段进行排序
     * 这个时候已经组装好数据了 {"row_id":{xx:xx,xx:xx}}
     * 然后将该json串中的某字段进行排序即可
     *
    */
    @GetMapping("/get_list")
    public DataResult getViewDataList(){
        return DataResult.createSuccess();
    }

    /**
     * 删除指定ID的数据
    */
    @PostMapping("/remove_data")
    public DataResult removeData(){
        return DataResult.createSuccess();
    }

    /**
     * 修改指定ID的数据 需要校验每个值的类型是否正确
     */
    @PostMapping("/update_data")
    public DataResult updateData(){
        return DataResult.createSuccess();
    }

    /**
     * 新增指定视图中的数据 需要校验每个值的类型是否正确
     */
    @PostMapping("/create_data")
    public DataResult createData(){
        return DataResult.createSuccess();
    }


}
