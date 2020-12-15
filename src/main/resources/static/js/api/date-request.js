
// 创建数据
function createData(viewId,fields,success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/data/create_data",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(
            {
                viewId:viewId, dataInfoList:fields
        }),
        success:function(res){
            if(SUCCESS_CODE == res.errCode){
                success && success(res.data);
            } else {
                vm.$notify({
                    title: '创建数据失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}

// 删除 行数据
function deleteRowData(rowId,success){
    $.ajax({
        url: SERVER_HOST + "/v1/api/data/remove_row_data",
        type: "POST",
        data:{"rowId":rowId},
        success:function(res){
            if(SUCCESS_CODE == res.errCode){
                success && success(res.data);
            } else {
                vm.$notify({
                    title: '删除数据失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}

// 修改数据
function updateData(viewId,rowId,fields,success){
    $.ajax({
        url: SERVER_HOST + "/v1/api/data/update_data",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(
            {
                viewId:viewId,rowId:rowId, dataInfoList:fields
            }),
        success:function(res){
            if(SUCCESS_CODE == res.errCode){
                success && success(res.data);
            } else {
                vm.$notify({
                    title: '修改数据失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}

// 获取数据
function getColumnData(options,success){
    $.ajax({
        url: SERVER_HOST + "/v1/api/data/get_data_list",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            viewId:options["viewId"],
            pageSize:options["pageSize"],
            pageNumber:options["pageNumber"],
            filterCondition:options["filterCondition"],
        }),
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                success && success(res.data);
            } else {
                vm.$notify({
                    title: '获取视图失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}