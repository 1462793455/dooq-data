package cc.dooq.data.controller;

import cc.dooq.data.dto.ViewCreateDTO;
import cc.dooq.data.dto.ViewUpdateDTO;
import cc.dooq.data.service.ViewService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewFieldInfoVO;
import cc.dooq.data.vo.ViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:34
 */
@RestController
@RequestMapping("/v1/api/view")
public class ViewController {

    @Autowired
    private ViewService viewService;

    /**
     * 创建新视图
    */
    @PostMapping("/create_view")
    public DataResult<Long> createView(@RequestBody ViewCreateDTO param){
        return viewService.createView(param);
    }

    /**
     * 删除视图,传入视图ID
     */
    @PostMapping("/remove_view")
    public DataResult<Boolean> removeView(@RequestParam(value = "viewId") Long viewId){
        return viewService.removeView(viewId);
    }

    /**
     * 修改视图
     */
    @PostMapping("/update_view")
    public DataResult<Boolean> updateView(@RequestBody ViewUpdateDTO param){
        return viewService.updateView(param);
    }

    /**
     * 查询视图列表
     */
    @GetMapping("/view_list")
    public DataResult<List<ViewVO>> viewList(){
        return viewService.selectAllView();
    }

}
