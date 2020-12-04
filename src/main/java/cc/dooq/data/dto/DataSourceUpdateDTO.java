package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:43
 */
@Data
public class DataSourceUpdateDTO extends DataSourceCreateDTO{

    /** 数据源Id */
    private Long dataSourceId;


}
