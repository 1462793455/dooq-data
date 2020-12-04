package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:41
 */
@Data
public class DataSourceCreateDTO implements Serializable {

    /** 数据源名称 */
    private String sourceName;

    /** 数据源描述 */
    private String sourceDesc;

}
