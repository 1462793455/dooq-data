
// 获取所有字段类型
function getFieldTypes(success){
    // 已经获取过了
    if(vm.fieldTypes != null){
        return success && success(vm.fieldTypes);
    }
    $.ajax({
        url: SERVER_HOST + "/v1/api/expand/field_list",
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                vm.fieldTypes = res.data;
                success && success(res.data);
            }
        }
    })
}

// 获取所有支持的颜色
function getColorList(success){
    // 已经获取过了
    if(vm.fieldTypes != null){
        return success && success(vm.colorList);
    }
    $.ajax({
        url: SERVER_HOST + "/v1/api/expand/color_list",
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                vm.colorList = res.data;
                success && success(res.data);
            }
        }
    })
}
