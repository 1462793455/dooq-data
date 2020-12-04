package cc.dooq.data.component.converter;

import cc.dooq.data.enums.ColorDataEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 19:09
 */
@Component
@Slf4j
public class ColorDataConverter implements Converter<String, ColorDataEnum> {

    @Override
    public ColorDataEnum convert(String s) {
        try{
            return ColorDataEnum.getColorDataEnum(Integer.parseInt(s));
        }catch (Exception e){
            log.error("ColorDataConverter#convert",e);
            return null;
        }
    }
}
