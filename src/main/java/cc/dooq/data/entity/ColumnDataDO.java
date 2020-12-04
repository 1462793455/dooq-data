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
@TableName("t_column_data")
public class ColumnDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer status;

    private Date createDate;

    private Date updateDate;

    /** 行ID */
    private Long rowId;

    /** 视图ID */
    private Long viewId;

    /** 字段ID */
    private Long fieldId;

    /** 值 */
    private String value;

    /**
     * 列背景色，只支持固定的5种，颜色定义在代码中写死的 默认没有颜色
     */
    private Integer columnColorId;


}
