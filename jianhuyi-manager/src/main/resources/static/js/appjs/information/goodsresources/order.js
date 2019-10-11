
var prefix = "/information/goodsresources"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset
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
						columns : [
								{
									checkbox : true
								},
																{
									field : 'id', 
									title : 'id' 
								},
//																{
//									field : 'type', 
//									title : '0：物业费；1：维修费' 
//								},
//																{
//									field : 'orderNum', 
//									title : '订单号' 
//								},
//																{
//									field : 'orderMoney', 
//									title : '订单金额' 
//								},
//																{
//									field : 'userId', 
//									title : '用户id' 
//								},
//																{
//									field : 'ownerId', 
//									title : '业主id' 
//								},                             
//																{
//									field : 'name', 
//									title : '用户姓名' 
//								},
																{
									field : 'linkman', 
									title : '联系人' 
								},
																{
									field : 'phone', 
									title : '联系电话' 
								},
//																{
//									field : 'plot', 
//									title : '所在小区' 
//								},
//																{
//									field : 'money', 
//									title : '金额' 
//								},
//																{
//									field : 'payType', 
//									title : '缴费类型' 
//								},
//																{
//									field : 'maintainType', 
//									title : '维修类型' 
//								},
																{
									field : 'orderType', 
									title : '订单状态', 
									formatter: function (value, row, index){
										var text="-";
										if (value == 0) {
						                      text = "已发布";
						                  } else if (value == 1) {
						                      text = "已接单";
						                  } else if (value == 2) {
						                      text = "已完成";
						                  }else if (value == 3) {
						                      text = "已取消";
						                  }
									return text;
								}
							},
//																{
//									field : 'maintainTime', 
//									title : '维修时间' 
//								},
//																{
//									field : 'orderDetails', 
//									title : '订单备注' 
//								},
																{
									field : 'startTime', 
									title : '发布时间' 
								},
//																{
//									field : 'addTime', 
//									title : '添加时间' 
//								},
//																{
//									field : 'updateTime', 
//									title : '修改时间' 
//								},
//																{
//									field : 'deleteFlg', 
//									title : '0：是；1：否' 
//								},
																{
									field : 'sAdress', 
									title : '始发地' 
								},
																{
									field : 'eAdress', 
									title : '目的地' 
								},
																{
									field : 'carSize', 
									title : '货车车长' 
								},
																{
									field : 'carType', 
									title : '车类型' 
								},
																{
									field : 'goodsType', 
									title : '货物类型' 
								},
																{
									field : 'goodsWeight', 
									title : '货物重量' 
								},
																{
									field : 'driverPhone', 
									title : '司机号码' 
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="取消"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="请输入司机手机号"  mce_href="#" onclick="confim(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d +f;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function confim(id){
	layer.open({
		type : 2,
		title : '司机手机号',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '300px' ],
		content : prefix + '/siji/'+id // iframe的url
	});
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要取消选中的订单？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}


//function batchRemove() {
//	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
//	if (rows.length == 0) {
//		layer.msg("请选择要删除的数据");
//		return;
//	}
//	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
//		btn : [ '确定', '取消' ]
//	// 按钮
//	}, function() {
//		var ids = new Array();
//		// 遍历所有选择的行数据，取每条数据对应的ID
//		$.each(rows, function(i, row) {
//			ids[i] = row['id'];
//		});
//		$.ajax({
//			type : 'POST',
//			data : {
//				"ids" : ids
//			},
//			url : prefix + '/batchRemove',
//			success : function(r) {
//				if (r.code == 0) {
//					layer.msg(r.msg);
//					reLoad();
//				} else {
//					layer.msg(r.msg);
//				}
//			}
//		});
//	}, function() {
//
//	});
//}