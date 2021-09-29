//向后台发送请求(添加、修改字典)
function aeZd(data, type, indexOut) {
  let loadi = top.layer.load();

  $.ajax({
    url: ctx + "GYFW/CZF_ZDGL/aeZd/" + type,
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    data: JSON.stringify(data),
    success: function (data) {
      top.layer.close(loadi); //关闭弹出框
      top.layer.msg(data.msg);
      top.layer.close(indexOut); //关闭弹出框
      TAB.refreshLayerOpen();
      return false;
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      top.layer.close(loadi); //关闭弹出框
      top.layer.msg(XMLHttpRequest.responseJSON.msg);
      return false;
    }
  });
}