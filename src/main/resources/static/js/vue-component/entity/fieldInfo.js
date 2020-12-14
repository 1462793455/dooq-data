
// 字段信息
function getFieldInfo(){
    return {
        // 字段名称
        fieldName:null,
        // 字段描述
        fieldDesc:null,
        // 是否固定 默认不固定
        fixed:0,
        // 字段类型信息
        fieldTypeInfo:null,
        // 字段颜色
        fieldColorCode:null,
        // 视图id 这个需要在方法里插入
        viewId:null,
    };
}

// 重置
function resetFieldCreateInfo(){
    vm.fieldCreateInfo = getFieldInfo();
}