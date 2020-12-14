package cc.dooq.data.service;

import cc.dooq.data.dto.FieldCreateDTO;
import cc.dooq.data.dto.FieldUpdateDTO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewFieldInfoVO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/7 15:23
 */
public interface FieldService {

    /**
     * 向指定视图增加一个字段
     * @param param 参数
     * @return DataResult<Boolean>
     */
    DataResult<Boolean> createViewField(FieldCreateDTO param);

    /**
     * 删除指定字段
     * @param fieldId
     * @return DataResult<Boolean>
     */
    DataResult<Boolean> removeViewField(Long fieldId);

    /**
     * 修改指定视图的指定字段
     * @param param
     * @return DataResult<Boolean>
     */
    DataResult<Boolean> updateViewField(FieldUpdateDTO param);

    /**
     * 获取单个字段详细信息
     * @param fieldId
     * @return DataResult<FieldDO>
     */
    DataResult<FieldDO> getFieldInfo(Long fieldId);

    /**
     * 查询该视图下所有字段信息
     * @param viewId 视图ID
     * @return DataResult<ViewFieldInfoVO>
     */
    DataResult<ViewFieldInfoVO> getViewFieldInfo(Long viewId);

}
