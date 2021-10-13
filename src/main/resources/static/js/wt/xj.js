const XJ = {
  //获取询价单位详情
  getXjDwDetail: function (id, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: ctx + "CZF/WT/XJ/getDetail/" + id,
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
  saveXjDw: function (xj, url, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: url,
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(xj),
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
  },
  //删除询价单位
  delXjDw: function (id, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: ctx + "CZF/WT/XJ/delXj/" + id,
      type: "POST",
      success: function (data) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(data.msg);
        if(callBack){
          callBack();
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(XMLHttpRequest.responseJSON.msg ? XMLHttpRequest.responseJSON.msg : "操作失败!");
      }
    });
    return false;
  },
  //确认询价单位
  confirmXjDw: function (xmid, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: ctx + "CZF/WT/XJ/confirmXj/" + xmid,
      type: "POST",
      success: function (data) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(data.msg);
        if(callBack){
          callBack();
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(XMLHttpRequest.responseJSON.msg ? XMLHttpRequest.responseJSON.msg : "操作失败!");
      }
    });
    return false;
  }
}