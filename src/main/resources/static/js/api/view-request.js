
// 获取所有视图列表
function getViews(success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/view/view_list",
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                success && success(res.data);
            } else {
                globalVue.$notify({
                    title: '获取视图失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}

// 新增视图
function createViewInfo(viewName,success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/view/create_view",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({viewName:viewName}),
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                success && success(res.data);
            } else {
                globalVue.$notify({
                    title: '新增视图失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })
}

// 修改视图名称
function updateViewName(viewId,viewName,success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/view/update_view",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({"viewName":viewName,"viewId":viewId}),
        success:function(res){
            if(SUCCESS_CODE == res.errCode){
                success && success();
            } else {
                globalVue.$notify({
                    title: '修改视图失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })
}

// 删除视图
function removeViewByViewId(viewId,success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/view/remove_view",
        type: "POST",
        data: {"viewId":viewId},
        success:function(res){
            if(SUCCESS_CODE == res.errCode){
                success && success();
            } else {
                globalVue.$notify({
                    title: '删除视图失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })
}
