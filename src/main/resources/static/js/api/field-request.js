
// 添加新字段
function createFieldInfo(fileInfo,success){

    $.ajax({
        url: SERVER_HOST + "/v1/api/field/create_view_field",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(fileInfo),
        success:function(res){
            if(SUCCESS_CODE == res.errCode && res.data != null){
                success && success(res.data);
            } else {
                globalVue.$notify({
                    title: '添加字段失败',
                    message: res.message,
                    type: 'error'
                });
            }
        }
    })

}