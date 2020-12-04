package cc.dooq.data.controller;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 14:43
 */

import cc.dooq.data.BaseTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
class getDataSourceByNameTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void 正确结果(){

        response(this.mockMvc.perform(get("/v1/api/data_source/get_data_source_list")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .param("dataSourceName","测试"))
        .andReturn());
    }

}

