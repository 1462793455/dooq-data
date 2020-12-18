package cc.dooq.data.util;

import cc.dooq.data.entity.ColumnDataDO;
import cc.dooq.data.mapper.ColumnDataMapper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/17 22:40
 */
public class DataUtil implements BeanFactoryAware {

    /** 容器 */
    private static BeanFactory beanFactory;

    /** Column Mapper */
    private static ColumnDataMapper columnDataMapper;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DataUtil.beanFactory = beanFactory;
    }


    /**
     * 得到当前 行ID 所对应的 列数据
     * @param rowId 行ID
     * @return  JSONObject 字段ID -> 字段值
    */
    public static JSONObject getRowJsonData(Long rowId){

        List<ColumnDataDO> columnDataDOS =
                columnDataMapper.selectList(new QueryWrapper<ColumnDataDO>().eq("row_id", rowId));

        JSONObject json = new JSONObject();
        if(columnDataDOS == null || columnDataDOS.isEmpty()){
            return json;
        }

        columnDataDOS.forEach(item -> {
            json.put(item.getFieldId() + "",item.getValue());
        });

        return json;
    }

}
