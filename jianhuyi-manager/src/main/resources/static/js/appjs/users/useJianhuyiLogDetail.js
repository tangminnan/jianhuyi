var prefix = "/information/useJianhuyiLog"
$(function () {
   load();
});

function load() {
   $('#exampleTable')
      .bootstrapTable(
         {
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/listDetail", // 服务器数据的加载地址
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
                  saveTime: $('#saveTime').val()

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
                  }, {
                  field: 'saveTime',
                  title: '开始时间',
                  align: 'center',
                  valign: 'middle',
                  rowspan: 2
               },
                  {
                     field: 'addTime',
                     title: '上传时间',
                     align: 'center',
                     valign: 'middle',
                     rowspan: 2
                  },
                  {
                     field: 'equipmentId',
                     title: '设备号',
                     align: 'center',
                     valign: 'middle',
                     rowspan: 2
                  },

                  {
                     field: 'status',
                     title: '状态',
                     align: 'center',
                     formatter: function (value) {
                        if (value == 1) {
                           return "阅读"
                        } else if (value == 2) {
                           return "非阅读"
                        } else {
                           return ""
                        }
                     },
                     valign: 'middle',
                     rowspan: 2,


                  },
                  {
                     /*field : 'readDuration', */
                     title: '阅读时长(分钟)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },
                  {
                     field: 'outdoorsDuration',
                     title: '户外时长(小时)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },
                  {
                     field: 'readDistance',
                     title: '阅读距离(厘米)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },
                  {
                     field: 'readLight',
                     title: '阅读光照(lux)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },
                  {
                     field: 'lookPhoneDuration',
                     title: '看手机时长<br>(分钟)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 2
                  },
                  {
                     field: 'lookTvComputerDuration',
                     title: '看电脑电视时长<br>(分钟)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 2
                  },
                  {
                     field: 'sitTilt',
                     title: '坐姿倾斜度',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },
                  {
                     field: 'useJianhuyiDuration',
                     title: '使用监护仪时长<br>(小时)',
                     align: 'center',
                     valign: 'middle',
                     colspan: 1
                  },

                  /*								{
                      field : 'delFlag',
                      title : '删除标志(1:删除 0：未删除)'
                  },
                       */                        /*   {
                            title : '操作',
                            field : 'id',
                            align : 'center',
                            formatter : function(value, row, index) {
                                var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="当日数据详情" onclick="detaildata(\''
                                        + row.id
                                        + '\')"><i class="fa fa-edit"></i></a> ';
                                var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="当日原始数据"  mce_href="#" onclick="yuanshiData(\''
                                        + row.id
                                        + '\')"><i class="fa fa-remove"></i></a> ';
                                return e ;
                            }
                        }*/],
               [{
                  field: 'readDuration',
                  title: '阅读',
                  sortable: true,
                  valign: 'middle',
                  align: 'center'
               },

                  {
                     field: 'outdoorsDuration',
                     title: '户外',
                     valign: 'middle',
                     sortable: true, cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     align: 'center'
                  },

                  {
                     field: 'readDistance',
                     title: '距离',
                     valign: 'middle',
                     sortable: true,
                     align: 'center'
                  },


                  {
                     field: 'readLight',
                     title: '光照',
                     valign: 'middle',
                     sortable: true, cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     align: 'center'
                  },


                  {
                     field: 'lookPhoneDuration',
                     title: '看手机',
                     valign: 'middle',
                     sortable: true,
                     align: 'center'
                  }, {
                  field: 'lookPhoneCount',
                  title: '次数',
                  valign: 'middle',
                  sortable: true,
                  align: 'center'
               },


                  {
                     field: 'lookTvComputerDuration',
                     title: '看电子屏',
                     valign: 'middle',
                     sortable: true, cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     align: 'center'
                  },
                  {
                     field: 'lookTvComputerCount',
                     title: '次数',
                     valign: 'middle',
                     sortable: true, cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     align: 'center'
                  },

                  {
                     field: 'sitTilt',
                     title: '坐姿',
                     valign: 'middle',
                     sortable: true,
                     align: 'center'
                  },


                  {
                     field: 'useJianhuyiDuration',
                     title: '使用时长',
                     sortable: true,
                     valign: 'middle', cellStyle: function (value, row, index, field) {
                        return {
                           classes: 'text - nowrap another - class',
                           css: {'background-color': '#5792C6', 'font - size': '50px'}
                        }
                     },
                     align: 'center'
                  },


               ]
            ]
         });
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
   const startDate = moment().week(moment().week()).startOf('week').format('YYYY-MM-DD');
   const endDate = moment().week(moment().week()).endOf('week').format('YYYY-MM-DD');

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
         week: 'week'
      },
      success: function (result) {
         console.log(result)
         if (result.length > 0) {
            var readDuration = [];
            var outdoorsDuration = [];
            var readDistance = [];
            var readLight = [];
            var lookPhoneDuration = [];
            var lookTvComputerDuration = [];
            var sitTilt = [];
            var useJianhuyiDuration = [];
            var sportDuration = [];

            var readDurations = 0;
            var outdoorsDurations = 0;
            var readDistances = 0;
            var readLights = 0;
            var lookPhoneDurations = 0;
            var lookTvComputerDurations = 0;
            var sitTilts = 0;
            var useJianhuyiDurations = 0;
            var sportDurations = 0;

            var week = [];
            for (const resultElement of result) {
               readDuration.push(resultElement.readDuration)
               outdoorsDuration.push(resultElement.outdoorsDuration)
               readDistance.push(resultElement.readDistance)
               readLight.push(resultElement.readLight)
               lookPhoneDuration.push(resultElement.lookPhoneDuration)
               lookTvComputerDuration.push(resultElement.lookTvComputerDuration)
               sitTilt.push(resultElement.sitTilt)
               useJianhuyiDuration.push(resultElement.useJianhuyiDuration)
               sportDuration.push(resultElement.sportDuration)

               week.push(resultElement.week)

               readDurations += resultElement.readDuration;
               outdoorsDurations += resultElement.outdoorsDuration;
               readDistances += resultElement.readDistance;
               readLights += resultElement.readLights;
               lookPhoneDurations += resultElement.lookPhoneDurations;
               lookTvComputerDurations += resultElement.lookTvComputerDurations;
               sitTilts += resultElement.sitTilt;
               useJianhuyiDurations += resultElement.useJianhuyiDurations;
               sportDurations += resultElement.sportDuration;
            }
            readDurations = readDurations / result.length;
            outdoorsDurations = outdoorsDurations / result.length;
            readDistances = outdoorsDurations / result.length;
            readLights = outdoorsDurations / result.length;
            lookPhoneDurations = outdoorsDurations / result.length;
            lookTvComputerDurations = outdoorsDurations / result.length;
            sitTilts = outdoorsDurations / result.length;
            useJianhuyiDurations = outdoorsDurations / result.length;
            sportDurations = outdoorsDurations / result.length;


            $('#div1')[0].innerText = '总平均' + readDurations + '分钟';
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '分钟',
                  max: 120,
                  splitNumber: 4
               },
               series: [{
                  data: readDuration,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '小时',
                  splitNumber: 3
               },
               series: [{
                  data: outdoorsDuration,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '厘米',
                  splitNumber: 5
               },
               series: [{
                  data: readDistance,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: 'lux',
                  splitNumber: 5
               },
               series: [{
                  data: readLight,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '分钟',
                  splitNumber: 5
               },
               series: [{
                  data: lookPhoneDuration,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '分钟',
                  splitNumber: 8
               },
               series: [{
                  data: lookTvComputerDuration,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '°',
                  splitNumber: 5
               },
               series: [{
                  data: sitTilt,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: useJianhuyiDuration,
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
                  data: week
               },
               yAxis: {
                  type: 'value',
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: sportDuration,
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


      }

   })
}


//月折线统计图
function monthEchart() {
   $('#weekEchart').show()
   //获取本周一
   const startDate = moment().week(moment().week()).startOf('week').format('YYYY-MM-DD');
   const endDate = moment().week(moment().week()).endOf('week').format('YYYY-MM-DD');

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
         month: 'month'
      },
      success: function (result) {
         console.log(result)
         if (result.length > 0) {
            var readDuration = [];
            var outdoorsDuration = [];
            var readDistance = [];
            var readLight = [];
            var lookPhoneDuration = [];
            var lookTvComputerDuration = [];
            var sitTilt = [];
            var useJianhuyiDuration = [];
            var sportDuration = [];

            var readDurations = 0;
            var outdoorsDurations = 0;
            var readDistances = 0;
            var readLights = 0;
            var lookPhoneDurations = 0;
            var lookTvComputerDurations = 0;
            var sitTilts = 0;
            var useJianhuyiDurations = 0;
            var sportDurations = 0;

            var day = [];
            for (const resultElement of result) {
               readDuration.push(resultElement.readDuration)
               outdoorsDuration.push(resultElement.outdoorsDuration)
               readDistance.push(resultElement.readDistance)
               readLight.push(resultElement.readLight)
               lookPhoneDuration.push(resultElement.lookPhoneDuration)
               lookTvComputerDuration.push(resultElement.lookTvComputerDuration)
               sitTilt.push(resultElement.sitTilt)
               useJianhuyiDuration.push(resultElement.useJianhuyiDuration)
               sportDuration.push(resultElement.sportDuration)

               day.push(resultElement.day);

               readDurations += resultElement.readDuration;
               outdoorsDurations += resultElement.outdoorsDuration;
               readDistances += resultElement.readDistance;
               readLights += resultElement.readLights;
               lookPhoneDurations += resultElement.lookPhoneDurations;
               lookTvComputerDurations += resultElement.lookTvComputerDurations;
               sitTilts += resultElement.sitTilt;
               useJianhuyiDurations += resultElement.useJianhuyiDurations;
               sportDurations += resultElement.sportDuration;
            }
            readDurations = readDurations / result.length;
            outdoorsDurations = outdoorsDurations / result.length;
            readDistances = outdoorsDurations / result.length;
            readLights = outdoorsDurations / result.length;
            lookPhoneDurations = outdoorsDurations / result.length;
            lookTvComputerDurations = outdoorsDurations / result.length;
            sitTilts = outdoorsDurations / result.length;
            useJianhuyiDurations = outdoorsDurations / result.length;
            sportDurations = outdoorsDurations / result.length;


            $('#div1')[0].innerText = '总平均' + readDurations + '分钟';
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  name: '分钟',
                  max: 120,
                  splitNumber: 4
               },
               series: [{
                  data: readDuration,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 3
               },
               series: [{
                  data: outdoorsDuration,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '厘米',
                  splitNumber: 5
               },
               series: [{
                  data: readDistance,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 300,
                  name: 'lux',
                  splitNumber: 5
               },
               series: [{
                  data: readLight,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '分钟',
                  splitNumber: 5
               },
               series: [{
                  data: lookPhoneDuration,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '分钟',
                  splitNumber: 8
               },
               series: [{
                  data: lookTvComputerDuration,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  name: '°',
                  splitNumber: 5
               },
               series: [{
                  data: sitTilt,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: useJianhuyiDuration,
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
                  data: day
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: sportDuration,
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


      }

   })
}


//季折线统计图
function quarterEchart() {
   $('#weekEchart').show()
   //获取本周一
   const startDate = moment().week(moment().week()).startOf('week').format('YYYY-MM-DD');
   const endDate = moment().week(moment().week()).endOf('week').format('YYYY-MM-DD');

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
         quarter: 'quarter'
      },
      success: function (result) {
         console.log(result)
         if (result.length > 0) {
            var readDuration = [];
            var outdoorsDuration = [];
            var readDistance = [];
            var readLight = [];
            var lookPhoneDuration = [];
            var lookTvComputerDuration = [];
            var sitTilt = [];
            var useJianhuyiDuration = [];
            var sportDuration = [];

            var readDurations = 0;
            var outdoorsDurations = 0;
            var readDistances = 0;
            var readLights = 0;
            var lookPhoneDurations = 0;
            var lookTvComputerDurations = 0;
            var sitTilts = 0;
            var useJianhuyiDurations = 0;
            var sportDurations = 0;

            var quarter = [];
            for (const resultElement of result) {
               readDuration.push(resultElement.readDuration)
               outdoorsDuration.push(resultElement.outdoorsDuration)
               readDistance.push(resultElement.readDistance)
               readLight.push(resultElement.readLight)
               lookPhoneDuration.push(resultElement.lookPhoneDuration)
               lookTvComputerDuration.push(resultElement.lookTvComputerDuration)
               sitTilt.push(resultElement.sitTilt)
               useJianhuyiDuration.push(resultElement.useJianhuyiDuration)
               sportDuration.push(resultElement.sportDuration)

               quarter.push(resultElement.quarter);

               readDurations += resultElement.readDuration;
               outdoorsDurations += resultElement.outdoorsDuration;
               readDistances += resultElement.readDistance;
               readLights += resultElement.readLights;
               lookPhoneDurations += resultElement.lookPhoneDurations;
               lookTvComputerDurations += resultElement.lookTvComputerDurations;
               sitTilts += resultElement.sitTilt;
               useJianhuyiDurations += resultElement.useJianhuyiDurations;
               sportDurations += resultElement.sportDuration;
            }
            readDurations = readDurations / result.length;
            outdoorsDurations = outdoorsDurations / result.length;
            readDistances = outdoorsDurations / result.length;
            readLights = outdoorsDurations / result.length;
            lookPhoneDurations = outdoorsDurations / result.length;
            lookTvComputerDurations = outdoorsDurations / result.length;
            sitTilts = outdoorsDurations / result.length;
            useJianhuyiDurations = outdoorsDurations / result.length;
            sportDurations = outdoorsDurations / result.length;


            $('#div1')[0].innerText = '总平均' + readDurations + '分钟';
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  name: '分钟',
                  max: 120,
                  splitNumber: 4
               },
               series: [{
                  data: readDuration,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 3
               },
               series: [{
                  data: outdoorsDuration,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '厘米',
                  splitNumber: 5
               },
               series: [{
                  data: readDistance,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 300,
                  name: 'lux',
                  splitNumber: 5
               },
               series: [{
                  data: readLight,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '分钟',
                  splitNumber: 5
               },
               series: [{
                  data: lookPhoneDuration,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '分钟',
                  splitNumber: 8
               },
               series: [{
                  data: lookTvComputerDuration,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  name: '°',
                  splitNumber: 5
               },
               series: [{
                  data: sitTilt,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: useJianhuyiDuration,
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
                  data: quarter
               },
               yAxis: {
                  type: 'value',
                  max: 100,
                  name: '小时',
                  splitNumber: 5
               },
               series: [{
                  data: sportDuration,
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


      }

   })
}


//年折线统计图
function yearEchart() {
   $('#weekEchart').show()

   //格式化日期
   function formatDate(date) {
      var myyear = date.getFullYear();
      var mymonth = date.getMonth() + 1;
      var myweekday = date.getDate();

      if (mymonth < 10) {
         mymonth = "0" + mymonth;
      }
      if (myweekday < 10) {
         myweekday = "0" + myweekday;
      }
      return (myyear + "-" + mymonth + "-" + myweekday);
   }

   const startDate = moment(moment().year(moment().year()).startOf('year').valueOf()).format('YYYY-MM-DD');
   const endDate = moment(moment().year(moment().year()).endOf('year').valueOf()).format('YYYY-MM-DD');

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
      url: 'http://39.106.55.72:8090/jianhuyi/useJianhuyiLog/yearData',
      data: {
         userId: $('#uid').val(),
         start: startDate,
         end: endDate
      },
      success: function (result) {
         console.log(result)
         if (result.code == 0) {
            myChart.hideLoading();
            myChart.setOption({
               title: {
                  text: '日平均单次阅读时长',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgReadDuration,
                  type: 'line'
               }]
            });


            myChart1.hideLoading();
            myChart1.setOption({
               title: {
                  text: '户外总时间',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.outdoorsDuration,
                  type: 'line'
               }]
            });

            myChart2.hideLoading();
            myChart2.setOption({
               title: {
                  text: '平均阅读距离',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgReadDistance,
                  type: 'line'
               }]
            })

            myChart3.hideLoading();
            myChart3.setOption({
               title: {
                  text: '平均阅读光照',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgReadLight,
                  type: 'line'
               }]
            })

            myChart4.hideLoading();
            myChart4.setOption({
               title: {
                  text: '平均单次看手机时长',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgLookPhoneDuration,
                  type: 'line'
               }]
            })

            myChart5.hideLoading();
            myChart5.setOption({
               title: {
                  text: '平均单次看电脑电视时长',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgLookTvComputerDuration,
                  type: 'line'
               }]
            })

            myChart6.hideLoading();
            myChart6.setOption({
               title: {
                  text: '平均坐姿倾斜角度',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgSitTilt,
                  type: 'line'
               }]
            })

            myChart7.hideLoading();
            myChart7.setOption({
               title: {
                  text: '平均有效使用监护仪时长',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.avgUseJianhuyiDuration,
                  type: 'line'
               }]
            })

            myChart8.hideLoading();
            myChart8.setOption({
               title: {
                  text: '运动时长',
                  left: 'center'
               },
               xAxis: {
                  type: 'category',
                  data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
               },
               yAxis: {
                  type: 'value'
               },
               series: [{
                  data: result.data.lineChart.sportDuration,
                  type: 'line'
               }]
            })


         }
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