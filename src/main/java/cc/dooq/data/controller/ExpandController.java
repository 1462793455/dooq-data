package cc.dooq.data.controller;

import cc.dooq.data.enums.ColorDataEnum;
import cc.dooq.data.enums.FieldTypeEnum;
import cc.dooq.data.util.DataResult;
import cc.dooq.data.vo.ColorInfoVO;
import cc.dooq.data.vo.FieLdTypeInfoVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:02
 */
@RestController
@RequestMapping("/v1/api/expand")
public class ExpandController {

    /**
     * 获取支持的所有颜色信息
    */
    @GetMapping("/color_list")
    public DataResult<List<ColorInfoVO>> getColorList(){
        return DataResult.createSuccess(Arrays.stream(ColorDataEnum.values()).map(item -> {
            ColorInfoVO vo = new ColorInfoVO();
            vo.setColor(item.getColor());
            vo.setColorCode(item.getCode());
            return vo;
        }).collect(Collectors.toList()));
    }


    /**
     * 获取支持的字段类型
     */
    @GetMapping("/field_list")
    public DataResult<List<FieLdTypeInfoVO>> getFieldList(){
        return DataResult.createSuccess(Arrays.stream(FieldTypeEnum.values()).map(item -> {
            FieLdTypeInfoVO vo = new FieLdTypeInfoVO();
            vo.setFieldCode(item.getFieldCode());
            vo.setFieldName(item.getFieldName());
            vo.setIsEnum(item.getEnum());
            return vo;
        }).collect(Collectors.toList()));
    }

}
