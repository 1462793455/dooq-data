package cc.dooq.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/4 16:39
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }

}
