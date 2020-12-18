package cc.dooq.data.service.impl;

import cc.dooq.data.dto.ColumnDataGetDTO;
import cc.dooq.data.dto.DataCreateDTO;
import cc.dooq.data.dto.DataUpdateDTO;
import cc.dooq.data.manager.DataManager;
import cc.dooq.data.service.DataService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.DataInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:30
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataManager dataManager;

    @Override
    public DataResult<Boolean> createData(DataCreateDTO param) {
        return dataManager.createData(param);
    }

    @Override
    public DataResult<Page<DataInfoVO>> getViewDataList(ColumnDataGetDTO param) {
        return dataManager.getViewDataList(param);
    }

    @Override
    public DataResult<Boolean> updateData(DataUpdateDTO param) {
        return dataManager.updateData(param
        );
    }

    @Override
    public DataResult<Boolean> removeRowData(List<Long> rowIds) {
        return dataManager.removeRowData(rowIds);
    }
}
