package cc.dooq.data.vo;

import lombok.Data;

import java.io.*;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/7 20:14
 */
@Data
public class ColorInfoVO implements Serializable {

    /** 颜色十六进制表示 */
    private String color;
    /** 颜色编号 */
    private Integer colorCode;

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\14627\\Desktop\\新建文本文档.txt");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        StringBuffer stringBuffer = new StringBuffer();
        String s = null;
        while((s = bufferedReader.readLine()) != null){

            stringBuffer.append(s+",");
        }

        System.out.println(stringBuffer.toString());
    }
}
