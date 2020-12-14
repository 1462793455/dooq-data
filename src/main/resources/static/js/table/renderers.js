var DEFAULT_TEXT = "";

/**
 * 初始化所有 Renderer
 */
function initRenderer() {
    initTextRenderer();
    initTextareaRenderer();
    initNumberRenderer();
    initTimeRenderer();
    initDateRenderer();
    initDateTimeRenderer();
    initSystemOperateRenderer();
}


/**
 * 文本项
 */
function initTextRenderer() {
    VXETable.renderer.add('custom-text', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-input @input="customTextInput" placeholder="请输入" v-model="input"></el-input>',
                data: function () {
                    return {
                        input: row[column.property || null]
                    }
                },
                methods: {
                    customTextInput(value) {
                        vm.dataInput(column.property, value);
                    }
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            if (!row[column.property || null]) {
                return DEFAULT_TEXT;
            }
            return row[column.property];
        },
        // 筛选模板
        renderFilter: textRenderFilter,
    })
}

// 文本类型筛选的 Filter 目前 短文本和长文本在公用
function textRenderFilter(h, renderOpts, {column}) {
    let template = {
        template: `<div class="filter-body text-filter-body">
                                <label class="el-form-item__label">${column.title}</label>
                                <el-form style="display: flex;justify-content: space-between;" size="mini" :inline="true">
                                    <el-form-item style="width: 100%">
                                        <el-input @change="changeText" v-model="value" placeholder="模糊查询"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>`,
        data: function () {
            return {
                value :"",
            }
        },
        methods: {
            changeText(value){
                setFilterCondition(column.field,{"text":value});
            }
        }
    }
    return [h(template)];
}

// 使用该框有三个场景  添加数据、行级修改数据、单元格级修改数据

// 他们都统一操作 editData 所以可以调用同一个方法，提供 字段id 和修改的值

// 当修改单元格时：
// 1. 编辑框需要展示旧值
// 2. 修改后发送请求进行修改，并前端直接将原本单元格的值替换为新值
// 3. 当收到修改成功时则提示，当收到修改失败时提示并将单元格的值回滚。

/**
 * 文本域
 */
function initTextareaRenderer() {
    VXETable.renderer.add('custom-textarea', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-input @input="customTextareaInput" type="textarea" placeholder="请输入" v-model="textarea"></el-input>',
                data: function () {
                    return {
                        textarea: row[column.property],
                    }
                },
                methods: {
                    customTextareaInput(value) {
                        vm.dataInput(column.property, value);
                    }
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            if (!row[column.property]) {
                return DEFAULT_TEXT;
            }
            return row[column.property];
        },
        // 筛选模板
        renderFilter: textRenderFilter,
    })
}

/**
 * 系统操作列
 */
function initSystemOperateRenderer() {
    VXETable.renderer.add('system-operate', {
        // 可编辑显示模板
        renderDefault(h, renderOpts, {row, column}) {
            let template = {
                template: `<div style="display: flex;justify-content: space-between;">
                                <el-link :underline="false" @click="selectData" type="primary">查看</el-link>
                                <el-link :underline="false" @click="editData" type="primary">编辑</el-link>
                                <el-popconfirm @confirm="removeRowData" title="这是一段内容确定删除吗？">
                                  <el-link slot="reference" :underline="false" type="danger">删除</el-link>
                                </el-popconfirm>
                            </div>`,
                data: function () {
                    return {}
                },
                methods: {
                    // 编辑当前数据
                    editData() {
                        // 当前行ID
                        let rowId = row["rowId"];
                        // 打开编辑框
                        vm.checkEditMode(1, rowId);
                    },
                    // 编辑当前数据
                    selectData() {
                        // 当前行ID
                        let rowId = row["rowId"];
                        // 打开编辑框
                        vm.checkEditMode(2, rowId);
                    },
                    // 删除当前行
                    removeRowData() {
                        deleteRowData(row["rowId"], () => {
                            vm.$notify({
                                title: '删除数据成功',
                                // message: res.message,
                                type: 'success'
                            });
                        });
                    }
                }
            }
            return [h(template)];
        }
    })
}

/**
 * 数字项
 */
function initNumberRenderer() {
    VXETable.renderer.add('custom-number', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-input-number v-model="value" controls-position="right" @change="customNumberChange"></el-input-number>',
                data: function () {
                    return {
                        value: row[column.property || null],
                    }
                },
                methods: {
                    customNumberChange(currentValue) {
                        vm.dataInput(column.property, currentValue);
                    },
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            if (!row[column.property]) {
                return DEFAULT_TEXT;
            }
            let template = {
                template: `<div style="text-align: center;color: #ff3d44;font-weight: 600;">${row[column.property]}</div>`,
            }
            return [h(template)];
        },
        // 筛选模板 结束不能比开始小
        renderFilter(h, renderOpts, {column}) {

            let template = {
                template: `<div class="filter-body number-filter-body">
                                <label class="el-form-item__label">${column.title}</label>
                                <el-form style="display: flex;justify-content: space-between;" size="mini" :inline="true">
                                    <el-form-item >
                                        <el-input v-model="startNumber" @change="changeStartNumber" type="number" placeholder="开始范围"></el-input>
                                    </el-form-item>
                                    <span style="font-size: 12px;line-height: 28px;">到</span>
                                    <el-form-item>
                                        <el-input v-model="endNumber" @change="changeEntNumber" type="number" placeholder="结束范围"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>`,
                data: function () {
                    return {
                        startNumber:null,
                        endNumber:null,
                    }
                },
                methods: {
                    changeStartNumber(currentValue){
                        // 获得已经保存的旧数据 {}
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        // 设置 值
                        condition["startRange"] = currentValue;

                        setFilterCondition(column.field,condition);
                    },
                    changeEntNumber(currentValue){

                        // 获得已经保存的旧数据 {}
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        // 设置 值
                        condition["endRange"] = currentValue;

                        setFilterCondition(column.field,condition);

                    }
                }
            }
            return [h(template)];
        },
    })
}

/**
 * 将筛选条件设置到 vm.selectData.filter 中
*/
function setFilterCondition(fieldId,conditionJson){
    vm.selectData.filter[fieldId] = conditionJson;
}

/**
 * 获取 filter 中旧数据
*/
function getOldFilterValue(fieldId){
    // 当前筛选参数
    let filterMap = vm.selectData.filter;
    if(filterMap && filterMap[fieldId]){
        return filterMap[fieldId];
    }
    return null;
}

/**
 * 时间
 */
function initTimeRenderer() {
    VXETable.renderer.add('custom-time', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-time-picker v-model="value" @change="customTimeChange" placeholder="任意时间点"></el-time-picker>',
                data: function () {
                    return {
                        value: true,
                    }
                },
                methods: {
                    customTimeChange(value) {
                        vm.dataInput(column.property, value);
                    }
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            return row[column.title] || DEFAULT_TEXT;
        }
    })
}

/**
 * 日期
 */
function initDateRenderer() {
    VXETable.renderer.add('custom-date', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-date-picker value-format="timestamp" @change="customDateChange" v-model="value" type="date" placeholder="选择日期">',
                data: function () {
                    return {
                        value: row[column.property],
                    }
                },
                methods: {
                    customDateChange(value) {
                        vm.dataInput(column.property, value);
                    },
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            if (!row[column.property]) {
                return DEFAULT_TEXT;
            }
            return dateFormat(row[column.property], "Y-m-d");
        },
        // 筛选模板 结束不能比开始小
        renderFilter(h, renderOpts, {column}) {
            let template = {
                template: `<div class="filter-body number-filter-body">
                                <label class="el-form-item__label">${column.title}</label>
                                <el-form style="display: flex;justify-content: space-between;" size="mini" :inline="true">
                                    <el-form-item >
                                       <el-date-picker value-format="timestamp" @change="customStartDateChange" v-model="startTime" type="date" placeholder="开始时间">
                                    </el-form-item>
                                    <span style="font-size: 12px;line-height: 28px;">到</span>
                                    <el-form-item>
                                        <el-date-picker value-format="timestamp" @change="customEndDateChange" v-model="endTime" type="date" placeholder="结束时间">
                                    </el-form-item>
                                </el-form>
                            </div>`,
                data: function () {
                    return {
                        startTime:null,
                        endTime:null,
                    }
                },
                methods: {
                    customStartDateChange(value){
                        // 得到旧值
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        condition["startTime"] = value;

                        setFilterCondition(column.field,condition);
                    },
                    customEndDateChange(value){
                        // 得到旧值
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        condition["entTime"] = value;

                        setFilterCondition(column.field,condition);
                    }
                }
            }
            return [h(template)];
        },
    })
}

/**
 * 日期时间
 */
function initDateTimeRenderer() {
    VXETable.renderer.add('custom-date-time', {
        // 可编辑激活模板
        renderEdit(h, renderOpts, {row, column}) {
            let template = {
                template: '<el-date-picker value-format="timestamp" @change="customDateTimeChange" v-model="value" type="datetime" placeholder="选择日期时间"> </el-date-picker>',
                data: function () {
                    return {
                        value: row[column.property],
                    }
                },
                methods: {
                    customDateTimeChange(value) {
                        vm.dataInput(column.property, value);
                    },
                }
            }
            return [h(template)];
        },
        // 可编辑显示模板
        renderCell(h, renderOpts, {row, column}) {
            if (!row[column.property]) {
                return DEFAULT_TEXT;
            }
            return dateFormat(row[column.property], "Y-m-d h:i:s");
        },
        // 筛选模板 结束不能比开始小
        renderFilter(h, renderOpts, {column}) {
            let template = {
                template: `<div class="filter-body number-filter-body">
                                <label class="el-form-item__label">${column.title}</label>
                                <el-form style="display: flex;justify-content: space-between;" size="mini" :inline="true">
                                    <el-form-item >
                                       <el-date-picker value-format="timestamp" @change="customStartDateChange" v-model="startTime" type="datetime" placeholder="开始时间"> </el-date-picker>
                                    </el-form-item>
                                    <span style="font-size: 12px;line-height: 28px;">到</span>
                                    <el-form-item>
                                        <el-date-picker value-format="timestamp" @change="customEndDateChange" v-model="endTime" type="datetime" placeholder="结束时间"> </el-date-picker>
                                    </el-form-item>
                                </el-form>
                            </div>`,
                data: function () {
                    return {
                        startTime:null,
                        endTime:null,
                    }
                },
                methods: {
                    customStartDateChange(value){
                        // 得到旧值
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        condition["startTime"] = value;

                        setFilterCondition(column.field,condition);
                    },
                    customEndDateChange(value){
                        // 得到旧值
                        let condition = getOldFilterValue(column.field);
                        if(!condition){
                            condition = {};
                        }

                        condition["entTime"] = value;

                        setFilterCondition(column.field,condition);
                    }
                }
            }
            return [h(template)];
        }
    })
}