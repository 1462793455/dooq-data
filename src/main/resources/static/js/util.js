

// 校验短文本
function verifyShortText(text){
    return text != null && text.length > SHORT_TEXT_MIN_LENGTH && text.length < SHORT_TEXT_MAX_LENGTH;
}

// 获取当前选中的视图信息
function getViewInfo(){
    return globalVue.currSelectViewInfo;
}

// 获取当前选中的视图ID
function getViewId(){
    return globalVue.currSelectViewInfo != null ? globalVue.currSelectViewInfo.viewId : null;
}

// 解析 globalVue.fieldCreateInfo 为 接口所需格式
function parseFieldCreateInfoForFieldInfo(){

    // 获得输入的内容
    let fieldCreateInfo = globalVue.fieldCreateInfo;

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