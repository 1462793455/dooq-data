

// 表头菜单 修改模式
var tableHeaderMenusUpdate = [
    {"icon":"el-icon-circle-plus-outline","name":"添加新字段","fun":""},
    {"icon":"el-icon-finished","name":"修改该字段","fun":""},
    {"icon":"el-icon-circle-close","name":"删除该字段","fun":""}
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
    // {"icon":"","name":"","fun":""},
];

// 表头菜单，创建模式
var tableHeaderMenusCreate = [
    {"icon":"el-icon-circle-plus-outline","name":"添加新字段","fun":""}
]

/**
 * 创建表头菜单
 * @param isUpdate 是否为修改模式
*/
function createTableHeaderMenu(isUpdate){
    if(isUpdate){
        showTableMenu(tableHeaderMenusUpdate);
    } else {
        showTableMenu(tableHeaderMenusCreate);
    }
}





///////////////////// 内部方法 ///////////////////////////

// 展示表格右键菜单，传入 menus 选项
function showTableMenu(menus){

    // 将传入的 menus 设置到变量中
    vm.menus = menus;

    // 先隐藏菜单
    vm.menuContextVisible = false;
    // 再展示
    vm.menuContextVisible = true;
    //关闭浏览器右键默认事件
    event.preventDefault();
    // 找到菜单实例
    let menuInstance = document.querySelector('#tableContextMenu');
    // 菜单偏移处理
    styleMenu(menuInstance);

}

// 菜单偏移处理
function styleMenu(menuInstance) {
    if (event.clientX > 1800) {
        menuInstance.style.left = event.clientX - 100 + 'px'
    } else {
        menuInstance.style.left = event.clientX + 1 + 'px'
    }
    // 给整个document新增监听鼠标事件
    document.addEventListener('click', foo);
    if (event.clientY > 700) {
        menuInstance.style.top = event.clientY - 30 + 'px'
    } else {
        menuInstance.style.top = event.clientY - 10 + 'px'
    }
}

// 处理
function foo(){
    // 取消鼠标监听事件 菜单栏
    vm.menuContextVisible = false;
    // 关掉监听
    document.removeEventListener('click', foo);
}