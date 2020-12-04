package cc.dooq.data.controller;

import cc.dooq.data.enums.ColorDataEnum;
import cc.dooq.data.util.DataResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:02
 */
@RestController
@RequestMapping("/v1/api/color")
public class ColorController {

    /**
     * 获取支持的所有颜色信息
    */
    @GetMapping("/color_list")
    public DataResult<JSONArray> getColorList(){
        return DataResult.createSuccess(JSONArray.parseArray(
                JSONObject.toJSONString(ColorDataEnum.values(), SerializerFeature.WriteEnumUsingToString)));
    }

}
