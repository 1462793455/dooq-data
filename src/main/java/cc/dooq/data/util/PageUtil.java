package cc.dooq.data.util;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.dto.PaginationDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:52
 */
public class PageUtil {

    /**
     * 获取分页 Page 对象
    */
    public static DataResult<Page> page(PaginationDTO pagination){
        // 检查并返回正确结果
        DataResult<PaginationDTO> defaultPaginationResult = getDefaultPagination(pagination);
        if(!defaultPaginationResult.isSuccess()){
            return DataResult.createError(defaultPaginationResult);
        }
        // 获得 分页信息
        PaginationDTO defaultPage = defaultPaginationResult.getData();
        // 生成并返回
        Page objectPage = new Page<>(defaultPage.getPageNumber(), defaultPage.getPageSize());
        objectPage.setSearchCount(true);
        // 返回
        return DataResult.createSuccess(objectPage);
    }

    /**
     * 返回经过校验的 Pagination
    */
    private static DataResult<PaginationDTO> getDefaultPagination(PaginationDTO pagination) {
        // 分页参数不存在时
        if(pagination == null){
            pagination = new PaginationDTO(CommonConstants.PAGE_DEFAULT_NUMBER,
                    CommonConstants.PAGE_DEFAULT_SIZE);
        }

        // 检查是否合法
        Integer pageNumber = pagination.getPageNumber();
        Integer pageSize = pagination.getPageSize();

        // 默认等于 1
        if(pageNumber == null || pageNumber >= 0){
            pagination.setPageNumber(CommonConstants.PAGE_DEFAULT_NUMBER);
        }
        // pageSize
        if(pageSize == null || pageSize >= 0 || pageSize > CommonConstants.PAGE_MAX_SIZE){
            pagination.setPageSize(CommonConstants.PAGE_DEFAULT_SIZE);
        }

        // 返回结果
        return DataResult.createSuccess(pagination);
    }

}
