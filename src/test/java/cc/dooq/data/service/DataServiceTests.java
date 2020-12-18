package cc.dooq.data.service;

import cc.dooq.data.BaseTest;
import cc.dooq.data.dto.DataCreateDTO;
import cc.dooq.data.dto.DataInfoDTO;
import cc.dooq.data.util.DataResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/16 23:56
 */
public class DataServiceTests extends BaseTest {

    @Autowired
    private DataService dataService;

    @SneakyThrows
    @Test
    public void test1(){

        List total = new ArrayList();

        File file = new File("C:\\Users\\14627\\Desktop\\abc\\abc");
        File[] files = file.listFiles();
        Arrays.asList(files).stream().forEach(itemJSON -> {
            System.out.println("======================" + itemJSON.getName());
            try {
                InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream(itemJSON));

                char[] chars = new char[128];
                StringBuffer str = new StringBuffer();
                while(fileInputStream.read(chars) != -1){
                    str.append(chars);
                    chars = new char[128];
                }

                String jsonString = str.toString();

                JSONObject jsonObject = JSONObject.parseObject(jsonString);

                JSONArray content = jsonObject.getJSONArray("content");

                int size = content.size();

                List list = new ArrayList();

                content.forEach(item -> {

                    JSONObject jsonObject1 = (JSONObject)item;

                    String wechat = jsonObject1.getString("wechat");
                    String shopName = jsonObject1.getString("shopName");
                    String phone = jsonObject1.getString("phone");

                    // 电话为空则不处理
                    if(StringUtils.isBlank(phone)){
                        return;
                    }

                    List<DataInfoDTO> dataInfos = new ArrayList<>();
                    if(StringUtils.isNotBlank(shopName)){
                        // 店铺名称
                        dataInfos.add(new DataInfoDTO(48L,shopName));
                    }
                    if(StringUtils.isNotBlank(phone)){
                        // 电话
                        dataInfos.add(new DataInfoDTO(49L,phone));
                    }
                    if(StringUtils.isNotBlank(wechat)){
                        // 微信
                        dataInfos.add(new DataInfoDTO(50L,wechat));
                    }

                    if(dataInfos.size() <= 0){
                        return;
                    }

                    DataCreateDTO dataCreateDTO = new DataCreateDTO();
                    dataCreateDTO.setViewId(22L);
                    dataCreateDTO.setDataInfoList(dataInfos);

                    DataResult<Boolean> data = dataService.createData(dataCreateDTO);
                    if(data.isSuccess()){
                        list.add("");
                    } else {
                        System.out.println("数据添加错误：" + data.getMessage());
                    }

                });

                System.out.println("文件名：" + itemJSON.getName() + " 数据总数：" + size + " 成功总数：" + list.size());

                total.addAll(list);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("数据总共添加：" + total.size());

    }

}
