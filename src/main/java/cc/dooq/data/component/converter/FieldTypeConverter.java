package cc.dooq.data.component.converter;

import cc.dooq.data.enums.FieldTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:08
 */
@Component
@Slf4j
public class FieldTypeConverter implements Converter<String, FieldTypeEnum> {

    @Override
    public FieldTypeEnum convert(String s) {
        try{
            return FieldTypeEnum.getFieldTypeEnum(Integer.parseInt(s));
        }catch (Exception e){
            log.error("FieldTypeConverter#convert",e);
            return null;
        }
    }
}
