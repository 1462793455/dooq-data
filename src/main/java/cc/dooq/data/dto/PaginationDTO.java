package cc.dooq.data.dto;

import lombok.Data;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:53
 */
@Data
public class PaginationDTO {

    /** 页大小 */
    private Integer pageSize;

    /** 页码 */
    private Integer pageNumber;

    public PaginationDTO() {
    }

    public PaginationDTO(Integer pageNumber, Integer pageSize) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}
