var prefix = "/information/data"
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
            theadClasses: "thead-dark",
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
            dataVisible: 'center',
            queryParams: function (params) {
               return {
                  //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                  limit: params.limit,
                  offset: params.offset,
                  startTime: $('#startTime').val(),
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
               /*{
                  checkbox : true
               },*/
               {
                  field: 'id',
                  title: 'id',
                  align: 'center'
               },
               {
                  field: 'startTime',
                  title: '开始时间',
                  align: 'center'
               }, {
                  field: 'updateTime',
                  title: '上传时间',
                  align: 'center'
               }, {
                  field: 'equipmentId',
                  title: '设备号',
                  align: 'center'
               }, {
                  field: 'picNum',
                  title: '图片总数',
                  align: 'center'
               },
               {
                  field: 'picStatus',
                  title: '图片解析失败<br>数量',
                  align: 'center'
               },
               {
                  field: 'status',
                  title: '状态',
                  align: 'center',
                  formatter: function (value) {
                     if (value == 0) {
                        return "初始状态"
                     } else if (value == 1) {
                        return "阅读"
                     } else if (value == 2) {
                        return "非阅读"
                     } else if (value == 3) {
                        return "无效"
                     } else if (value == 4) {
                        return "遮挡状态"
                     } else if (value == 5) {
                        return "USB接入"
                     } else {
                        return "";
                     }
                  }
               },
               /*			{
   field : 'baseData',
   title : '角度信息'
},
                        {
   field : 'energys',
   title : '电量信息'
},
                        {
   field : 'remaind',
   title : '震动提醒'
},
                        {
   field : 'errors',
   title : '错误信息'
}*/
               {
                  title: '操作',
                  field: 'id',
                  align: 'center',
                  formatter: function (value, row, index) {
                     var e = '<a class="btn btn-primary btn-sm " href="#" mce_href="#" title="角度信息" onclick="jiaodu(\'' + row.id + '\',\'' + row.startTime + '\')"><i class="fa fa-terminal">角度信息</i></a> ';
                     /*var d = '<a class="btn btn-warning btn-sm " href="#" title="电量信息"  mce_href="#" onclick="dianliang(\''
                           + row.id
                           + '\')"><i class="fa fa-battery-quarter">电量信息</i></a> ';*/
                     var f = '<a class="btn btn-success btn-sm" href="#" title="震动提醒"  mce_href="#" onclick="zhendong(\''
                        + row.id
                        + '\')"><i class="fa fa-bell-slash-o">震动提醒</i></a> ';
                     /* var g = '<a class="btn btn-danger btn-sm" href="#" title="错误信息"  mce_href="#" onclick="errors(\''
                          + row.id
                          + '\')"><i class="fa fa-exclamation-triangle">错误信息</i></a> ';*/
                     var h = '<a class="btn btn-info btn-sm" href="#" title="图片信息"  mce_href="#" onclick="pic(\''
                        + row.id
                        + '\')"><i class="fa fa-image">图片信息</i></a> ';

                     return e + f;
                  }
               }]
         });
}

function reLoad() {
   $('#exampleTable').bootstrapTable('refresh');
};

function jiaodu(id, startTime) {
   var page = layer.open({
      type: 2,
      title: '角度信息',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/jiaodu?id=' + id + '&startTime=' + startTime // iframe的url
   });
   layer.full(page);
}

function zhendong(id) {
   var page = layer.open({
      type: 2,
      title: '震动信息',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/zhendong/' + id // iframe的url
   });
   layer.full(page);
}

function pic(id) {
   var page = layer.open({
      type: 2,
      title: '震动信息',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/dataImg/pic/' + id // iframe的url
   });
   layer.full(page);
}

function errors() {
   var page = layer.open({
      type: 2,
      title: '错误信息',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/errorData/listdata?userId=' + $('#userId').val() + '&time=' + $('#saveTime').val() // iframe的url
   });
   layer.full(page);
}

function energys() {
   var page = layer.open({
      type: 2,
      title: '错误信息',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/energysData/listdata?userId=' + $('#userId').val() + '&time=' + $('#saveTime').val() // iframe的url
   });
   layer.full(page);
}