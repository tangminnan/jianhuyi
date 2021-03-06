var prefix = "/information/users"
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
                  id: $('#id').val(),
                  name: $('#name').val(),
                  school: $('#school').val(),
                  grade: $('#grade').val(),
                  phone: $('#phone').val()
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
                },*/
               {
                  field: 'id',
                  title: 'id'
               },
               /*	                            {
              field : 'userId',
              title : '用户ID'
          },					{
              field : 'openId',
              title : '微信id'
          },
          */      /*                          {
                        field: 'nickname',
                        title: '昵称',
                        align:'center'
                    },*/
               /*							{
                   field : 'password',
                   title : '密码'
               },
               */                              {
                  field: 'name',
                  title: '姓名',
                  align: 'center'
               }, {
                  field: 'phone',
                  title: '手机号',
                  align: 'center'
               },
               /*								{
                   field : 'heardUrl',
                   title : '头像'
               },
               */
               {
                  field: 'sex',
                  title: '性别',
                  align: 'center',
                  formatter: function (value, row, index) {
                     if (value == '1') {
                        return '<span class="label">男</span>';
                     } else if (value == '2') {
                        return '<span class="label">女</span>';

                     }
                  }
               },
               {
                  field: 'identityCard',
                  title: '身份证号',
                  align: 'center'
               },
               {
                  field: 'birthday',
                  title: '生日',
                  align: 'center',
                  formatter: function (value) {
                     if (value != null) {
                        return value.split(" ")[0];
                     } else {
                        return "";
                     }
                  }

               },

               {
                  field: 'age',
                  title: '年龄',
                  align: 'center'
               }, {
                  field: 'school',
                  title: '学校',
                  align: 'center'
               }, {
                  field: 'grade',
                  title: '班级',
                  align: 'center'
               },
               {
                  field: 'isWearGlasses',
                  title: '是否戴镜',
                  align: 'center'
               },
               {
                  field: 'studentNum',
                  title: '学号',
                  align: 'center'
               },
               {
                  field: 'lVision',
                  title: '左眼视力',
                  align: 'center'
               }, {
                  field: 'rVision',
                  title: '右眼视力',
                  align: 'center'
               }, {
                  field: 'lEyeBallDiameter',
                  title: '左眼球径',
                  align: 'center'
               }, {
                  field: 'rEyeBallDiameter',
                  title: '右眼球径',
                  align: 'center'
               }, {
                  field: 'lColumnDiameter',
                  title: '左眼柱径',
                  align: 'center'
               }, {
                  field: 'rColumnDiameter',
                  title: '右眼柱径',
                  align: 'center'
               }, {
                  field: 'lEyeAxis',
                  title: '左眼轴',
                  align: 'center'
               }, {
                  field: 'rEyeAxis',
                  title: '右眼轴',
                  align: 'center'
               }, {
                  field: 'registerTime',
                  title: '注册时间',
                  align: 'center'
               },
               /*								{
                   field : 'payNum',
                   title : '消费金额'
               },
                                               {
                   field : 'serveNum',
                   title : '服务次数'
               },
                                               {
                   field : 'balance',
                   title : '余额'
               },
                                               {
                   field : 'restitution',
                   title : '返还'
               },
                                               {
                   field : 'payTime',
                   title : '缴费日期'
               },
               */                                {
                  field: 'loginTime',
                  title: '最后登录时间'
               }, {
                  field: 'bindType',
                  title: '绑定类型',
                  formatter: function (value) {
                     console.log(value)
                     if (value == 1) {
                        return "管理员"
                     } else {
                        return "普通用户"
                     }
                  }
               }, {
                  field: 'managerId',
                  title: '所属管理id',
                  formatter: function (value) {
                     if (value == 0) {
                        return "无"
                     } else {
                        return value;
                     }
                  }
               },
               /*								{
                   field : 'addTime',
                   title : '添加时间'
               },
                                               {
                   field : 'updateTime',
                   title : '修改时间'
               },

                                               {
                   field : 'username',
                   title : ''
               },
               */
               {
                  field: 'deleteFlag',
                  title: '状态',
                  align: 'center',
                  formatter: function (value, row, index) {
                     if (value == '0') {
                        return '<span class="label label-danger">禁止</span>';
                     } else if (value == '1') {
                        return '<span class="label label-primary">正常</span>';

                     }
                  }
               },


               {
                  title: '操作',
                  field:
                     'id',
                  align:
                     'center',
                  formatter:

                     function (value, row, index) {

                        var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                           + row.id
                           + '\')"><i class="fa fa-edit"></i></a> ';
                        var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="状态"  mce_href="#" onclick="remove(\''
                           + row.id
                           + '\')"><i class="fa fa-remove"></i></a> ';
                        var f = '<a class="btn btn-success btn-sm" title="历史记录详情"  mce_href="#" onclick="showdetail(\''
                           + row.id
                           + '\')"><i class="fa fa-list"></i></a> ';


                         var g = '<a class="btn btn-primary btn-sm" title="兑换记录"  mce_href="#" onclick="duihuanjilu(\''
                             + row.id
                             + '\')"><i class="fa fa-list"></i></a> ';
                        var b = '<a class="btn btn-danger btn-sm" title="设置为管理员"  mce_href="#" onclick="setManager(\''
                           + row.id
                           + '\')"><i class="fa fa-list">设为管理员</i></a> ';

                        var c = '<a class="btn btn-danger btn-sm getUser" title="查看我的用户"  mce_href="#" ><i class="fa fa-list">查看我的用户</i></a> ';

                        if (row.bindType != 1 && row.managerId == 0) {
                           return e + d + f +g+ b;
                        } else {
                           return e + d + f+g
                           /*+ c;*/
                        }


                     }
               }
            ]
         })
   ;
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
      area: ['1000px', '800px'],
      content: prefix + '/add' // iframe的url
   });
}

function duihuanjilu(id){
   window.location.href="/information/myGift/duihuanjilu/"+id;
}

function batchAdd() {
   layer.open({
      type: 2,
      title: '批量导入',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/importtemplate' // iframe的url
   });
}


function setManager(id) {
   $.ajax({
      cache: true,
      type: "POST",
      url: "/information/users/update",
      data: {
         id: id,
         bindType: 1
      },// 你的formid
      async: false,
      error: function (request) {
         layer.alert("Connection error");
      },
      success: function (data) {
         if (data.code == 0) {
            layer.msg("操作成功");
            reLoad();
            /*var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);*/

         } else {
            layer.alert(data.msg)
         }

      }
   });
}

function batchBind() {
   layer.open({
      type: 2,
      title: '批量绑定',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/batchBind' // iframe的url
   });
}


function details(id) {
   layer.open({
      type: 2,
      title: '详情',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/data/lists/' + id // iframe的url
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

function daoru() {
   var formData = new FormData(document.getElementById("signupForm"));
   $.ajax({
      cache: true,
      type: "POST",
      url: prefix + "/importMember",
      data: formData,// 你的formid
      processData: false,
      contentType: false,
      async: false,
      error: function (request) {
         parent.layer.alert("网络超时");
      },
      success: function (data) {
         if (data.code == 0) {
            parent.layer.msg(data.msg);
            parent.reLoad();
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
         } else {
            parent.layer.alert(data.msg)
         }

      }
   });
}

function remove(id) {
   layer.confirm('确定要禁用选中的记录？', {
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

function showdetail(id) {
   var showpage = layer.open({
      type: 2,
      title: '查看详情',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: '/information/dataEveryday/detail/' + id // iframe的url
   });
   layer.full(showpage);

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

/**
 *  数据导出
 */
function exportData(){
   let school=$("#school").val();
   let grade=$("#grade").val();
   let date=$("#date").val();
   if(school==""){
      alert("学校不可为空");
      return;
   }
   if(grade==""){
      alert("班级不可为空");
      return;
   }
   if(date==""){
      alert("数据日期不可为空");
      return;
   }

   window.location.href="/information/dataEveryday/exportClassData?school="+school+"&grade="+grade+"&date="+date;
}

function exportReportC() {
    let school = $("#school").val();
    let grade = $("#grade").val();
    let date = $("#date").val();
    if (school == "") {
        alert("学校不可为空");
        return;
    }
    if (grade == "") {
        alert("班级不可为空");
        return;
    }
    if (date == "") {
        alert("数据日期不可为空");
        return;
    }
    let params=[];
    $.ajax({
        url: "/information/dataEveryday/getEchartsDataC",
        method: "GET",
        async:false,
        data: {school, grade, date},

        success: result => {
            console.info(result);
            if (result.code == -1) {
                alert("没有当天的数据");
            } else {
                for (let key in result) {
                    if(key=="code") continue;
                    let res = result[key];
                    let loghtF = res.loghtF;
                    let distanceF = res.distanceF;
                    let sitF = res.sitF;
                    let lightChart = echarts.init(document.getElementById("light"));
                    let name = res.name;
                    let sex = res.sex;
                    let userId = res.userId;
                    let data1 = [];
                    for (let da of res.lights) {
                        data1.push({name: da.name, value: da.num});
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
                            padding: [0, 300, 0, 0],
                            orient: 'vertical',
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
                                return name + '  ' + val[0].value;
                            }
                        },
                        animation: false,
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '220px',
                                center: ['60%', '55%'],
                                data: data1,
                                label: {
                                    normal: {
                                        textStyle: {
                                            fontWeight: 'normal',
                                            fontSize: 30
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
                    let data2 = [];
                    for (let da of res.distances) {
                        data2.push({name: da.name, value: da.num});
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
                            padding: [0, 300, 0, 0],
                            orient: 'vertical',
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
                                return name + '  ' + val[0].value;
                            }
                        },
                        animation: false,
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '220px',
                                center: ['60%', '55%'],
                                data: data2,
                                label: {
                                    normal: {
                                        textStyle: {
                                            fontWeight: 'normal',
                                            fontSize: 30
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
                    let data3 = [];
                    for (let da of res.sits) {
                        data3.push({name: da.name, value: da.num});
                    }
                    var option3 = {

                        tooltip: {trigger: 'item'},
                        legend: {
                            itemGap: 16,
                            icon: 'rect',
                            left: 'left',  //图例距离左的距离
                            top: 'center',
                            itemWidth: 150,
                            itemHeight: 80,
                            padding: [0, 300, 0, 0],
                            orient: 'vertical',
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
                        animation: false,
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '220px',
                                center: ['60%', '55%'],
                                data: data3,
                                label: {
                                    normal: {
                                        textStyle: {
                                            fontWeight: 'normal',
                                            fontSize: 30
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
                    //          setTimeout(() => {
                    let sn = sitChart.getDataURL().split("base64,")[1];
                    let ln = lightChart.getDataURL().split("base64,")[1];
                    let dn = distanceChart.getDataURL().split("base64,")[1];
                    params.push({
                        userId: key,
                        school,
                        grade,
                        date,
                        sn,
                        ln,
                        dn,
                        loghtF,
                        distanceF,
                        sitF,
                        name,
                        sex,
                        userId
                    });
                    $("#light").html("");
                    $("#distance").html("");
                    $("#sit").html("");
                    //         }, 10);
                }
                setTimeout(() => {
                    let form = document.createElement("form");
                    form.style.display = 'none';
                    form.action = "/information/dataEveryday/exportReportC"
                    form.method = "post";
                    document.body.appendChild(form);
                    console.info("ddd=  " + params.length);
                    for (let i = 0; i < params.length; i++) {

                        let data = params[i];
                        for (let key in data) {
                            var input = document.createElement("input");
                            //       input.type = "hidden";
                            input.name = "dataList[" + i + "]." + key;
                            input.value = data[key];
                            console.info(input)
                            form.appendChild(input);
                        }
                    }
                    form.submit();
                    form.remove();
                }, 2000)


            }
        }

});

}

