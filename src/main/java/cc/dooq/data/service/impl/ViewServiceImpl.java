package cc.dooq.data.service.impl;

import cc.dooq.data.dto.DataSourceCreateDTO;
import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.dto.ViewUpdateDTO;
import cc.dooq.data.entity.ViewDO;
import cc.dooq.data.enums.DataStatusEnum;
import cc.dooq.data.manager.ViewManager;
import cc.dooq.data.mapper.ViewMapper;
import cc.dooq.data.service.ViewService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 22:45
 */
@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ViewManager viewManager;

    @Override
    public DataResult<Boolean> createView(ViewCreateDTO param) {
        if(param == null){
            param = new ViewCreateDTO();
        }
        ViewDO viewDO = new ViewDO();
        viewDO.setViewDesc(param.getViewDesc());
        viewDO.setViewName(param.getViewName());
        return viewManager.createView(viewDO);
    }

    @Override
    public DataResult<Boolean> removeView(Long viewId) {
        return viewManager.removeView(viewId);
    }

    @Override
    public DataResult<Boolean> updateView(ViewUpdateDTO param) {
        if(param == null){
            param = new ViewUpdateDTO();
        }
        ViewDO viewDO = new ViewDO();
        viewDO.setId(param.getViewId());
        viewDO.setViewDesc(param.getViewDesc());
        viewDO.setViewName(param.getViewName());
        return viewManager.updateView(viewDO);
    }

    @Override
    public DataResult<List<ViewVO>> selectAllView() {
        return viewManager.selectAllView();
    }
}
