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
        editData:getEditData(),
        // 查询条件存储
        selectData: getDefaultSelectData(),
        // 表格数据加载状态
        tableDataLoading:false,
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
        // 原始 字段配置
        originalColumn:[],
        // 当前选中的数据
        selectedRowDataArrays:[],
        // 筛选字段展示数组
        checkShowFilterCheckBoxArray:[],
        // 列展示字段数据
        checkColumnVisibleCheckBoxArray:[],
        // 单元格配置
        gridOptions: {
            // 表格大小
            size:"small",
            // 是否需要边框
            border: true,
            // 列宽是否允许调整
            resizable: true,
            // 表头过长时是否展示省略号
            showHeaderOverflow: true,
            // 内容过长时是否展示省略号
            showOverflow: true,
            // 移动到当列是否高亮显示
            highlightHoverRow: true,
            // 高亮当前行
            highlightCurrentRow: true,
            // 高亮当前列
            highlightCurrentColumn: true,
            // 鼠标移到列是否要高亮显示
            highlightHoverColumn: true,
            // 斑马纹
            // stripe:true,
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
            },
            // 行样式设定
            rowStyle({ row, rowIndex, $rowIndex }){
                // 行信息
                let rowInfo = row["_rowInfo"];
                if(rowInfo && rowInfo["rowColorId"]){
                    // 颜色信息
                    let colorInfo = vm.colorList[rowInfo["rowColorId"]];
                    if(colorInfo){
                        return {
                            backgroundColor: colorInfo.color,
                        }
                    }
                }
            },
            // 单元格样式设定,这里 列和单元格样式都在处理
            // 系统列无法上色
            // 样式优先级为 单元格 > 行
            cellStyle({ row, rowIndex, $rowIndex, column, columnIndex, $columnIndex }){
                // 字段ID
                let fieldId = column.property;
                // 字段详细信息
                let fieldInfo = row["_" + fieldId];
                if(fieldInfo && fieldInfo["columnColorId"]){
                    // 颜色信息
                    let colorInfo = vm.colorList[fieldInfo["columnColorId"]];
                    if(colorInfo){
                        return {
                            backgroundColor: colorInfo.color,
                        }
                    }
                }

            },
            // 快捷菜单配置
            menuConfig:{
                // 单元格/行 菜单
                body: {
                    options: [
                        [
                            { code: '置顶', name: '置顶' },
                            { code: '取消置顶', name: '取消置顶' },
                            { code: '设置单元格背景色', name: '设置单元格背景色' },
                            { code: '设置行背景色', name: '设置行背景色', record: { name: '默认名称' } }
                        ]
                    ]
                },
                // 字段/列菜单
                header:{
                    options: [
                        [
                            { code: 'exportData', name: '删除字段' },
                            { code: 'exportData', name: '固定' },
                            { code: 'exportData', name: '删除字段' },
                            { code: 'insert', name: '修改字段名称', record: { name: '默认名称' } }
                        ]
                    ]
                }
            },
            // 列宽调整时
            resizableChange({ $rowIndex, column, columnIndex, $columnIndex, $event }){
                console.info(column.property,column.renderWidth);
            },
            // 手动选择数据时
            checkboxChange({ records, reserves, indeterminates, checked, row, rowIndex, $rowIndex, column, columnIndex, $columnIndex, $event }){
               setSelectedRowDataArrays(records);
            },
            // 全选数据时
            checkboxAll({ records, reserves, indeterminates, checked, $event }){
                setSelectedRowDataArrays(records);
            },
            // 拖动选中时
            checkboxRangeChange({ records, reserves, $event }){
                setSelectedRowDataArrays(records);
            }
        },

    }
}


function getEditData(){
    return {
        // dataInput 方法 在操作
        viewId:null, // 视图ID
        fields:[] // 修改的字段、值信息
    };
}