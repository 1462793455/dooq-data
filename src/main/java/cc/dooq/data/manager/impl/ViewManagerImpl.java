package cc.dooq.data.manager.impl;

import cc.dooq.data.constants.CommonConstants;
import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.entity.ViewDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.manager.ViewManager;
import cc.dooq.data.mapper.ViewMapper;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.util.DataResultCode;
import cc.dooq.data.vo.ViewVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 22:52
 */
@Component
@Slf4j
public class ViewManagerImpl implements ViewManager {

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public DataResult<Boolean> createView(ViewDO param) {
        // 参数校验
        DataResult verifyResult = verifyViewInfoParam(param);
        // 校验失败则直接返回错误信息
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }
        try{

            // 创建时间
            param.setCreateDate(new Date());
            // 数据有效状态
            param.setStatus(DataStatusEnum.VALID.getStatus());

            // 插入数据
            int insertCount = viewMapper.insert(param);
            // 返回插入结果
            return DataResult.createSuccess(insertCount > 0 ? true :false);
        } catch (Exception e){
            log.error("ViewManagerImpl#createView",e);
            return DataResult.createDBError();
        }
    }

    @Override
    public DataResult<Boolean> updateView(ViewDO param) {

        // 视图ID 不可为空
        DataResult verifyIdResult = verifyRemoveView(param.getId());
        // 校验失败则直接返回错误信息
        if(!verifyIdResult.isSuccess()){
            return DataResult.createError(verifyIdResult);
        }

        // 其他参数校验
        DataResult verifyInfoResult = verifyViewInfoParam(param);
        // 校验失败则直接返回错误信息
        if(!verifyInfoResult.isSuccess()){
            return DataResult.createError(verifyInfoResult);
        }

        try{

            // 修改时间
            param.setUpdateDate(new Date());

            // 修改
            int updateCount = viewMapper.updateById(param);
            // 返回修改结果
            return DataResult.createSuccess(updateCount > 0 ? true : false);
        }catch (Exception e){
            log.error("ViewManagerImpl#updateView",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 创建\修改视图方法参数校验
     * @param param 校验参数
     * @return DataResult 校验结果
    */
    private DataResult verifyViewInfoParam(ViewDO param) {
        // 参数校验
        if(param == null){
            return DataResult.createError(DataResultCode.PARAM_ERROR);
        }

        // 视图名称不允许为空
        String viewName = param.getViewName();
        // 为空时
        if(StringUtils.isBlank(viewName)){
            return DataResult.createError(DataResultCode.VIEW_NAME_IS_NULL_ERROR);
        }
        // 视图名称长度校验
        int viewNameLength = viewName.length();
        if(viewNameLength < CommonConstants.SHORT_TEXT_MIN_LENGTH
                || viewNameLength > CommonConstants.SHORT_TEXT_MAX_LENGTH){
            return DataResult.createError(DataResultCode.VIEW_NAME_LENGTH_OUT_ERROR);
        }

        // 检查是否存在相同的视图名称
        Integer sameViewNameCount = viewMapper.selectCount(new QueryWrapper<ViewDO>().eq("view_name", viewName));
        if(sameViewNameCount != null && sameViewNameCount > 0){
            return DataResult.createError(DataResultCode.VIEW_NAME_EXIST_SAME_ERROR);
        }

        // 视图描述，允许为空，如不为空时需要在 LONG_TEXT 范围内
        String viewDesc = param.getViewDesc();
        if(StringUtils.isNotBlank(viewDesc) && (viewDesc.length() > CommonConstants.LONG_TEXT_MAX_LENGTH
                || viewDesc.length() < CommonConstants.LONG_TEXT_MIN_LENGTH )){
            return DataResult.createError(DataResultCode.VIEW_DESC_LENGTH_OUT_ERROR);
        }

        // 返回校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<Boolean> removeView(Long viewId) {
        // 参数校验
        DataResult verifyResult = verifyRemoveView(viewId);
        if(!verifyResult.isSuccess()){
            return DataResult.createError(verifyResult);
        }

        try{
            // 删除
            int deleteCount = viewMapper.deleteById(viewId);
            return DataResult.createSuccess(deleteCount > 0 ? true : false);
        } catch (Exception e){
            log.error("ViewManagerImpl#removeView",e);
            return DataResult.createDBError();
        }
    }

    /**
     * 校验 RemoveView 方法参数
     * @param viewId 视图ID
     * @return DataResult 校验结果
    */
    private DataResult verifyRemoveView(Long viewId) {
        // 视图ID 不允许为空
        if(viewId == null){
            return DataResult.createError(DataResultCode.VIEW_ID_IS_NULL_ERROR);
        }

        // 检查该视图是否存在
        ViewDO viewInfo = viewMapper.selectById(viewId);
        // 视图不存在，删除失败
        if(viewInfo == null){
            return DataResult.createError(DataResultCode.VIEW_IS_NULL_ERROR);
        }

        // 校验成功
        return DataResult.createSuccess();
    }

    @Override
    public DataResult<List<ViewVO>> selectAllView() {
        // 查询
        List<ViewDO> views = viewMapper.selectList(new QueryWrapper<ViewDO>());
        // 没有找到
        if(views == null || views.size() <= 0){
            return DataResult.createSuccess();
        }

        // DO -> VO 转换
        List<ViewVO> vos = views.stream().map(item -> {
            ViewVO viewVO = new ViewVO();
            BeanUtils.copyProperties(item, viewVO);
            return viewVO;
        }).collect(Collectors.toList());

        // 返回结果
        return DataResult.createSuccess(vos);
    }
}
