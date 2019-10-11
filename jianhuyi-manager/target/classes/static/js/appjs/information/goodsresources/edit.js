$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/goodsresources/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}


function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			userName : {
				required : true
			},
			linkman : {
				required : true
			},
			phone : {
				required : true,
				isMobile :true
			},
			startTime : {
				required : true
			},
			sAdress : {
				required : true
			},
			eAdress : {
				required : true
			},
			carSize : {
				digits:true,
				required : true
			},
			carType : {
				required : true
			}
		},
		messages : {
			userName : {
				required : icon + "请输入用户姓名"
			},
		   linkman : {
				required : icon + "请输入联系人姓名"
			},
			phone : {
				required : icon + "请输入联系人电话"
			},
			startTime : {
				required : icon + "请输入订单发布时间"
			},
			sAdress : {
				required : icon + "请输入始发地"
			},
			eAdress : {
				required : icon + "请输入目的地"
			},
			carSize : {
				required : icon + "请输入数字"
			},
			carType : {
				required : icon + "请输入货车类型"
			}
		}
	})
}

//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");