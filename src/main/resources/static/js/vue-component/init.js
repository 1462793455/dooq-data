

// 初始化数据
function init(){

    // 赋值全局实例
    globalVue = this;

    $("#app").css("opacity","1");

    // 初始化可视高度
    this.clientHeight = document.body.clientHeight;

    // 视图数据初始化
    getViews((data)=>{globalVue.viewList = data;});
    // 字段类型初始化
    getFieldTypes();
    // 颜色初始化
    getColorList();

}

// 监听页面高度变化
window.onresize = function () {
    if (globalVue) {
        globalVue.clientHeight = document.body.clientHeight
    }
};