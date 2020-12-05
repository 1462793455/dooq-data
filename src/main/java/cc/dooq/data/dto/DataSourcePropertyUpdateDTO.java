package cc.dooq.data.dto;

import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:11
 */
@Data
public class DataSourcePropertyUpdateDTO extends DataSourcePropertyCreateDTO {

    /** 属性ID */
    public Long propertyId;

}
