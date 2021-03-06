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
               /* {
                   checkbox: true
                },
                {
                   field: 'id',
                   title: 'id'
                },*/
               {
                  field: 'uploadId',
                  title: '上传者id',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'userId',
                  title: '用户Id',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'equipmentId',
                  title: '设备号',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'useTime',
                  title: '数据时间',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'addTime',
                  title: '添加时间',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'allReadDuration',
                  title: '总阅读时长</br>(min)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'readDuration',
                  title: '平均阅读时长</br>(min）',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'outdoorsDuration',
                  title: '总户外时长</br>(min）',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'readDistance',
                  title: '平均阅读距离</br>(cm)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'readLight',
                  title: '平均阅读光照</br>(lux)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'lookPhoneDuration',
                  title: '看手机时长</br>(min)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'lookTvComputerDuration',
                  title: '看电脑电视时长</br>(min)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'sitTilt',
                  title: '坐姿倾斜度 -右偏 +左偏</br>(°)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'remind',
                  title: '提醒次数',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'runningTime',
                  title: '开机时长</br>(h)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'effectiveTime',
                  title: '有效时长</br>(h)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'coverTime',
                  title: '遮挡</br>(min)',
                  valign: 'middle',
                  align: 'center'
               },
               {
                  field: 'noneffectiveTime',
                  title: '无效</br>(min)',
                  valign: 'middle',
                  align: 'center'
               },
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
                        var f = '<a class="btn btn-primary btn-sm" href="#" title="监护仪报告"  mce_href="#" onclick="sixteen(\'' + row.userId + '\',\'' + row.useTime + '\')"><i class="fa fa-navicon"></i></a> ';

                         return e + d +f;
                     }
               }]
         });
}

function reLoad() {
   $('#exampleTable').bootstrapTable('refresh');
}

function detaildata(userId, saveTime) {
   var page = layer.open({
      type: 2,
      title: '当日数据详情',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/useJianhuyiLog/useJianhuyiLogDetail?userId=' + userId + '&saveTime=' + saveTime // iframe的url
   });
   layer.full(page)
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

/**
 * 16分区数据比对导出
 */
function sixteen(userId,saveTime){
  window.location.href="/information/data/exportDistanceData?userId="+userId+"&saveTime="+saveTime
}

/*function treeDetail(index, row) {
   var alldate = {
      userId: row.userId,
      useTime: row.useTime
   };
   var str = "";
   var cggys = "";
   $.ajax({
      url: prefix + "/list",
      method: 'get',                      //请求方式（*）
//            contentType: 'application/json; charset=utf-8',
      dataType: "json",
      data: alldate,
      beforeSend: function () {
         close = layer.load(2);
      },
      success: function (data) {
         layer.close(close);
         $.each(data, function (index, DesignEchartsDetail) {
            var num = index + 1;
            cggys = DesignEchartsDetail.cggys == null ? "-" : DesignEchartsDetail.cggys;
            var ondblclick = "dblclick" + num;
            tree = "tree" + num;
            str += "<tr id=" + "'" + ondblclick + "'" + " class='detail-view'>";
            str += "<td style='width: 50px' align='center'></td>";
            str += "<td style='width: 50px' align='center'>" + num + "</td>";
            str += "<td class='colStyle' align='center'>" + DesignEchartsDetail.shareorderno + "</td>";
            str += "<td class='colStyle' align='center'>" + DesignEchartsDetail.putype + "</td>";
            str += "<td class='colStyle' align='center'>" + DesignEchartsDetail.planstarttime + "</td>";
            str += "</tr>";
         });
         //获取到加减号的class .detail-icon，然后获取第index个  拿到他的父亲的父亲（也就是当前行）
         $(".detail-icon").eq(index).parent().parent().after(str);
      }
   })
   return '';
}*/

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