package cc.dooq.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 10:50
 */
@Data
public class DataInfoDTO implements Serializable {

    /** 字段Id */
    private Long fieldId;

    /** 字段值 */
    private String value;

    public DataInfoDTO(Long fieldId, String value) {
        this.fieldId = fieldId;
        this.value = value;
    }

    public DataInfoDTO() {
    }
}
