$().ready(function () {
   $('.summernote').summernote({
      height: '220px',
      lang: 'zh-CN',
      focus: true,
      callbacks: {
         /* onImageUpload: function (files, editor, welEditable) {
             sendFile(files[0], editor, welEditable);
          }*/
      }
   });
   validateRule();
});

$.validator.setDefaults({
   submitHandler: function () {
      save();
   }
});

function sendFile(files, editor, welEditable) {
   data = new FormData();
   data.append("file", files);

   $.ajax({
      data: data,
      type: "POST",
      url: "/information/gift/saveImg",
      cache: false,
      contentType: false,
      processData: false,
      success: function (data) {
         console.log(editor)
         //editor.insertImage(welEditable, data.fileName);
      },
      error: function () {
         layer.alert('上传失败!');
         return;
      }

   });
}

function save() {
   var content_sn = $("#content_sn").summernote('code');
   $("#giftDetails").val(content_sn);
   var formData = new FormData(document.getElementById("signupForm"));
   $.ajax({
      cache: true,
      type: "POST",
      url: "/information/gift/save",
      data: formData,// 你的formid
      processData: false,
      contentType: false,
      async: false,
      error: function (request) {
         parent.layer.alert("Connection error");
      },
      success: function (data) {
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
      rules: {
         name: {
            required: true
         }
      },
      messages: {
         name: {
            required: icon + "请输入姓名"
         }
      }
   })
}