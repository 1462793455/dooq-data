package cc.dooq.data.controller;

import cc.dooq.data.BaseTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 0:43
 */
@AutoConfigureMockMvc
public class ColorControllerTests extends BaseTest {

    @Autowired
    MockMvc mock;

    @Test
    @SneakyThrows
    public void 获取所有颜色(){

        response(this.mock.perform(get("/v1/api/color/color_list")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn());

    }

}
