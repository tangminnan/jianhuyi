
var prefix = "/information/userTaskLinshi"
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
						// pageSize : 10, // 如果设置了分页，每页数据条数
						// pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
			//			sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                taskId:$("#taskId").val()
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
                                field : 'day',
                                title : '日期'
                            },

																{
									field : 'avgRead', 
									title : '平均单次阅读时长' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgOut', 
									title : '平均户外时长',
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgReadDistance', 
									title : '平均阅读距离' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgLookPhone', 
									title : '平均单次看手机时长' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgLookTv', 
									title : '看电子屏' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgSitTilt', 
									title : '平均坐姿倾斜度' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'effectiveUseTime', 
									title : '有效使用时长' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
																{
									field : 'avgLight', 
									title : '平均阅读光照' ,
                                                                    formatter : function(value, row, index) {
                                                                        if(value==5) return "优秀";
                                                                        if(value==4) return "一般";
                                                                        if(value==2) return "差";
                                                                        if(value==1) return "极差";
                                                                    }
								},
								// 								{
								// 	field : 'createTime',
								// 	title : '创建时间'
								// },
																{
									field : 'score', 
									title : '当日所获积分' 
								},
																{
									field : 'hourtime', 
									title : '监护仪有效使用时长' 
								},


																]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
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
	layer.confirm('确定要删除选中的记录？', {
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

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}