package cc.dooq.data.mapper;

import cc.dooq.data.dto.PaginationDTO;
import cc.dooq.data.entity.ColumnDataDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-12-03
 */
@Mapper
public interface ColumnDataMapper extends BaseMapper<ColumnDataDO> {

    /**
     * 条件查询 Column Data
     * @param fieldList 匹配字段ID集合
     * @param conditionList 值查询条件集合
     * @return 匹配的 rowId
    */
    List<Long> selectColumnDataRowIdList(List<Long> fieldList, List<String> conditionList,Long viewId, PaginationDTO page);

    /**
     * 条件查询 Column Data 总数
     * @param fieldList 匹配字段ID集合
     * @param conditionList 值查询条件集合
     * @return 匹配的 rowId
     */
    Long selectColumnDataRowIdCount(List<Long> fieldList,List<String> conditionList,Long viewId);

}
