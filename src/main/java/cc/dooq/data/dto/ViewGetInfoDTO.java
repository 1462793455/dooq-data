package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/15 14:15
 */
@Data
public class ViewGetInfoDTO extends PaginationDTO implements Serializable {

    /** 视图名称 */
    private String viewName;

}
