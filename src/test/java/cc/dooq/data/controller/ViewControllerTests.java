package cc.dooq.data.controller;

import cc.dooq.data.BaseTest;
import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.dto.ViewUpdateDTO;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 23:20
 */
@AutoConfigureMockMvc
class CreateViewTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void 正确结果时(){

        // 参数
        ViewCreateDTO viewCreate = new ViewCreateDTO();
        viewCreate.setViewName("测试视图名称");
        viewCreate.setViewDesc("测试视图描述长度要够");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/create_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewCreate)))
                .andReturn());

    }

}

@AutoConfigureMockMvc
class RemoveViewTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void 正确结果时(){

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/remove_view")
                .contentType("application/json;charset=UTF-8")
                .param("viewId","1"))
                .andReturn());

    }

}

@AutoConfigureMockMvc
class SelectAllViewTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void 正确结果时(){

        // 发送请求
        response(this.mockMvc.perform(get("/v1/api/view/view_list")
                .contentType("application/json;charset=UTF-8"))
                .andReturn());

    }

}


@AutoConfigureMockMvc
class UpdateViewTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void 正确结果时(){

        // 参数
        ViewUpdateDTO viewUpdate = new ViewUpdateDTO();
        viewUpdate.setViewId(1L);
        viewUpdate.setViewDesc("描述描述描述描述");
        viewUpdate.setViewName("测试名称");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/update_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewUpdate)))
                .andReturn());

    }

    @Test
    @SneakyThrows
    public void 视图名称超长(){

        // 参数
        ViewUpdateDTO viewUpdate = new ViewUpdateDTO();
        viewUpdate.setViewId(1L);
        viewUpdate.setViewDesc("描述描述描述描述");
        viewUpdate.setViewName("测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/update_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewUpdate)))
                .andReturn());

    }

    @Test
    @SneakyThrows
    public void 视图名称超短(){

        // 参数
        ViewUpdateDTO viewUpdate = new ViewUpdateDTO();
        viewUpdate.setViewId(1L);
        viewUpdate.setViewDesc("描述描述描述描述");
        viewUpdate.setViewName("测");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/update_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewUpdate)))
                .andReturn());

    }

    @Test
    @SneakyThrows
    public void 视图描述超短(){

        // 参数
        ViewUpdateDTO viewUpdate = new ViewUpdateDTO();
        viewUpdate.setViewId(1L);
        viewUpdate.setViewDesc("描述");
        viewUpdate.setViewName("测三生三世搜索");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/update_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewUpdate)))
                .andReturn());

    }

    @Test
    @SneakyThrows
    public void 视图描述为空(){

        // 参数
        ViewUpdateDTO viewUpdate = new ViewUpdateDTO();
        viewUpdate.setViewId(1L);
        viewUpdate.setViewDesc("");
        viewUpdate.setViewName("测三生三世搜索");

        // 发送请求
        response(this.mockMvc.perform(post("/v1/api/view/update_view")
                .contentType("application/json;charset=UTF-8")
                .content(JSONObject.toJSONString(viewUpdate)))
                .andReturn());

    }

}


