package cc.dooq.data.dto;

import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 14:35
 */
@Data
public class DataSourceGetDTO extends PaginationDTO{

    /** 数据源名称 */
    private String dataSourceName;

}
