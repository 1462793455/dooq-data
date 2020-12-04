package cc.dooq.data.manager;

import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.entity.ViewDO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewVO;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 22:52
 */
public interface ViewManager {

    /**
     * 创建视图
     * @param param
     * @return DataResult<Boolean>
     */
    DataResult<Boolean> createView(ViewDO param);

    /**
     * 删除指定视图
     * @param viewId 视图ID
     * @return DataResult<Boolean>
     */
    DataResult<Boolean> removeView(Long viewId);

    /**
     * 修改视图
     * @param param 修改信息
     * @return DataResult<Boolean> 修改结果
    */
    DataResult<Boolean> updateView(ViewDO param);

    /**
     * 查询所有视图，没有分页和筛选
     * @return DataResult<List<ViewVO>>
    */
    DataResult<List<ViewVO>> selectAllView();
}
