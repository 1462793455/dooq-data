package cc.dooq.data.component.converter;

import cc.dooq.data.enums.FieldTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/3 18:08
 */
@Component
@Slf4j
public class FieldTypeConverter implements Converter<Integer, FieldTypeEnum> {

    @Override
    public FieldTypeEnum convert(Integer s) {
        try{
            return FieldTypeEnum.getFieldTypeEnum(s);
        }catch (Exception e){
            log.error("FieldTypeConverter#convert",e);
            return null;
        }
    }
}
