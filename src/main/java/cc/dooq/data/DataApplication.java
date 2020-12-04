package cc.dooq.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Data 项目启动
 * @author 蛋清
*/
@SpringBootApplication
@MapperScan("cc.dooq.data.mapper")
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }


}