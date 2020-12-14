

// 校验短文本
function verifyShortText(text){
    return text != null && text.length > SHORT_TEXT_MIN_LENGTH && text.length < SHORT_TEXT_MAX_LENGTH;
}

// 获取当前选中的视图信息
function getViewInfo(){
    return vm.currSelectViewInfo;
}

// 获取当前选中的视图ID
function getViewId(){
    return vm.currSelectViewInfo != null ? vm.currSelectViewInfo.viewId : null;
}

// 解析 vm.fieldCreateInfo 为 接口所需格式
function parseFieldCreateInfoForFieldInfo(){

    // 获得输入的内容
    let fieldCreateInfo = vm.fieldCreateInfo;

    // fieldTypeInfo -> fieldType
    let fieldTypeInfo = fieldCreateInfo.fieldTypeInfo;
    if(fieldTypeInfo != null){
        fieldCreateInfo["fieldType"] = fieldTypeInfo.fieldCode;
    }
    // fieldColorCode -> fieldColor
    let fieldColorCode = fieldCreateInfo.fieldColorCode
    if(fieldColorCode != null){
        fieldCreateInfo["fieldColor"] = fieldColorCode;
    }

    // 填充 视图ID
    fieldCreateInfo.viewId = getViewId();

    // 返回
    return fieldCreateInfo;
}

/**
 * 切换视图工具函数
 * 1、切换视图ID
 * 2、获取视图字段信息并组装
 * 3. 获取视图字段数据组装并提供基础列(多选、操作等)
*/
function $checkView(viewInfo){

    // 1. 切换视图信息
    vm.currSelectViewInfo = viewInfo;
    // 2. 刷新字段
    refreshFieldInfo(viewInfo.viewId);
    // 3. 刷新数据
    getColumnDataFunction();

}

// 刷新字段
function refreshFieldInfo(viewId){

    // 如果没有传入视图ID 代表刷新当前视图的字段信息
    if(viewId == null){
        if(vm.currSelectViewInfo == null){
            return;
        }
        // 赋值
        viewId = vm.currSelectViewInfo.viewId;
    }

    // 字段信息数组
    let columns = [];
    // 前系统字段
    beforeAddColumns(columns);

    // 2. 获取该视图下所有字段
    getViewFieldInfo(viewId,(viewFieldInfo)=>{

        // 得到字段信息Array
        let fieldArray = viewFieldInfo.fields;

        // 循环组装
        fieldArray.forEach((item)=>{
            columns.push(assembleFieldInfo(item));
        });

        // 后系统字段
        afterAddColumns(columns);

        // 设置进去
        vm.gridOptions.columns = columns;

    });
}

// 前字段
function beforeAddColumns(columns){
    return columns.push({ type: 'checkbox',
        // 是否允许拖动
        resizable:false ,system:true, title: '', width: 55 });
}
// 后字段
function afterAddColumns(columns){
    return columns.push(
        {
            field:"operate",
            system:true,
            // 是否允许拖动
            resizable:false ,
            title: '操作',
            // 使用 slot:operate 作为内容展示
            // slots: { default: 'operate' },
            cellRender:{name:"system-operate"},
            width: 140});
}

// 组装 字段信息
function assembleFieldInfo(fieldInfo){
    if(fieldInfo == null){
        return null;
    }

    try{

        // 字段名称
        let fieldName = fieldInfo.fieldName;

        // 字段描述
        let fieldDesc = fieldInfo.fieldDesc;

        // 字段类型处理器名称
        let rendererName = fieldInfo.rendererName;

        // 字段宽度
        let fieldWidth = fieldInfo.fieldWidth;

        // 字段颜色
        let fieldColor = fieldInfo.fieldColor;

        return  {field: fieldInfo.id, title: fieldName, width: fieldWidth ,editRender: { name: rendererName  }};

    }catch (e) {

    }



}





function dateFormat (time, format) {
    const t = new Date(parseInt(time));
    // 日期格式
    format = format || 'Y-m-d h:i:s'
    let year = t.getFullYear()
    // 由于 getMonth 返回值会比正常月份小 1
    let month = t.getMonth() + 1
    let day = t.getDate()
    let hours = t.getHours()
    let minutes = t.getMinutes()
    let seconds = t.getSeconds()

    const hash = {
        'y': year,
        'm': month,
        'd': day,
        'h': hours,
        'i': minutes,
        's': seconds
    }
    // 是否补 0
    const isAddZero = (o) => {
        return /M|D|H|I|S/.test(o)
    }
    return format.replace(/\w/g, o => {
        let rt = hash[o.toLocaleLowerCase()]
        return rt > 10 || !isAddZero(o) ? rt : `0${rt}`
    })
}

/**
 * 获取数据函数 ，其中就是操作 vm.selectData 数据
*/
function getColumnDataFunction(){

    // 查询数据
    let selectData = vm.selectData;

    // 当前使用的视图
    let viewId = vm.currSelectViewInfo.viewId;

    // 筛选条件 Map
    let filterMap = selectData.filter;
    // 当前页
    let pageNumber = selectData.pageNumber;
    // 当前页大小
    let pageSize = selectData.pageSize;

    // filterList 存储筛选条件
    let filterList = [];
    // filterMap 需要转换成功 filterList
    if(filterMap){
        // key = fieldId , value = conditionJson  转换成 {fileId:"",conditionJson:""}
        filterMap.forEach((value,key)=>{
            filterList.push({fileId:key,conditionJson:value});
        })
    }

    // 获取数据
    getColumnData({
        viewId,pageNumber,pageSize,filterCondition:filterList
    },(dataInfo)=>{

        console.info("数据",dataInfo);

        // 数据List
        let records = dataInfo.records;
        // 当前页
        let pageNumber = dataInfo.current;
        // 数据总数
        let totalRecordCount = dataInfo.total;
        // 页大小
        let pageSize = dataInfo.size;

        // 结果集设置
        vm.dataResult["resultList"] = records;
        // 当前页
        vm.dataResult["currPage"] = pageNumber;
        // 页大小
        vm.dataResult["currPageSize"] = pageSize;
        // 数据总数
        vm.dataResult["totalDataCount"] = totalRecordCount;
        // 页总数
        vm.dataResult["totalPage"] = totalRecordCount / pageSize;

        // 数据转换并展示到表格中
        let data = [];
        // 循环格式化数据
        records.forEach(item => {
            let fieldInfo = item.fieldInfo;
            fieldInfo["rowId"] = item.rowId;
            data.push(fieldInfo);
        });

        // 设置到表格中
        vm.gridOptions.data = data;
    });

}