package cc.dooq.data.service;

import cc.dooq.data.dto.ColumnDataGetDTO;
import cc.dooq.data.dto.DataCreateDTO;
import cc.dooq.data.dto.DataUpdateDTO;
import cc.dooq.data.dto.PaginationDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.DataInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:29
 */
public interface DataService {

    /**
     * 创建数据
     * @param param 数据参数
     * @return DataResult<Boolean> 创建结果
    */
    DataResult<Boolean> createData(DataCreateDTO param);

    /**
     *  获取指定视图下的数据
     * @param param 查询条件
     * @return 查询结果
    */
    DataResult<Page<DataInfoVO>> getViewDataList(ColumnDataGetDTO param);

    /**
     * 修改数据(需要检查 row 是否有效)
     * @param param 修改数据参数
     * @return 修改结果
    */
    DataResult<Boolean> updateData(DataUpdateDTO param);

    /**
     * 删除  row 数据
     * @param rowId 行ID
     * @return 结果
    */
    DataResult<Boolean> removeRowData(Long rowId);
}
