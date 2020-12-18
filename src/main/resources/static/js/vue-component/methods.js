function methods() {
    return {
        // 创建视图
        createView() {
            // 获得当前视图名称输入框内容
            let viewName = vm.viewNameStorage;
            // 校验是否合法
            if (verifyShortText(viewName)) {
                // 创建
                createViewInfo(viewName, (viewId) => {

                    // 清除 input 框
                    vm.viewNameStorage = "";

                    // 刷新视图列表
                    getViews((data) => {
                        vm.viewList = data;
                        if (data != null) {
                            data.forEach((item) => {
                                if (item.viewId == viewId) {
                                    //切换到新视图
                                    $checkView(item);
                                }
                            })
                        }
                    });

                    // 提示
                    vm.$notify({
                        title: '创建成功',
                        message: '恭喜你于清，视图创建成功了!',
                        type: 'success'
                    });

                    // 关闭创建框
                    vm.$refs.createViewPopoverItem.doClose();

                })
                // 否则，输入错误，input 获得焦点
            } else {
                vm.$refs.viewNameInputRef.focus()
            }
        },
        // 删除视图
        removeView(viewId) {
            removeViewByViewId(viewId, () => {

                // 刷新列表
                getViews((data) => {
                    vm.viewList = data;
                });

                // 提示信息
                vm.$notify({
                    title: '删除成功',
                    message: '恭喜你于清，视图删除成功了!',
                    type: 'success'
                });

            });
        },
        // 修改视图
        updateView(viewId, index) {
            // 获得当前视图名称输入框内容
            let viewName = vm.viewNameStorage;
            // 校验是否合法
            if (verifyShortText(viewName)) {
                // 创建
                updateViewName(viewId, viewName, () => {

                    // 清除 input 框
                    vm.viewNameStorage = "";

                    // 刷新视图列表
                    getViews((data) => {
                        vm.viewList = data;
                    });

                    // 提示
                    vm.$notify({
                        title: '创建成功',
                        message: '恭喜你于清，视图修改成功了!',
                        type: 'success'
                    });

                    // 关闭模态框
                    vm.$refs[`viewUpdatePopoverItem${index}`][0].doClose();
                })
                // 否则，输入错误，input 获得焦点
            } else {
                // 关闭创建框
                vm.$refs[`viewNameUpdateInputRef${index}`][0].focus()
            }
        },
        // 选择切换视图
        selectCurrViewItem(item, index) {
            // 切换
            $checkView(item);
        },
        // 批量删除数据
        batchRemoveRowData(){

            // 得到当前批量选中的数据
            let selectedRowDataArrays = vm.selectedRowDataArrays;
            // 循环组装数据ID
            let idArrays = [];
            selectedRowDataArrays.forEach(item => {
                idArrays.push(item.rowId);
            });

            if(idArrays.length <= 0){
                vm.$message({
                    message: '没有选中任何数据，无法批量删除',
                    type: 'error'
                });
                return;
            }

            // 删除
            deleteRowData(idArrays, () => {

                vm.$notify({
                    title: '删除数据成功',
                    // message: res.message,
                    type: 'success'
                });

                // 刷新数据
                getColumnDataFunction();

            });
        },
        // 创建新字段
        createViewFieldInfo() {
            // 创建字段
            createFieldInfo(parseFieldCreateInfoForFieldInfo(), () => {
                // 提示，并关闭模态框，初始化 fieldCreateInfo
                vm.$notify({
                    title: '创建成功',
                    message: '恭喜你于清，视图修改成功了!',
                    type: 'success'
                });
                // // 关闭模态框
                // vm.createFieldDialogVisible = false;
                // 重置 fieldCreateInfo
                resetFieldCreateInfo();
                // 刷新表格
                refreshFieldInfo();
            });
        },
        // 添加数据 弹出框 内容 生成 方法
        // 如果是添加模式则直接干，如果是修改模式则需要获取 rowId 对应的数据展示进去
        renderChild(h, fieldInfo) {

            // 初始化
            let row = {};
            let column = {property: fieldInfo.field};

            // 修改 || 查看模式时
            if (vm.editMode == 1 || vm.editMode == 2) {
                // 当前编辑的列ID
                let rowId = vm.editRowId;
                // 所有列单元格数据
                let columnData = vm.dataResult.resultList;
                // 循环找到对应的数据
                columnData.forEach(item => {
                    let irowId = item.rowId;
                    if(rowId == irowId){

                        // 数据转换 fieldId -> 信息
                        let fieldInfo = item.fieldInfo;
                        if(fieldInfo){
                            fieldInfo.forEach(item => {
                                row[item.fieldId] = item.value;
                            })
                        }
                    }
                });
            }

            try{
                // 处理器
                let renderer = VXETable.renderer.get(fieldInfo.editRender.name);
                // 查看模式时
                if(vm.editMode == 2){
                    this.$slots[fieldInfo.field] = renderer.renderCell(h, null, {row, column})[0];
                // 添加或修改时
                } else {
                    this.$slots[fieldInfo.field] = renderer.renderEdit(h, null, {row, column})[0];
                }
            }catch (e) {}
        },
        // 筛选页面生成元素方法
        renderFilter(h, fieldInfo){
            try{
                // 处理器
                let renderer = VXETable.renderer.get(fieldInfo.editRender.name);
                // 获取筛选框 element
                this.$slots[fieldInfo.field] = renderer.renderFilter(h, null, {column:fieldInfo})[0];
            }catch (e) {
                // console.info(e);
            }
        },
        // 单元格 edit 输入时触发
        dataInput(fieldId, value) {

            // 得到当前修改的视图ID，如果视图ID为空代表再次之前还没有修改过，如果不为空并且视图ID相同就不操作，如果不相同则清空数据
            let viewId = vm.editData.viewId;

            // 所有字段信息存储数组
            let fields = vm.editData.fields;

            // 如果是别的视图修改的则清空
            if (viewId != null && vm.currSelectViewInfo.viewId != viewId) {
                fields = [];
            }

            // 视图ID
            vm.editData.viewId = vm.currSelectViewInfo.viewId;

            // 循环找到是否有相同的，如果有则覆盖，没有则 push
            if (fields == null) {
                fields = [];
            }

            // 是否覆盖了
            let isCover = false;

            // 循环
            fields.forEach(item => {
                // 当前值所属的字段id
                let itemFieldId = item.fieldId;
                if (itemFieldId == fieldId) {
                    isCover = true;
                    item.value = value;
                }
            });

            // 没有覆盖则 push
            if (!isCover) {
                fields.push({fieldId, value});
            }

            vm.editData.fields = fields;
        },

        // 数据编辑框点击按钮(添加\修改\查看时)
        editColumnData(){

            // 添加框
            if(vm.editMode == 0){
                this.createColumnData();
                // 修改框
            } else if(vm.editMode == 1) {
                this.editColumnDataFun();
            }

        },

        // 创建数据
        createColumnData() {
            // 创建数据请求
            createData(vm.editData.viewId || vm.currSelectViewInfo.viewId, vm.editData.fields, () => {
                vm.$notify({
                    title: '创建数据成功',
                    // message: res.message,
                    type: 'success'
                });

                // 刷新数据
                getColumnDataFunction();

                // 清除输入缓存
                vm.editData.fields = [];

                // 关闭输入框
                vm.editDataDialogClose();

            });

        },
        // 编辑数据
        editColumnDataFun(){
            // 发起请求
            updateData(vm.editData.viewId || vm.currSelectViewInfo.viewId,vm.editRowId, vm.editData.fields, () => {
                vm.$notify({
                    title: '修改数据成功',
                    // message: res.message,
                    type: 'success'
                });

                // 刷新数据
                getColumnDataFunction();

                // 清除输入缓存
                vm.editData.fields = [];

                // 关闭输入框
                vm.editDataDialogClose();

            });
        },

        // 切换编辑模式 mode = true 代表 添加数据
        //                     false 代表修改数据 不同点在于，修改数据 rowId 必填
        // 并打开数据编辑框
        checkEditMode(mode, rowId) {
            // 设置编辑模式
            vm.editMode = mode;
            // 设置当前编辑的行ID
            vm.editRowId = rowId;

            // 打开编辑框
            vm.editDataDialog = true;
        },
        // 关闭数据编辑框
        editDataDialogClose() {

            // 设置编辑模式
            vm.editMode = 0;
            // 设置当前编辑的行ID
            vm.editRowId = null;

            // 打开编辑框
            vm.editDataDialog = false;

        },
        // 返回编辑模式名称
        getEditModeStr(){
            return vm.editMode == 0 ? '添加' : vm.editMode == 1 ? '修改' : '查看';
        },

        // 筛选按钮点击触发
        filterColumnDate(){
            filterColumnDateFunction();
        },
        // 重置筛选数据
        resetFilterColumnData(){

            // 1. 重置查询数据
            resetSelectData();

            // 2. 重新获取数据
            getColumnDataFunction();

        },
        // 切换数据页
        changeCruuPage(currPageNumber){

            // 1. 设置查询页
            vm.selectData.pageNumber = currPageNumber;
            // 查询
            getColumnDataFunction();
        },

    }
}