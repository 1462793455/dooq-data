

// 初始化数据
function init(){

    // 赋值全局实例
    vm = this;

    $("#app").css("opacity","1");

    // 初始化可视高度
    this.clientHeight = document.body.clientHeight;
    vm.gridOptions.maxHeight = this.clientHeight - 105;

    // 视图数据初始化
    getViews((data)=>{vm.viewList = data;});
    // 字段类型初始化
    getFieldTypes();
    // 颜色初始化
    getColorList();

    // 初始化所有 Renderer
    initRenderer();

}

// 监听页面高度变化
window.onresize = function () {
    if (vm) {
        vm.clientHeight = document.body.clientHeight;

    }
};