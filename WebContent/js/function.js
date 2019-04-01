
    function postAjax(url,param,callback){
        $.ajax({
            url : url,
            type : 'GET',
            data : param,
            dataType : 'text',
            async:false,
            success : function(data) {
//            	    console.log(data);
                    callback(data);
            },
            error : function(data, status, e) {
                alert(e);
            },
            complete : function() {
            }
        });
    }
    
    function getAjax(url,param){
        var info;
        $.ajax({
            url : url,
            type : 'GET',
            data : param,
            async: false,
            dataType : 'json',
            success : function(data, status) {
                if (status == "success") {
                    info= data;
                }
            },
            error : function(data, status, e) {
                alert(e);
            },
            complete : function() {
            }
        });
        return info;
    }

    function feedback(result)
    {
    	console.log("feedback");
    	if(result=="success") alert("操作成功！");
    	else alert("操作失败！");
    }



