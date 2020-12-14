package cc.dooq.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-12-03
 */
@Data
@TableName("t_data_source_property")
public class DataSourcePropertyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer status;

    private Date createDate;

    private Date updateDate;

    private Long dataSourceId;

    private String propertyName;

    private String propertyKey;

    /**
     * 属性背景色
     */
    private Integer propertyColorId;



}
