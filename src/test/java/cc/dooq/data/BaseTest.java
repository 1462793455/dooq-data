package cc.dooq.data;

import lombok.SneakyThrows;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 23:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataApplication.class)
public class BaseTest {

    @SneakyThrows
    public static void response(MvcResult mvcResult){
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        System.out.println(response.getContentAsString());
    }

}
