package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:39
 */
@Data
public class ViewCreateDTO implements Serializable {

    /** 视图名称 */
    private String viewName;

    /** 视图描述 */
    private String viewDesc;

}
