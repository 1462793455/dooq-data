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
@TableName("t_field")
public class FieldDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer status;

    private Date createDate;

    private Date updateDate;

    /** 视图ID */
    private Long viewId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldDesc;

    /**
     * 字段类型 单选、多选等
     */
    private Integer fieldType;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 排序
     */
    private Integer orderDesc;

    /**
     * 字段宽度
     */
    private Integer fieldWidth;

    /**
     * 是否固定 1:固定 0:不固定  默认不固定
     */
    private Integer fixed;

    /**
     * 字段颜色Code
    */
    private Integer fieldColor;

}
