var prefix = "/information/dataEveryday"
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
                  userId: $('#userId').val(),
                  equipmentId: $('#equipmentId').val(),
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
                  {
                     field: 'userId',
                     title: '用户Id',
                     align: 'center',
                     valign: 'middle',
                     rowspan: 2
                  },

                  {
                     field: 'useTime',
                     title: '开始时间',
                     align: 'center',
                     valign: 'middle',
                     rowspan: 2
                  },
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
                           && (row.effectiveTime >= 10 || row.effectiveTime <= 8 && row.effectiveTime < 10)) {
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
                           ) && (row.effectiveTime >= 10 || (row.effectiveTime >= 8 && row.effectiveTime < 10))
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
                              ((row.effectiveTime >= 10) || (row.effectiveTime >= 8 && row.effectiveTime < 10) || (row.effectiveTime >= 5 < 8))
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
                                 ((row.readDuration <= 20) || (row.readDuration > 20 && row.readDuration <= 40) || (row.readDuration > 40 && row.readDuration <= 90)) && ((row.outdoorsDuration >= 2) || (row.outdoorsDuration >= 1 && row.outdoorsDuration < 2)) && ((row.readDistance >= 33) || (row.readDistance >= 30 && row.readDistance < 33) || (row.readDistance > 20 && row.readDistance <= 30))
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
                              (row.effectiveTime >= 10) || (row.effectiveTime >= 8 && row.effectiveTime < 10) || (row.effectiveTime >= 5 && row.effectiveTime < 8) || (row.effectiveTime < 5)
                           )
                        ) {
                           return "差"
                        } else {
                           return "极差"
                        }

                     }
                  },
                  {
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
                        '户外时长(分钟)',
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
                        2
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
                        2
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
                     field: 'effectiveTime',
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
                     title: '操作',
                     field:
                        'id',
                     align:
                        'center',
                     formatter:

                        function (value, row, index) {
                           var e = '<a class="btn btn-success btn-sm" href="#" mce_href="#" title="当日统计数据" onclick="detaildata(\'' + row.userId + '\',\'' + row.useTime + '\')"><i class="fa fa-bar-chart-o"></i></a> ';
                           var d = '<a class="btn btn-warning btn-sm" href="#" title="当日原始数据"  mce_href="#" onclick="historyData(\'' + row.userId + '\',\'' + row.useTime + '\')"><i class="fa fa-navicon"></i></a> ';
                           var f = '<a class="btn btn-primary btn-sm" href="#" title="监护仪报告"  mce_href="#" onclick="jianhuyiReport(\'' + row.userId + '\',\'' + row.useTime + '\')"><i class="fa fa-navicon"></i></a> ';

                            return e + d+f;
                        }
                  }
               ],
               [{
                  field: 'readDuration',
                  title: '阅读',
                  sortable: true,
                  valign: 'middle',
                  align: 'center',
                  formatter: function (value) {
                     if (value != null) {
                        return value.toFixed(2);
                     } else {
                        return ""
                     }

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
                     cellStyle: function (value, row, index, field) {
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
                     cellStyle: function (value, row, index, field) {
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
                        } else {
                           return "暂无"
                        }
                     }
                  },
                  {
                     field: 'readDistance',
                     title: '距离',
                     valign: 'middle',
                     sortable: true,
                     align: 'center',
                     formatter: function (value) {
                        if (value != null) {
                           return value.toFixed(2);
                        } else {
                           return ""
                        }

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
                     cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     formatter: function (value) {
                        if (value != null) {
                           return value.toFixed(2);
                        } else {
                           return ""
                        }

                     }
                  },
                  {
                     title: '评价',
                     align: 'center',
                     valign: 'middle',
                     cellStyle: function (value, row, index, field) {
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
                     formatter: function (value) {
                        if (value != null) {
                           return value.toFixed(2);
                        } else {
                           return ""
                        }

                     }
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
                     cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     formatter: function (value) {
                        if (value != null) {
                           return value.toFixed(2);
                        } else {
                           return ""
                        }

                     }
                  },
                  {
                     title: '评价',
                     align: 'center',
                     valign: 'middle',
                     cellStyle: function (value, row, index, field) {
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
                     formatter: function (value) {
                        if (value != null) {
                           return value.toFixed(2);
                        } else {
                           return ""
                        }

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
                     field: 'effectiveTime',
                     title: '使用时长',
                     sortable: true,
                     valign: 'middle',
                     align: 'center',
                     cellStyle: function (value, row, index, field) {
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
                     cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     formatter: function (value, row) {
                        if (row.effectiveTime > 10) {
                           return "优"
                        } else if (row.effectiveTime >= 8 && row.effectiveTime < 10) {
                           return "良"
                        } else if (row.effectiveTime == 10) {
                           return "标准"
                        } else if (row.effectiveTime >= 5 && row.effectiveTime < 8) {
                           return "差"
                        } else if (row.effectiveTime < 5) {
                           return "极差"
                        } else {
                           return "暂无"
                        }
                     }
                  }
               ]
            ]
         });
}

function reLoad() {
   $('#exampleTable').bootstrapTable('refresh');
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

function detaildata(userId, useTime) {
   var page = layer.open({
      type: 2,
      title: '当日数据详情',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/useJianhuyiLog/useJianhuyiLogDetail?userId=' + userId + '&saveTime=' + useTime // iframe的url
   });
   layer.full(page)
}

/**
 * 监护仪报告
 */




//求data中value的总和
function arrCount(arr) {
    let count = 0
    arr.forEach(item => {
        count = count + item.value
    })
    return count
}











function jianhuyiReport(userId,saveTime){

    $.ajax({
        url:"/information/dataEveryday/getEchartsData",
        method:"GET",
        data:{userId,saveTime},

        success:res=>{console.info(res);

            let loghtF=res.loghtF;
            let distanceF=res.distanceF;
            let sitF=res.sitF;
            let lightChart = echarts.init(document.getElementById("light"));
            let data1=[];
            for(let da of res.lights){
                data1.push({name:da.name,value:da.num});
            }

            var option1 = {

                tooltip: {trigger: 'item'},
                legend: {
                    itemGap: 16,
                    icon: 'rect',
                    left: 'left',  //图例距离左的距离
                    top: 'center',
                    itemWidth: 150,
                    itemHeight: 80,
                    padding: [0,300,0,0],
                    orient : 'vertical',
                    textStyle: {
                        color: '##FFC8B4',
                        fontSize: 30
                    },
                    //格式化图例文本
                    formatter(name) {
                        //找到data中name和文本name值相同的对象
                        let val = res.lights.filter(item => {
                            return item.name === name
                        })
            //            return name + '  ' + ((val[0].value / count).toFixed(4)) * 100 + '%'
                        return name+'  '+val[0].value;
                    }
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '220px',
                        center: ['60%', '55%'],
                        data: data1,
                        label : {
                            normal : {
                                textStyle : {
                                    fontWeight : 'normal',
                                    fontSize : 30
                                }
                            }
                        },
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            lightChart.setOption(option1);
            let distanceChart = echarts.init(document.getElementById("distance"));
            let data2=[];
            for(let da of res.distances){
               data2.push({name:da.name,value:da.num});
            }

            var option2 = {

                tooltip: {trigger: 'item'},
                legend: {
                    itemGap: 16,
                    icon: 'rect',
                    left: 'left',  //图例距离左的距离
                    top: 'center',
                    itemWidth: 150,
                    itemHeight: 80,
                    padding: [0,300,0,0],
                    orient : 'vertical',
                    textStyle: {
                        color: '##FFC8B4',
                        fontSize: 30
                    },
                    //格式化图例文本
                    formatter(name) {
                        //找到data中name和文本name值相同的对象
                        let val = res.distances.filter(item => {
                            return item.name === name
                        })
            //            return name + '  ' + ((val[0].value / count).toFixed(4)) * 100 + '%'
                        return name+'  '+val[0].value;
                    }
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '220px',
                        center: ['60%', '55%'],
                        data: data2,
                        label : {
                            normal : {
                                textStyle : {
                                    fontWeight : 'normal',
                                    fontSize : 30
                                }
                            }
                        },
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            distanceChart.setOption(option2);
            var sitChart = echarts.init(document.getElementById("sit"));
            let data3=[];
            for(let da of res.sits){
                data3.push({name:da.name,value:da.num});
            }

            console.info("==========data3===============");
            console.info(data3);
            console.info("==========data3===============");
            var option3 = {

                tooltip: {trigger: 'item'},
                legend: {
                    itemGap: 16,
                    icon: 'rect',
                    left: 'left',  //图例距离左的距离
                    top: 'center',
                    itemWidth: 150,
                    itemHeight: 80,
                    padding: [0,300,0,0],
                    orient : 'vertical',
                    textStyle: {
                        color: '##FFC8B4',
                        fontSize: 30
                    },
                    //格式化图例文本
                    formatter(name) {
                        //找到data中name和文本name值相同的对象
                        let val = res.sits.filter(item => {
                            return item.name === name
                        })
                        return name + '  ' + val[0].value;
                    }
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '220px',
                        center: ['60%', '55%'],
                        data: data3,
                        label : {
                            normal : {
                                textStyle : {
                                    fontWeight : 'normal',
                                    fontSize : 30
                                }
                            }
                        },
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            sitChart.setOption(option3);
            setTimeout(()=>{
                let sn = sitChart.getDataURL().split("base64,")[1];
                let ln = lightChart.getDataURL().split("base64,")[1];
                let dn = distanceChart.getDataURL().split("base64,")[1];
                let form = document.createElement("form");
                form.style.display = 'none';
                form.action = prefix + "/jianhuyiReport"
                form.method = "post";
                document.body.appendChild(form);
                let params={userId:userId,useTime:saveTime,ln:ln,dn:dn,sn:sn,loghtF,distanceF,sitF};
                for(var key in params){
                    var input = document.createElement("input");
                    input.type = "hidden";
                    input.name = key;
                    input.value = params[key];
                    form.appendChild(input);
                }

                form.submit();
                form.remove();


            },1000)

        }
    })


}

function historyData(userId, saveTime) {
   var page = layer.open({
      type: 2,
      title: '当日数据详情',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/data/listdata?userId=' + userId + '&startTime=' + saveTime // iframe的url
   });
   layer.full(page)
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

function daochu() {
   var userId = $('#userId').val()
   var equipmentId = $('#equipmentId').val()
   var startTime = $('#startTime').val()
   var endTime = $('#endTime').val()

   layer.msg("正在导出请耐心等待...")
   window.location.href = prefix + '/daochu?userId=' + userId + '&equipmentId=' + equipmentId + '&startTime=' + startTime + '&endTime=' + endTime
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