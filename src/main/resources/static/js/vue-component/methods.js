function methods() {
    return {
        // 创建视图
        createView(){
            // 获得当前视图名称输入框内容
            let viewName = globalVue.viewNameStorage;
            // 校验是否合法
            if(verifyShortText(viewName)){
                // 创建
                createViewInfo(viewName,(viewId)=>{

                    // 清除 input 框
                    globalVue.viewNameStorage = "";

                    // 刷新视图列表
                    getViews((data)=>{
                        globalVue.viewList = data;
                        if(data != null){
                            data.forEach((item)=>{
                                if(item.viewId == viewId){
                                    //切换到新视图
                                    globalVue.checkView(item);
                                }
                            })
                        }
                    });

                    // 提示
                    globalVue.$notify({
                        title: '创建成功',
                        message: '恭喜你于清，视图创建成功了!',
                        type: 'success'
                    });

                    // 关闭创建框
                    globalVue.$refs.createViewPopoverItem.doClose();

                })
            // 否则，输入错误，input 获得焦点
            } else {
                globalVue.$refs.viewNameInputRef.focus()
            }
        },
        // 删除视图
        removeView(viewId){
            removeViewByViewId(viewId,()=>{

                // 刷新列表
                getViews((data)=>{
                    globalVue.viewList = data;
                });

                // 提示信息
                globalVue.$notify({
                    title: '删除成功',
                    message: '恭喜你于清，视图删除成功了!',
                    type: 'success'
                });

            });
        },
        // 修改视图
        updateView(viewId,index){
            // 获得当前视图名称输入框内容
            let viewName = globalVue.viewNameStorage;
            // 校验是否合法
            if(verifyShortText(viewName)){
                // 创建
                updateViewName(viewId,viewName,()=>{

                    // 清除 input 框
                    globalVue.viewNameStorage = "";

                    // 刷新视图列表
                    getViews((data)=>{
                        globalVue.viewList = data;
                    });

                    // 提示
                    globalVue.$notify({
                        title: '创建成功',
                        message: '恭喜你于清，视图修改成功了!',
                        type: 'success'
                    });

                    // 关闭模态框
                    globalVue.$refs[`viewUpdatePopoverItem${index}`][0].doClose();
                })
                // 否则，输入错误，input 获得焦点
            } else {
                // 关闭创建框
                globalVue.$refs[`viewNameUpdateInputRef${index}`][0].focus()
            }
        },
        // 选择切换视图
        selectCurrViewItem(item,index){
            // 切换
            this.checkView(item);
            // 关闭模态框
            globalVue.$refs.viewWarpItem.doClose();
        },
        // 切换视图
        checkView(item){
            globalVue.currSelectViewInfo = item;
        },
        // 创建新字段
        createViewFieldInfo(){
            // 创建字段
            createFieldInfo(parseFieldCreateInfoForFieldInfo(),()=>{
                // 提示，并关闭模态框，初始化 fieldCreateInfo
                globalVue.$notify({
                    title: '创建成功',
                    message: '恭喜你于清，视图修改成功了!',
                    type: 'success'
                });
                // 关闭模态框
                globalVue.createFieldDialogVisible = false;
                // 重置 fieldCreateInfo
                resetFieldCreateInfo();
            });
        },








        // 表头 右键触发事件
        tableHeaderContextMenu(column, event) {

            // 传递
            createTableHeaderMenu();

        },


        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        },


        resetDateFilter() {
            this.$refs.filterTable.clearFilter('date');
        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        formatter(row, column) {
            return row.address;
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        }
    }
}