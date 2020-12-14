package cc.dooq.data.controller;

import cc.dooq.data.dto.FieldCreateDTO;
import cc.dooq.data.dto.FieldUpdateDTO;
import cc.dooq.data.entity.FieldDO;
import cc.dooq.data.service.FieldService;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ViewFieldInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 17:49
 */
@RestController
@RequestMapping("/v1/api/field")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    /**
     * 向指定视图增加一个字段
    */
    @PostMapping("/create_view_field")
    public DataResult<Boolean> createViewField(@RequestBody FieldCreateDTO param){
        return fieldService.createViewField(param);
    }

    /**
     * 删除指定字段
     */
    @PostMapping("/remove_view_field")
    public DataResult<Boolean> removeViewField(@RequestParam("fieldId") Long fieldId){
        return fieldService.removeViewField(fieldId);
    }

    /**
     * 修改指定视图的指定字段
     */
    @PostMapping("/update_view_field")
    public DataResult<Boolean> updateViewField(@RequestBody FieldUpdateDTO param){
        return fieldService.updateViewField(param);
    }

    /**
     * 查询字段信息详细信息
     */
    @GetMapping("/get_field_info")
    public DataResult<FieldDO> getFieldInfo(@RequestParam("fieldId") Long fieldId){
        return fieldService.getFieldInfo(fieldId);
    }

    /**
     * 查询视图字段信息
     */
    @GetMapping("/get_view_field_info")
    public DataResult<ViewFieldInfoVO> getViewFieldInfo(@RequestParam(value = "viewId",required = true) Long viewId){
        return fieldService.getViewFieldInfo(viewId);
    }
}
