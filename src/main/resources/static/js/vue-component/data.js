

function data() {
    return {
        // 屏幕高度变量，控制表格高度
        clientHeight: null,
        // 视图列表
        viewList:[],
        // 视图名称输入存储
        viewNameStorage:"",
        // 表格菜单 展示变量
        menuContextVisible: false,
        // 菜单选项存储
        menus:[],
        // 当前选择的视图ID
        currSelectViewInfo:null,
        // 支持的所有字段类型
        fieldTypes:null,
        // 支持的所有颜色
        colorList:null,
        // 新建字段信息存储
        fieldCreateInfo:getFieldInfo(),
        // 添加新字段模态框 展示控制变量
        createFieldDialogVisible: false,
        // 添加新数据源模态框 展示控制变量
        createDataSourceDialog:false,

        tableData: [ {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            tag: '家'
        },{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            tag: '公司'
        },{
            date: '2016-05-06',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            tag: '家'
        }, {
            date: '2016-05-07',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            tag: '家'
        }]
    }
}