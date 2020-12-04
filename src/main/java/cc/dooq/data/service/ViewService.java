package cc.dooq.data.service;

import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.dto.ViewUpdateDTO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 22:45
 */
public interface ViewService {

    /**
     * 创建视图
     * @param param 视图参数
     * @return DataResult<Boolean> 结果
    */
    DataResult<Boolean> createView(ViewCreateDTO param);

    /**
     * 删除视图
     * @param viewId
     * @return  DataResult<Boolean> 结果
    */
    DataResult<Boolean> removeView(Long viewId);

    /**
     * 修改视图信息
     * @param param 修改信息
     * @return DataResult<Boolean> 修改结果
    */
    DataResult<Boolean> updateView(ViewUpdateDTO param);

    /**
     * 查询所有视图，没有分页和海选
     * @return DataResult<List<ViewVO>>
    */
    DataResult<List<ViewVO>> selectAllView();
}
