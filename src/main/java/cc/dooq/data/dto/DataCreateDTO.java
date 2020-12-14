package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 10:45
 */
@Data
public class DataCreateDTO implements Serializable {

    /** 视图ID */
    private Long viewId;

    /** 创建的数据 */
    private List<DataInfoDTO> dataInfoList;
}
