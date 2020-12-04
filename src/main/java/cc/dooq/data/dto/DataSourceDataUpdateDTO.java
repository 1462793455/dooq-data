package cc.dooq.data.dto;

import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:11
 */
@Data
public class DataSourceDataUpdateDTO extends DataSourceDataCreateDTO {

    /** 数据源数据ID */
    public Long sourceDataId;

}
