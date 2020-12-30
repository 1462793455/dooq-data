package cc.dooq.data.util;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.dto.PaginationDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 13:52
 */
public class PageUtil {

    /**
     * 获取分页 Page 对象
    */
    public static <T> DataResult<Page<T>> page(PaginationDTO pagination,T t){
        // 检查并返回正确结果
        DataResult<PaginationDTO> defaultPaginationResult = getDefaultPagination(pagination);
        if(!defaultPaginationResult.isSuccess()){
            return DataResult.createError(defaultPaginationResult);
        }
        // 获得 分页信息
        PaginationDTO defaultPage = defaultPaginationResult.getData();
        // 生成并返回
        Page<T> objectPage = new Page<>(defaultPage.getPageNumber(), defaultPage.getPageSize());
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
        if(pageNumber == null || pageNumber <= 0){
            pagination.setPageNumber(CommonConstants.PAGE_DEFAULT_NUMBER);
        }
        // pageSize
        if(pageSize == null || pageSize <= 0 || pageSize > CommonConstants.PAGE_MAX_SIZE){
            pagination.setPageSize(CommonConstants.PAGE_DEFAULT_SIZE);
        }

        // 返回结果
        return DataResult.createSuccess(pagination);
    }

    /**
     * 当前页
     *
     * @param curPage
     * @return
     */
    public static Integer curPage(Integer curPage) {
        if (null == curPage) {
            curPage = 1;
        }
        return curPage <= 0 ? 1 : curPage;
    }

    /**
     * 每页大小
     *
     * @param pageSize
     * @return
     */
    public static Integer pageSize(Integer pageSize) {
        if (null == pageSize) {
            pageSize = CommonConstants.PAGE_DEFAULT_SIZE;
        }
        return pageSize < CommonConstants.PAGE_DEFAULT_SIZE || pageSize > CommonConstants.PAGE_MAX_SIZE ? CommonConstants.PAGE_DEFAULT_SIZE : pageSize;
    }

    /**
     * 分页起始位置计算
     * @param curPage 页码
     * @param pageSize 页大小
    */
    public static Integer getStart(Integer curPage, Integer pageSize) {
        if (null == curPage) {
            curPage = 1;
        }
        if (null == pageSize) {
            pageSize = CommonConstants.PAGE_DEFAULT_SIZE;
        }
        return (curPage(curPage) - 1) * pageSize(pageSize);
    }


    /**
     * 重置 page 结果集
    */
    public static <T> Page<T> resetPageData(Page target,List<T> records){

        // 直接赋值
        Page<T> sourcePage = target;
        sourcePage.setRecords(records);

        // 返回
        return sourcePage;
    }


    /**
     * 组装 page 参数
     * @param pageInfo 分页信息
     * @return 返回组装好的完善的 Page Info
    */
    public static PaginationDTO assemblyPage(PaginationDTO pageInfo) {
        // 不存在就赋值
        if(pageInfo == null){
            return new PaginationDTO(1,20);
        }

        PaginationDTO paginationDTO = new PaginationDTO(1,20);

        // 检查 pageInfo 参数，并修改成可以分页的参数
        paginationDTO.setPageSize(PageUtil.pageSize(pageInfo.getPageSize()));
        paginationDTO.setPageNumber(PageUtil.getStart(pageInfo.getPageNumber(),pageInfo.getPageSize()));

        // 返回结果
        return paginationDTO;
    }
}
