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
        // 当前选择的视图信息
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
        // 添加新数据模态框 展示控制变量
        editDataDialog:false,
        // 正在编辑的数据，其中对象固定 {viewId:"视图ID",fields:[{fieldId:"所属字段Id","value":"数据值"}]  }
        editData:{ // dataInput 方法 在操作
            viewId:null, // 视图ID
            fields:[] // 修改的字段、值信息
        },
        // 查询条件存储
        selectData:{
            // 筛选条件 map  其中 字段id -> 条件对象如：{startTime:"xx","endTime":"xx"}
            // id:{xxx:xxx},id:{xxx:xxx}
            filter:[],
            pageNumber:1,            // 当前页
            pageSize:20,              // 当前页大小
        },
        // 数据信息存储
        dataResult:{
            resultList:[],// 结果集
            currPage:null, // 当前页
            currPageSize:null,//页大小
            totalPage:null,         // 页总数
            totalDataCount:null,    // 数据总数
        },
        // 编辑模式  0 : 添加 1: 修改 2：查看
        editMode: 0,
        // 当前编辑行ID
        editRowId:null,
        // 单元格配置
        gridOptions: {
            border: true,
            // 列宽是否允许调整
            resizable: true,
            // 表头过长时是否展示省略号
            showHeaderOverflow: true,
            // 内容过长时是否展示省略号
            showOverflow: true,
            // 移动到当列是否高亮显示
            highlightHoverRow: true,
            // 斑马纹
            stripe:true,
            // 字段配置
            columns: [],
            // 数据
            data:[],
            keepSource: true,
            // 可编辑单元格配置
            editConfig: {
                // 开启编辑方式 dblclick 双击
                trigger: 'dbl1click',
                // 编辑方式 cell 单元格 row 行
                mode: 'cell',
                // 是否展示列头标志
                showIcon: false
            },
            // 筛选配置
            filterConfig:{
                // 服务端进行筛选
                // remote:true,
                showIcon:true,
            }
        },

    }
}