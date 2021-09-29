const XMFJ = {
  //删除项目附件
  delXmFj: function delXmFj(path, callBack) {
    let loadi = top.layer.load();
    $.ajax({
      url: ctx + "common/del/upload",
      type: "GET",
      contentType: "application/json;charset=utf-8",
      data: {"resource": path},
      success: function (data) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(data.msg);
        callBack();
        return false;
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        top.layer.close(loadi); //关闭弹出框
        top.layer.msg(XMLHttpRequest.responseJSON.msg);
        return false;
      }
    });
    return false;
  },
  //下载项目附件
  downloadXmfj: function(path, name) {
    let dUrl = ctx + "common/download/upload?resource=" + path;
    if(name) {
      dUrl += "&name=" + name;
    }
    window.open(dUrl);
  }
};
