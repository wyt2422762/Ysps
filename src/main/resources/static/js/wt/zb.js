const ZB = {
  //根据项目id获取招标信息
  getZbByXmId: function (xmid, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: ctx + "CZF/WT/ZB/getDetailByXmId/" + xmid,
      type: "GET",
      async: false,
      contentType: "application/json;charset=utf-8",
      success: function (data) {
        top.layer.close(loadi); //关闭弹出框
        if(callBack){
          callBack(data);
        }
        return false;
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(XMLHttpRequest.responseJSON.msg ? XMLHttpRequest.responseJSON.msg : "操作失败!");
        return false;
      }
    });
    return false;
  },
  //保存询价单位
  saveZb: function (zb, url, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: url,
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(zb),
      success: function (data) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(data.msg);
        if(callBack){
          callBack();
        }
        return false;
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(XMLHttpRequest.responseJSON.msg ? XMLHttpRequest.responseJSON.msg : "操作失败!");
        return false;
      }
    });
    return false;
  }
}