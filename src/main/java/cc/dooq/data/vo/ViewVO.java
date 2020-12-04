package cc.dooq.data.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:44
 */
@Data
public class ViewVO implements Serializable {

    /** 视图名称 */
    private String viewName;
    /** 视图描述 */
    private String viewDesc;


}
