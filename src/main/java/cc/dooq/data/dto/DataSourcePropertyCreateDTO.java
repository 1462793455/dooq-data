package cc.dooq.data.dto;

import cc.dooq.data.enums.ColorDataEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:48
 */
@Data
public class DataSourcePropertyCreateDTO implements Serializable {

    /** 数据源Id */
    private Integer dataSourceId;

    /** 数据源数据名称 */
    private String propertyName;

    /** 数据源数据背景色 */
    private ColorDataEnum propertyColorId;

    /** 排序 */
    private Integer order;

    /** private String sourceKey; 数据源数据Key 该值后台随机生成一串可以使用的就行，不用输入 */

}
