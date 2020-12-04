package cc.dooq.data.enums;

/**
 * @author 蛋清
 * @Description: 数据有效/无效状态
 * @date 2020/12/3 22:48
 */
public enum  DataStatusEnum {

    // 有效
    VALID(1),
    // 无效
    INVALID(0)
    ;

    private Integer status;

    DataStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
