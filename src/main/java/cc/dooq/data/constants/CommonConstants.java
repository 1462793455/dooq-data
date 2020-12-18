package cc.dooq.data.constants;

/**
 * @author 蛋清
 * @Description: 常量类
 * @date 2020/12/3 22:57
 */
public class CommonConstants {

    /** 分页 页码 默认值 */
    public static final int PAGE_DEFAULT_NUMBER = 1;

    /** 分页 页大小 默认值 */
    public static final int PAGE_DEFAULT_SIZE = 20;

    /** 分页 页大小 最大值 */
    public static final int PAGE_MAX_SIZE = 200;

    /** 短文本最长长度限制 常用于名称等场景 */
    public static final int SHORT_TEXT_MAX_LENGTH = 20;

    /** 短文本最短长度限制 常用于名称等场景 */
    public static final int SHORT_TEXT_MIN_LENGTH = 1;

    /** 长文本最长长度限制 常用于描述等场景*/
    public static final int LONG_TEXT_MAX_LENGTH = 300;

    /** 长文本最长长度限制 常用于描述等场景*/
    public static final int LONG_TEXT_MIN_LENGTH = 1;

    /** 数据源无法删除提示语句 */
    public static final String DATA_SOURCE_EXIST_USE_NOT_REMOVE_MESSAGE = "%s 以上视图下的字段中，正在使用该数据源，无法删除!";
    public static final String DATA_SOURCE_EXIST_USE_NOT_REMOVE_MESSAGE_ITEM = "[%s 视图 >> %s 字段]";

}
