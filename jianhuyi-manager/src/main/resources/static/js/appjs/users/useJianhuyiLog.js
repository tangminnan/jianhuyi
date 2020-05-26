var prefix = "/information/useJianhuyiLog"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        userId: $('#uid').val(),
                        startTime: $('#startTime').val(),
                        endTime: $('#endTime').val()
                        // name:$('#searchName').val(),
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    [
                        /*{
                            field: 'id',
                            title: 'id',
                            align: 'center',
                            valign: 'middle',
                            rowspan: 2
                        },*/
                        {
                            field: 'userId',
                            title: '用户Id',
                            align: 'center',
                            valign: 'middle',
                            rowspan: 2
                        },

                        {
                            field: 'saveTime',
                            title: '开始时间',
                            align: 'center',
                            valign: 'middle',
                            rowspan: 2
                        }, {
                        field: 'addTime',
                        title: '上传时间',
                        align: 'center',
                        valign: 'middle',
                        rowspan: 2
                    },{
                        field: 'equipmentId',
                        title: '设备号',
                        align: 'center',
                        valign: 'middle',
                        rowspan: 2
                    },/* {
                        field: 'status',
                        title: '状态',
                        formatter:function (value) {
                            if(value == 1){
                                return "阅读"
                            }else if(value == 2){
                                return "非阅读"
                            }else{
                                return ""
                            }

                        }
                    },*/
                        {
                            title: '总评价',
                            align: 'center',
                            valign: 'middle',
                            rowspan: 2,
                            cellStyle: function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter: function (value, row) {
                                if ((row.readDuration <= 20 && row.outdoorsDuration >= 2 && row.readDistance >= 33) &&
                                    ((row.readLight >= 300 && row.lookPhoneDuration <= 10 && row.lookTvComputerDuration <= 20 && row.sitTilt <= 5) || (row.readLight >= 300 && row.lookPhoneDuration <= 10 && row.lookTvComputerDuration <= 20) || (row.readLight >= 300 && row.lookPhoneDuration <= 10 && row.sitTilt <= 5) || (row.lookTvComputerDuration <= 20 && row.lookPhoneDuration <= 10 && row.sitTilt <= 5))
                                    && (row.useJianhuyiDuration >= 10 || row.useJianhuyiDuration <= 8 && row.useJianhuyiDuration < 10)) {
                                    return "优"
                                } else if (
                                    ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40)) && ((row.outdoorsDuration >= 2 || (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2))) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33))
                                    //d优良差
                                    && (
                                        (
                                            ((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300) || ((row.readLight >= 200) && (row.readLight < 250)))
                                            && ((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20))
                                            && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40))
                                            && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10))
                                        ) || (
                                            ((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300))
                                            && ((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20) || (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40))
                                            && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40))
                                            && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10))
                                        ) || (
                                            ((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300))
                                            && ((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20)
                                                && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) || (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60))
                                                && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10))
                                            )
                                        ) || (
                                            ((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300))
                                            && ((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20)
                                                && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40))
                                                && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10) || (row.sitTilt > 10 && row.sitTilt <= 15))
                                            )
                                        )
                                    ) && (row.useJianhuyiDuration >= 10 || (row.useJianhuyiDuration >= 8 && row.useJianhuyiDuration < 10))
                                ) {
                                    return "良"
                                } else if (
                                    (
                                        ((row.readDuration <= 20) || ((row.readDuration > 20 && row.readDuration <= 40)) || ((row.readDuration > 40 && row.readDuration <= 90))) && ((row.outdoorsDuration >= 2) || ((row.outdoorsDuration >= 1 && row.outdoorsDuration < 2))) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33))
                                        ||
                                        ((row.readDuration <= 20) || ((row.readDuration > 20 && row.readDuration <= 40))) && ((row.outdoorsDuration >= 2) || ((row.outdoorsDuration >= 1 && row.outdoorsDuration < 2)) || (row.outdoorsDuration >= 0.5 && row.outdoorsDuration < 1)) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33))
                                        ||
                                        ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40)) || ((row.outdoorsDuration >= 2) || ((row.outdoorsDuration >= 1 && row.outdoorsDuration < 2))) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33) || (row.readDistance > 20 && row.readDistance <= 30))
                                    ) && (
                                        (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300) || (row.readLight >= 200 && row.readLight < 250)) && ((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20)) && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10)))
                                        ||
                                        (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300)) && (((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20)) || (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40)) && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10)))
                                        ||
                                        (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300)) && (((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20))) && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) || (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10)))
                                        ||
                                        (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300)) && (((row.lookPhoneDuration <= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20))) && ((row.lookTvComputerDuration <= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10)) || (row.sitTilt > 10 && row.sitTilt <= 15))
                                    )
                                    && (
                                        ((row.useJianhuyiDuration >= 10) || (row.useJianhuyiDuration >= 8 && row.useJianhuyiDuration < 10) || (row.useJianhuyiDuration >= 5 < 8))
                                    )
                                ) {
                                    return "不太好"
                                } else if (
                                    (
                                        //前三项最多2项差
                                        (
                                            ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40) || (row.readDuration > 40 && row.readDuration <= 90)) && ((row.outdoorsDuration >= 2) || (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2) || (row.outdoorsDuration >= 0.5 && row.outdoorsDuration < 1)) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33))
                                        )
                                        ||
                                        (
                                            ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40)) && ((row.outdoorsDuration >= 2) || (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2) || (row.outdoorsDuration >= 0.5 && row.outdoorsDuration < 1)) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33) || (row.readDistance > 20 && row.readDistance <= 30))
                                        )
                                        ||
                                        (
                                            ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40) || (row.readDuration > 40 && a <= 90)) && ((row.outdoorsDuration >= 2) || (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2)) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33) || (row.readDistance > 20 && row.readDistance <= 30))
                                        )
                                    ) &&
                                    (
                                        (
                                            (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300) || (row.readLight >= 200 && row.readLight < 250)) && ((row.lookPhoneDuration >= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20) || (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40)) && ((row.lookTvComputerDuration >= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) || (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10)))//123
                                            &&
                                            (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300) || (row.readLight >= 200 && row.readLight < 250)) && ((row.lookPhoneDuration >= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20)) && ((row.lookTvComputerDuration >= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) || (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10) || (row.sitTilt > 10 && row.sitTilt <= 15)))//134
                                            &&
                                            (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300)) && ((row.lookPhoneDuration >= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20) || (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40)) && ((row.lookTvComputerDuration >= 20) || (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) || (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10) || (row.sitTilt > 10 && row.sitTilt <= 15)))//234
                                            &&
                                            (((row.readLight >= 300) || (row.readLight >= 250 && row.readLight < 300) || (row.readLight >= 200 && row.readLight < 250)) && ((row.lookPhoneDuration >= 10) || (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20) || (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40)) && ((row.lookTvComputerDuration >= 20) || (row.lookTvComputerDuration > 20 && f <= 40)) && ((row.sitTilt <= 5) || (row.sitTilt > 5 && row.sitTilt <= 10) || (row.sitTilt > 10 && row.sitTilt <= 15)))//234
                                        )
                                    ) &&
                                    (
                                        (row.useJianhuyiDuration >= 10) || (row.useJianhuyiDuration >= 8 && row.useJianhuyiDuration < 10) || (row.useJianhuyiDuration >= 5 && row.useJianhuyiDuration < 8) || (row.useJianhuyiDuration < 5)
                                    )
                                ) {
                                    return "差"
                                } else {
                                    return "极差"
                                }


                                /*else if(){

                                } else if (row.readDuration > 20 && row.readDuration <= 40) {
                                    return "良"
                                } else if (row.readDuration == 20) {
                                    return "标准"
                                } else if (row.readDuration > 40 && row.readDuration <= 90) {
                                    return "差"
                                } else if (row.readDuration > 90) {
                                    return "极差"
                                }*/
                            }
                        },
                        {
                            /*field : 'readDuration', */
                            title: '阅读时长(分钟)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'outdoorsDuration',
                            title:
                                '户外时长(小时)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'readDistance',
                            title:
                                '阅读距离(厘米)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'readLight',
                            title:
                                '阅读光照(lux)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'lookPhoneDuration',
                            title:
                                '看手机时长<br>(分钟)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                3
                        }
                        ,
                        {
                            field: 'lookTvComputerDuration',
                            title:
                                '看电脑电视时长<br>(分钟)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                3
                        }
                        ,
                        {
                            field: 'sitTilt',
                            title:
                                '坐姿倾斜度',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'useJianhuyiDuration',
                            title:
                                '使用监护仪时长<br>(小时)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        {
                            field: 'sportDuration',
                            title:
                                '运动时长<br>(小时)',
                            align:
                                'center',
                            valign:
                                'middle',
                            colspan:
                                2
                        }
                        ,
                        /*								{
                            field : 'delFlag',
                            title : '删除标志(1:删除 0：未删除)'
                        },
                             */
                        {
                            title: '操作',
                            field:
                                'id',
                            align:
                                'center',
                            formatter:

                                function (value, row, index) {
                                    var e = '<a class="btn btn-success btn-sm" href="#" mce_href="#" title="当日统计数据" onclick="detaildata(\''+row.userId+'\',\''+row.saveTime+'\')"><i class="fa fa-bar-chart-o"></i></a> ';
                                    var d = '<a class="btn btn-warning btn-sm" href="#" title="当日原始数据"  mce_href="#" onclick="historyData(\''+ row.userId +'\',\''+row.saveTime+'\')"><i class="fa fa-navicon"></i></a> ';
                                    return e + d;
                                }
                        }
                    ],
                    [{
                        field: 'readDuration',
                        title: '阅读',
                        sortable: true,
                        valign: 'middle',
                        align: 'center',
                        formatter:function (value) {
                            return  value.toFixed(2);
                        }
                    },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',

                            formatter: function (value, row) {
                                if (row.readDuration < 20) {
                                    return "优"
                                } else if (row.readDuration > 20 && row.readDuration <= 40) {
                                    return "良"
                                } else if (row.readDuration == 20) {
                                    return "标准"
                                } else if (row.readDuration > 40 && row.readDuration <= 90) {
                                    return "差"
                                } else if (row.readDuration > 90) {
                                    return "极差"
                                }
                            }
                        },
                        {
                            field: 'outdoorsDuration',
                            title: '户外',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter: function (value, row) {
                                if (row.outdoorsDuration > 2) {
                                    return "优"
                                } else if (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2) {
                                    return "良"
                                } else if (row.outdoorsDuration == 2) {
                                    return "标准"
                                } else if (row.outdoorsDuration >= 0.5 && row.outdoorsDuration < 1) {
                                    return "差"
                                } else if (row.outdoorsDuration < 0.5) {
                                    return "极差"
                                }
                            }
                        },
                        {
                            field: 'readDistance',
                            title: '距离',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row) {
                                if (row.readDistance > 33) {
                                    return "优"
                                } else if (row.readDistance >= 30 && row.readDistance <= 33) {
                                    return "良"
                                } else if (row.readDistance == 33) {
                                    return "标准"
                                } else if (row.readDistance > 20 && row.readDistance <= 30) {
                                    return "差"
                                } else if (row.readDistance < 20) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'readLight',
                            title: '光照',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter: function (value, row) {
                                if (row.readLight > 300) {
                                    return "优"
                                } else if (row.readLight >= 250 && row.readLight < 300) {
                                    return "良"
                                } else if (row.readLight == 300) {
                                    return "标准"
                                } else if (row.readLight >= 200 && row.readLight < 250) {
                                    return "差"
                                } else if (row.readLight < 200) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'lookPhoneDuration',
                            title: '看手机',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },{
                        field: 'lookPhoneCount',
                        title: '次数',
                        valign: 'middle',
                        sortable: true,
                        align: 'center'
                    },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row) {
                                if (row.lookPhoneDuration < 10) {
                                    return "优"
                                } else if (row.lookPhoneDuration > 10 && row.lookPhoneDuration <= 20) {
                                    return "良"
                                } else if (row.lookPhoneDuration == 10) {
                                    return "标准"
                                } else if (row.lookPhoneDuration > 20 && row.lookPhoneDuration <= 40) {
                                    return "差"
                                } else if (row.lookPhoneDuration > 40) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'lookTvComputerDuration',
                            title: '看电子屏',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },{
                        field: 'lookTvComputerCount',
                        title: '次数',
                        valign: 'middle',
                        sortable: true,
                        align: 'center',
                        cellStyle:function (value, row, index, field) {
                            return {
                                classes: 'text - nowrap another - class',
                                css: {'background-color': '#5792C6', 'font - size': '50px'}
                            }
                        }
                    },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter: function (value, row) {
                                if (row.lookTvComputerDuration < 20) {
                                    return "优"
                                } else if (row.lookTvComputerDuration > 20 && row.lookTvComputerDuration <= 40) {
                                    return "良"
                                } else if (row.lookTvComputerDuration == 20) {
                                    return "标准"
                                } else if (row.lookTvComputerDuration > 40 && row.lookTvComputerDuration <= 60) {
                                    return "差"
                                } else if (row.lookTvComputerDuration > 60) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'sitTilt',
                            title: '坐姿',
                            valign: 'middle',
                            sortable: true,
                            align: 'center',
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row) {
                                if (row.sitTilt < 5) {
                                    return "优"
                                } else if (row.sitTilt > 5 && row.sitTilt <= 10) {
                                    return "良"
                                } else if (row.sitTilt == 5) {
                                    return "标准"
                                } else if (row.sitTilt > 10 && row.sitTilt <= 15) {
                                    return "差"
                                } else if (row.sitTilt > 15) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'useJianhuyiDuration',
                            title: '使用时长',
                            sortable: true,
                            valign: 'middle',
                            align: 'center',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            cellStyle:function (value, row, index, field) {
                                return {
                                    classes: 'text - nowrap another - class',
                                    css: {'background-color': '#5792C6', 'font - size': '50px'}
                                }
                            },
                            formatter: function (value, row) {
                                if (row.useJianhuyiDuration > 10) {
                                    return "优"
                                } else if (row.useJianhuyiDuration >= 8 && row.useJianhuyiDuration < 10) {
                                    return "良"
                                } else if (row.useJianhuyiDuration == 10) {
                                    return "标准"
                                } else if (row.useJianhuyiDuration >= 5 && row.useJianhuyiDuration < 8) {
                                    return "差"
                                } else if (row.useJianhuyiDuration < 5) {
                                    return "极差"
                                }
                            }
                        },

                        {
                            field: 'sportDuration',
                            title: '运动时长',
                            sortable: true,
                            valign: 'middle',
                            align: 'center',
                            formatter:function (value) {
                                return  value.toFixed(2);
                            }
                        },
                        {
                            title: '评价',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row) {
                                if (row.sportDuration > 2) {
                                    return "优"
                                } else if (row.sportDuration >= 1 && row.sportDuration < 2) {
                                    return "良"
                                } else if (row.sportDuration == 2) {
                                    return "标准"
                                } else if (row.sportDuration >= 0.5 && row.sportDuration < 1) {
                                    return "差"
                                } else if (row.sportDuration < 0.5) {
                                    return "极差"
                                }
                            }
                        }


                    ]
                ]
            })
    ;
}

function detaildata(userId,saveTime){
    var page = layer.open({
        type: 2,
        title: '当日数据详情',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/useJianhuyiLogDetail?userId='+userId+'&saveTime='+saveTime // iframe的url
    });
    layer.full(page)
}

function historyData(userId,saveTime){
    var page = layer.open({
        type: 2,
        title: '当日数据详情',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: '/information/data/listdata?userId='+userId+'&startTime='+saveTime // iframe的url
    });
    layer.full(page)
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}



function reLoadWeek() {
    weekEchart();
    $('#exampleTable').bootstrapTable('refresh', {query: {week: 'week'}});
}

function reLoadMonth() {
    monthEchart();
    $('#exampleTable').bootstrapTable('refresh', {query: {month: 'month'}});
}

function reLoadQuarter() {
    quarterEchart();
    $('#exampleTable').bootstrapTable('refresh', {query: {quarter: 'quarter'}});
}

function reLoadYear() {
    yearEchart();
    $('#exampleTable').bootstrapTable('refresh', {query: {year: 'year'}});
}


function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

//周折线统计图
function weekEchart() {
    $('#weekEchart').show()
    //获取本周一
    const startDate = moment().day("Monday").format('YYYY-MM-DD');
    const endDate = moment().day(7).format('YYYY-MM-DD');

    //初始化ECHART
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    var myChart4 = echarts.init(document.getElementById('main4'));
    var myChart5 = echarts.init(document.getElementById('main5'));
    var myChart6 = echarts.init(document.getElementById('main6'));
    var myChart7 = echarts.init(document.getElementById('main7'));
    var myChart8 = echarts.init(document.getElementById('main8'));

    $.ajax({
        type: 'get',
        dataType: "json",
        async: true,
        cache: false,
        url: '/information/useJianhuyiLog/listData',
        data: {
            userId: $('#uid').val(),
            start: startDate,
            end:endDate
        },
        success: function (result) {
            console.log(result)
                /*$('#div1')[0].innerText = '总平均' + readDurations + '分钟';
                if (readDurations >= 20) {
                    $('#span1')[0].innerText = '优';
                } else if (readDurations > 20 && readDurations <= 40) {
                    $('#span1')[0].innerText = '良';
                } else if (readDurations > 40 && readDurations <= 90) {
                    $('#span1')[0].innerText = '差';
                } else if (readDurations > 90) {
                    $('#span1')[0].innerText = '极差';
                }
                $('#div2')[0].innerText = '总平均' + outdoorsDurations + '小时';
                if (outdoorsDurations >= 2) {
                    $('#span2')[0].innerText = '优';
                } else if (outdoorsDurations >= 1 && outdoorsDurations < 2) {
                    $('#span2')[0].innerText = '良';
                } else if (outdoorsDurations >= 0.5 && outdoorsDurations < 1) {
                    $('#span2')[0].innerText = '差';
                } else if (outdoorsDurations < 0.5) {
                    $('#span2')[0].innerText = '极差';
                }
                $('#div3')[0].innerText = '总平均' + readDistances + '厘米';
                if (readDistances >= 33) {
                    $('#span3')[0].innerText = '优';
                } else if (readDistances >= 30 && readDistances < 33) {
                    $('#span3')[0].innerText = '良';
                } else if (readDistances > 20 && readDistances <= 30) {
                    $('#span3')[0].innerText = '差';
                } else if (readDistances < 20) {
                    $('#span3')[0].innerText = '极差';
                }
                $('#div4')[0].innerText = '总平均' + readLights + 'lux';
                if (readLights >= 300) {
                    $('#span4')[0].innerText = '优';
                } else if (readLights >= 250 && readLights < 300) {
                    $('#span4')[0].innerText = '良';
                } else if (readLights >= 200 && readLights < 250) {
                    $('#span4')[0].innerText = '差';
                } else if (readLights < 200) {
                    $('#span4')[0].innerText = '极差';
                }
                $('#div5')[0].innerText = '总平均' + lookPhoneDurations + '分钟';
                if (lookPhoneDurations >= 10) {
                    $('#span5')[0].innerText = '优';
                } else if (lookPhoneDurations > 10 && lookPhoneDurations <= 20) {
                    $('#span5')[0].innerText = '良';
                } else if (lookPhoneDurations > 20 && lookPhoneDurations <= 40) {
                    $('#span5')[0].innerText = '差';
                } else if (lookPhoneDurations > 40) {
                    $('#span5')[0].innerText = '极差';
                }
                $('#div6')[0].innerText = '总平均' + lookTvComputerDurations + '分钟';
                if (lookTvComputerDurations >= 20) {
                    $('#span6')[0].innerText = '优';
                } else if (lookTvComputerDurations > 20 && lookTvComputerDurations <= 40) {
                    $('#span6')[0].innerText = '良';
                } else if (lookTvComputerDurations > 40 && lookTvComputerDurations <= 60) {
                    $('#span6')[0].innerText = '差';
                } else if (lookTvComputerDurations > 60) {
                    $('#span6')[0].innerText = '极差';
                }
                $('#div7')[0].innerText = '总平均' + sitTilts + '°';
                if (sitTilts >= 5) {
                    $('#span7')[0].innerText = '优';
                } else if (sitTilts > 5 && sitTilts <= 10) {
                    $('#span7')[0].innerText = '良';
                } else if (sitTilts > 10 && sitTilts <= 15) {
                    $('#span7')[0].innerText = '差';
                } else if (sitTilts > 15) {
                    $('#span7')[0].innerText = '极差';
                }
                $('#div8')[0].innerText = '总平均' + useJianhuyiDurations + '小时';
                if (useJianhuyiDurations >= 10) {
                    $('#span8')[0].innerText = '优';
                } else if (useJianhuyiDurations >= 8 && useJianhuyiDurations < 10) {
                    $('#span8')[0].innerText = '良';
                } else if (useJianhuyiDurations >= 5 && useJianhuyiDurations < 8) {
                    $('#span8')[0].innerText = '差';
                } else if (useJianhuyiDurations < 5) {
                    $('#span8')[0].innerText = '极差';
                }
                $('#div9')[0].innerText = '总平均' + sportDurations + '小时';
                if (sportDurations >= 2) {
                    $('#span9')[0].innerText = '优';
                } else if (sportDurations >= 1 && sportDurations < 2) {
                    $('#span9')[0].innerText = '良';
                } else if (sportDurations >= 0.5 && sportDurations < 1) {
                    $('#span9')[0].innerText = '差';
                } else if (sportDurations < 0.5) {
                    $('#span9')[0].innerText = '极差';
                }*/


                myChart.setOption({
                    title: {
                        text: '日平均单次阅读时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        max: 120,
                        splitNumber: 4
                    },
                    series: [{
                        data: result.avgReadDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 20}
                            ]
                        },
                        type: 'line'
                    }]
                })


                myChart1.hideLoading();
                myChart1.setOption({
                    title: {
                        text: '户外总时间',
                        left: 'center'

                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 3
                    },
                    series: [{
                        data: result.outdoorsDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 2}
                            ]
                        },
                        type: 'line'
                    }]
                });

                myChart2.hideLoading();
                myChart2.setOption({
                    title: {
                        text: '平均阅读距离',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '厘米',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgReadDistance,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 33}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart3.hideLoading();
                myChart3.setOption({
                    title: {
                        text: '平均阅读光照',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: 'lux',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgReadLight,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 300}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart4.hideLoading();
                myChart4.setOption({
                    title: {
                        text: '平均单次看手机时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgLookPhoneDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 10}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart5.hideLoading();
                myChart5.setOption({
                    title: {
                        text: '平均单次看电脑电视时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        splitNumber: 8
                    },
                    series: [{
                        data: result.avgLookTvComputerDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 20}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart6.hideLoading();
                myChart6.setOption({
                    title: {
                        text: '平均坐姿倾斜角度',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '°',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgSitTilt,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 5}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart7.hideLoading();
                myChart7.setOption({
                    title: {
                        text: '平均有效使用监护仪时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgUseJianhuyiDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 10}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart8.hideLoading();
                myChart8.setOption({
                    title: {
                        text: '运动时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['周一','周二','周三','周四','周五','周六','周日']
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.sportDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 2}
                            ]
                        },
                        type: 'line'
                    }]
                })
            }

    })
}


//月折线统计图
function monthEchart() {
    $('#weekEchart').show()
    //获取本周一
    const startDate = moment().startOf('month').format('YYYY-MM-DD');
    const endDate = moment().endOf('month').format('YYYY-MM-DD');

    //初始化ECHART
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    var myChart4 = echarts.init(document.getElementById('main4'));
    var myChart5 = echarts.init(document.getElementById('main5'));
    var myChart6 = echarts.init(document.getElementById('main6'));
    var myChart7 = echarts.init(document.getElementById('main7'));
    var myChart8 = echarts.init(document.getElementById('main8'));

    $.ajax({
        type: 'get',
        dataType: "json",
        async: true,
        cache: false,
        url: '/information/useJianhuyiLog/listData',
        data: {
            userId: $('#uid').val(),
            start: startDate,
            end:endDate
        },
        success: function (result) {
            console.log(result)

                /*$('#div1')[0].innerText = '总平均' + readDurations + '分钟';
                if (readDurations >= 20) {
                    $('#span1')[0].innerText = '优';
                } else if (readDurations > 20 && readDurations <= 40) {
                    $('#span1')[0].innerText = '良';
                } else if (readDurations > 40 && readDurations <= 90) {
                    $('#span1')[0].innerText = '差';
                } else if (readDurations > 90) {
                    $('#span1')[0].innerText = '极差';
                }
                $('#div2')[0].innerText = '总平均' + outdoorsDurations + '小时';
                if (outdoorsDurations >= 2) {
                    $('#span2')[0].innerText = '优';
                } else if (outdoorsDurations >= 1 && outdoorsDurations < 2) {
                    $('#span2')[0].innerText = '良';
                } else if (outdoorsDurations >= 0.5 && outdoorsDurations < 1) {
                    $('#span2')[0].innerText = '差';
                } else if (outdoorsDurations < 0.5) {
                    $('#span2')[0].innerText = '极差';
                }
                $('#div3')[0].innerText = '总平均' + readDistances + '厘米';
                if (readDistances >= 33) {
                    $('#span3')[0].innerText = '优';
                } else if (readDistances >= 30 && readDistances < 33) {
                    $('#span3')[0].innerText = '良';
                } else if (readDistances > 20 && readDistances <= 30) {
                    $('#span3')[0].innerText = '差';
                } else if (readDistances < 20) {
                    $('#span3')[0].innerText = '极差';
                }
                $('#div4')[0].innerText = '总平均' + readLights + 'lux';
                if (readLights >= 300) {
                    $('#span4')[0].innerText = '优';
                } else if (readLights >= 250 && readLights < 300) {
                    $('#span4')[0].innerText = '良';
                } else if (readLights >= 200 && readLights < 250) {
                    $('#span4')[0].innerText = '差';
                } else if (readLights < 200) {
                    $('#span4')[0].innerText = '极差';
                }
                $('#div5')[0].innerText = '总平均' + lookPhoneDurations + '分钟';
                if (lookPhoneDurations >= 10) {
                    $('#span5')[0].innerText = '优';
                } else if (lookPhoneDurations > 10 && lookPhoneDurations <= 20) {
                    $('#span5')[0].innerText = '良';
                } else if (lookPhoneDurations > 20 && lookPhoneDurations <= 40) {
                    $('#span5')[0].innerText = '差';
                } else if (lookPhoneDurations > 40) {
                    $('#span5')[0].innerText = '极差';
                }
                $('#div6')[0].innerText = '总平均' + lookTvComputerDurations + '分钟';
                if (lookTvComputerDurations >= 20) {
                    $('#span6')[0].innerText = '优';
                } else if (lookTvComputerDurations > 20 && lookTvComputerDurations <= 40) {
                    $('#span6')[0].innerText = '良';
                } else if (lookTvComputerDurations > 40 && lookTvComputerDurations <= 60) {
                    $('#span6')[0].innerText = '差';
                } else if (lookTvComputerDurations > 60) {
                    $('#span6')[0].innerText = '极差';
                }
                $('#div7')[0].innerText = '总平均' + sitTilts + '°';
                if (sitTilts >= 5) {
                    $('#span7')[0].innerText = '优';
                } else if (sitTilts > 5 && sitTilts <= 10) {
                    $('#span7')[0].innerText = '良';
                } else if (sitTilts > 10 && sitTilts <= 15) {
                    $('#span7')[0].innerText = '差';
                } else if (sitTilts > 15) {
                    $('#span7')[0].innerText = '极差';
                }
                $('#div8')[0].innerText = '总平均' + useJianhuyiDurations + '小时';
                if (useJianhuyiDurations >= 10) {
                    $('#span8')[0].innerText = '优';
                } else if (useJianhuyiDurations >= 8 && useJianhuyiDurations < 10) {
                    $('#span8')[0].innerText = '良';
                } else if (useJianhuyiDurations >= 5 && useJianhuyiDurations < 8) {
                    $('#span8')[0].innerText = '差';
                } else if (useJianhuyiDurations < 5) {
                    $('#span8')[0].innerText = '极差';
                }
                $('#div9')[0].innerText = '总平均' + sportDurations + '小时';
                if (sportDurations >= 2) {
                    $('#span9')[0].innerText = '优';
                } else if (sportDurations >= 1 && sportDurations < 2) {
                    $('#span9')[0].innerText = '良';
                } else if (sportDurations >= 0.5 && sportDurations < 1) {
                    $('#span9')[0].innerText = '差';
                } else if (sportDurations < 0.5) {
                    $('#span9')[0].innerText = '极差';
                }
*/

                myChart.setOption({
                    title: {
                        text: '日平均单次阅读时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        max: 120,
                        splitNumber: 4
                    },
                    series: [{
                        data: result.avgReadDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 20}
                            ]
                        },
                        type: 'line'
                    }]
                })


                myChart1.hideLoading();
                myChart1.setOption({
                    title: {
                        text: '户外总时间',
                        left: 'center'

                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 3
                    },
                    series: [{
                        data: result.outdoorsDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 2}
                            ]
                        },
                        type: 'line'
                    }]
                });

                myChart2.hideLoading();
                myChart2.setOption({
                    title: {
                        text: '平均阅读距离',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '厘米',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgReadDistance,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 33}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart3.hideLoading();
                myChart3.setOption({
                    title: {
                        text: '平均阅读光照',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: 'lux',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgReadLight,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 300}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart4.hideLoading();
                myChart4.setOption({
                    title: {
                        text: '平均单次看手机时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgLookPhoneDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 10}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart5.hideLoading();
                myChart5.setOption({
                    title: {
                        text: '平均单次看电脑电视时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '分钟',
                        splitNumber: 8
                    },
                    series: [{
                        data: result.avgLookTvComputerDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 20}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart6.hideLoading();
                myChart6.setOption({
                    title: {
                        text: '平均坐姿倾斜角度',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '°',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgSitTilt,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 5}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart7.hideLoading();
                myChart7.setOption({
                    title: {
                        text: '平均有效使用监护仪时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.avgUseJianhuyiDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 10}
                            ]
                        },
                        type: 'line'
                    }]
                })

                myChart8.hideLoading();
                myChart8.setOption({
                    title: {
                        text: '运动时长',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20',
                            '21','22','23','24','25','26','27','28','29','30','31'],
                        name:'日'
                    },
                    yAxis: {
                        type: 'value',
                        name: '小时',
                        splitNumber: 5
                    },
                    series: [{
                        data: result.sportDuration,
                        markLine: {
                            label: '标准线',
                            data: [
                                {xAxis: 0, yAxis: 2}
                            ]
                        },
                        type: 'line'
                    }]
                })
            }


    })
}


//季折线统计图
function quarterEchart() {
    $('#weekEchart').show()
    //获取本周一
    const startDate = moment().startOf('quarter').format('YYYY-MM-DD');
    const endDate = moment().endOf('quarter').format('YYYY-MM-DD');

    //初始化ECHART
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    var myChart4 = echarts.init(document.getElementById('main4'));
    var myChart5 = echarts.init(document.getElementById('main5'));
    var myChart6 = echarts.init(document.getElementById('main6'));
    var myChart7 = echarts.init(document.getElementById('main7'));
    var myChart8 = echarts.init(document.getElementById('main8'));

    $.ajax({
        type: 'get',
        dataType: "json",
        async: true,
        cache: false,
        url: '/information/useJianhuyiLog/seasonData',
        data: {
            userId: $('#uid').val(),
            start:startDate,
            end:endDate
        },
        success: function (result) {
            console.log(result)

               /* $('#div1')[0].innerText = '总平均' + readDurations + '分钟';
                if (readDurations >= 20) {
                    $('#span1')[0].innerText = '优';
                } else if (readDurations > 20 && readDurations <= 40) {
                    $('#span1')[0].innerText = '良';
                } else if (readDurations > 40 && readDurations <= 90) {
                    $('#span1')[0].innerText = '差';
                } else if (readDurations > 90) {
                    $('#span1')[0].innerText = '极差';
                }
                $('#div2')[0].innerText = '总平均' + outdoorsDurations + '小时';
                if (outdoorsDurations >= 2) {
                    $('#span2')[0].innerText = '优';
                } else if (outdoorsDurations >= 1 && outdoorsDurations < 2) {
                    $('#span2')[0].innerText = '良';
                } else if (outdoorsDurations >= 0.5 && outdoorsDurations < 1) {
                    $('#span2')[0].innerText = '差';
                } else if (outdoorsDurations < 0.5) {
                    $('#span2')[0].innerText = '极差';
                }
                $('#div3')[0].innerText = '总平均' + readDistances + '厘米';
                if (readDistances >= 33) {
                    $('#span3')[0].innerText = '优';
                } else if (readDistances >= 30 && readDistances < 33) {
                    $('#span3')[0].innerText = '良';
                } else if (readDistances > 20 && readDistances <= 30) {
                    $('#span3')[0].innerText = '差';
                } else if (readDistances < 20) {
                    $('#span3')[0].innerText = '极差';
                }
                $('#div4')[0].innerText = '总平均' + readLights + 'lux';
                if (readLights >= 300) {
                    $('#span4')[0].innerText = '优';
                } else if (readLights >= 250 && readLights < 300) {
                    $('#span4')[0].innerText = '良';
                } else if (readLights >= 200 && readLights < 250) {
                    $('#span4')[0].innerText = '差';
                } else if (readLights < 200) {
                    $('#span4')[0].innerText = '极差';
                }
                $('#div5')[0].innerText = '总平均' + lookPhoneDurations + '分钟';
                if (lookPhoneDurations >= 10) {
                    $('#span5')[0].innerText = '优';
                } else if (lookPhoneDurations > 10 && lookPhoneDurations <= 20) {
                    $('#span5')[0].innerText = '良';
                } else if (lookPhoneDurations > 20 && lookPhoneDurations <= 40) {
                    $('#span5')[0].innerText = '差';
                } else if (lookPhoneDurations > 40) {
                    $('#span5')[0].innerText = '极差';
                }
                $('#div6')[0].innerText = '总平均' + lookTvComputerDurations + '分钟';
                if (lookTvComputerDurations >= 20) {
                    $('#span6')[0].innerText = '优';
                } else if (lookTvComputerDurations > 20 && lookTvComputerDurations <= 40) {
                    $('#span6')[0].innerText = '良';
                } else if (lookTvComputerDurations > 40 && lookTvComputerDurations <= 60) {
                    $('#span6')[0].innerText = '差';
                } else if (lookTvComputerDurations > 60) {
                    $('#span6')[0].innerText = '极差';
                }
                $('#div7')[0].innerText = '总平均' + sitTilts + '°';
                if (sitTilts >= 5) {
                    $('#span7')[0].innerText = '优';
                } else if (sitTilts > 5 && sitTilts <= 10) {
                    $('#span7')[0].innerText = '良';
                } else if (sitTilts > 10 && sitTilts <= 15) {
                    $('#span7')[0].innerText = '差';
                } else if (sitTilts > 15) {
                    $('#span7')[0].innerText = '极差';
                }
                $('#div8')[0].innerText = '总平均' + useJianhuyiDurations + '小时';
                if (useJianhuyiDurations >= 10) {
                    $('#span8')[0].innerText = '优';
                } else if (useJianhuyiDurations >= 8 && useJianhuyiDurations < 10) {
                    $('#span8')[0].innerText = '良';
                } else if (useJianhuyiDurations >= 5 && useJianhuyiDurations < 8) {
                    $('#span8')[0].innerText = '差';
                } else if (useJianhuyiDurations < 5) {
                    $('#span8')[0].innerText = '极差';
                }
                $('#div9')[0].innerText = '总平均' + sportDurations + '小时';
                if (sportDurations >= 2) {
                    $('#span9')[0].innerText = '优';
                } else if (sportDurations >= 1 && sportDurations < 2) {
                    $('#span9')[0].innerText = '良';
                } else if (sportDurations >= 0.5 && sportDurations < 1) {
                    $('#span9')[0].innerText = '差';
                } else if (sportDurations < 0.5) {
                    $('#span9')[0].innerText = '极差';
                }*/


            myChart.setOption({
                title: {
                    text: '日平均单次阅读时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    max: 120,
                    splitNumber: 4
                },
                series: [{
                    data: result.avgReadDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 20}
                        ]
                    },
                    type: 'line'
                }]
            })


            myChart1.hideLoading();
            myChart1.setOption({
                title: {
                    text: '户外总时间',
                    left: 'center'

                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 3
                },
                series: [{
                    data: result.outdoorsDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 2}
                        ]
                    },
                    type: 'line'
                }]
            });

            myChart2.hideLoading();
            myChart2.setOption({
                title: {
                    text: '平均阅读距离',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '厘米',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgReadDistance,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 33}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart3.hideLoading();
            myChart3.setOption({
                title: {
                    text: '平均阅读光照',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: 'lux',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgReadLight,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 300}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart4.hideLoading();
            myChart4.setOption({
                title: {
                    text: '平均单次看手机时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgLookPhoneDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 10}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart5.hideLoading();
            myChart5.setOption({
                title: {
                    text: '平均单次看电脑电视时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    splitNumber: 8
                },
                series: [{
                    data: result.avgLookTvComputerDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 20}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart6.hideLoading();
            myChart6.setOption({
                title: {
                    text: '平均坐姿倾斜角度',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '°',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgSitTilt,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 5}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart7.hideLoading();
            myChart7.setOption({
                title: {
                    text: '平均有效使用监护仪时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgUseJianhuyiDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 10}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart8.hideLoading();
            myChart8.setOption({
                title: {
                    text: '运动时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'周'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 5
                },
                series: [{
                    data: result.sportDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 2}
                        ]
                    },
                    type: 'line'
                }]
            })
            }


    })
}


//年折线统计图
function yearEchart() {
    $('#weekEchart').show()


    const startDate = moment().startOf('year').format('YYYY-MM-DD');
    const endDate = moment().endOf('year').format('YYYY-MM-DD');

    //初始化ECHART
    var myChart = echarts.init(document.getElementById('main'));
    var myChart1 = echarts.init(document.getElementById('main1'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    var myChart4 = echarts.init(document.getElementById('main4'));
    var myChart5 = echarts.init(document.getElementById('main5'));
    var myChart6 = echarts.init(document.getElementById('main6'));
    var myChart7 = echarts.init(document.getElementById('main7'));
    var myChart8 = echarts.init(document.getElementById('main8'));


    console.log(myChart)
    $.ajax({
        type: 'get',
        dataType: "json",
        async: true,
        cache: false,
        url: '/information/useJianhuyiLog/yearData',
        data: {
            userId: $('#uid').val(),
            start: startDate,
            end: endDate
        },
        success: function (result) {
            console.log(result)
            myChart.setOption({
                title: {
                    text: '日平均单次阅读时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    max: 120,
                    splitNumber: 4
                },
                series: [{
                    data: result.avgReadDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 20}
                        ]
                    },
                    type: 'line'
                }]
            })


            myChart1.hideLoading();
            myChart1.setOption({
                title: {
                    text: '户外总时间',
                    left: 'center'

                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 3
                },
                series: [{
                    data: result.outdoorsDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 2}
                        ]
                    },
                    type: 'line'
                }]
            });

            myChart2.hideLoading();
            myChart2.setOption({
                title: {
                    text: '平均阅读距离',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '厘米',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgReadDistance,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 33}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart3.hideLoading();
            myChart3.setOption({
                title: {
                    text: '平均阅读光照',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: 'lux',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgReadLight,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 300}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart4.hideLoading();
            myChart4.setOption({
                title: {
                    text: '平均单次看手机时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgLookPhoneDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 10}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart5.hideLoading();
            myChart5.setOption({
                title: {
                    text: '平均单次看电脑电视时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '分钟',
                    splitNumber: 8
                },
                series: [{
                    data: result.avgLookTvComputerDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 20}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart6.hideLoading();
            myChart6.setOption({
                title: {
                    text: '平均坐姿倾斜角度',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '°',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgSitTilt,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 5}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart7.hideLoading();
            myChart7.setOption({
                title: {
                    text: '平均有效使用监护仪时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 5
                },
                series: [{
                    data: result.avgUseJianhuyiDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 10}
                        ]
                    },
                    type: 'line'
                }]
            })

            myChart8.hideLoading();
            myChart8.setOption({
                title: {
                    text: '运动时长',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                    name:'月'
                },
                yAxis: {
                    type: 'value',
                    name: '小时',
                    splitNumber: 5
                },
                series: [{
                    data: result.sportDuration,
                    markLine: {
                        label: '标准线',
                        data: [
                            {xAxis: 0, yAxis: 2}
                        ]
                    },
                    type: 'line'
                }]
            })

        }

    })
}


function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}