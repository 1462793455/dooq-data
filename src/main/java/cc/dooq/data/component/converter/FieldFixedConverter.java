package cc.dooq.data.component.converter;

import cc.dooq.data.enums.FieldFixedEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:19
 */
@Component
@Slf4j
public class FieldFixedConverter implements Converter<String, FieldFixedEnum> {

    @Override
    public FieldFixedEnum convert(String s) {
        try{
            return FieldFixedEnum.getFieldFixedEnum(Integer.parseInt(s));
        }catch (Exception e){
            log.error("FieldFixedConverter#convert",e);
            return null;
        }
    }
}
