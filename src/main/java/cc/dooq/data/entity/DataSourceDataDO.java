package cc.dooq.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-12-03
 */
@Data
@TableName("t_data_source_data")
public class DataSourceDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer status;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Long dataSourceId;

    private String sourceName;

    private String sourceKey;

    /**
     * 数据源背景色
     */
    private Integer sourceColorId;

    /**
     * 排序
     */
    private Integer order;


}
