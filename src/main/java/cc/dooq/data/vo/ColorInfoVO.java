package cc.dooq.data.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/7 20:14
 */
@Data
public class ColorInfoVO implements Serializable {

    /** 颜色十六进制表示 */
    private String color;
    /** 颜色编号 */
    private Integer colorCode;

}
