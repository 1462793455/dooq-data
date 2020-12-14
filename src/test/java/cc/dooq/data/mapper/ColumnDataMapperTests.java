package cc.dooq.data.mapper;

import cc.dooq.data.BaseTest;
import cc.dooq.data.dto.PaginationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/13 12:46
 */
public class ColumnDataMapperTests extends BaseTest {

    @Autowired
    private ColumnDataMapper columnDataMapper;

    @Test
    public void test(){

        List<Long> fieldList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();

        fieldList.add(23L);
//        fieldList.add(24L);

        valueList.add("value like '%2%'");

//        List<Long> longs = columnDataMapper.selectColumnDataRowIdList(fieldList, valueList,,new PaginationDTO(0,10));
//        System.out.println(longs);
    }

    @Test
    public void test1(){

        List<Long> fieldList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();

        fieldList.add(23L);
//        fieldList.add(24L);

        valueList.add("value like '%2%'");

//        Long aLong = columnDataMapper.selectColumnDataRowIdCount(fieldList, valueList);
//        System.out.println(aLong);

    }

}
