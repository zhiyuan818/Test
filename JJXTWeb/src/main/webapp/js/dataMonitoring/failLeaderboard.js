$(function(){
	// 弹出框取消按钮事件
	$('.popup_de .btn_cancel').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');
	})
	
	$("#search_appId").click(function(){
		var appId=$("#appId").val();
		if(appId=='' || appId==null || appId==undefined){
			$('.popup_de .show_msg').text('请输入账号');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}else{
			$.ajax({
				url:"findAppFailLeaderboard.action",
				data:{appId:appId},
				type:"post",
				success:function(data){
					$("#appBody").empty();
					if(data !=null && data.length>0){
						for(var i in data){
							$("#appBody").append("<tr><td>"+(parseInt(i)+parseInt(1))+"</td><td>"+data[i].key+"</td><td>"+data[i].score+"</td></tr>");
						}
					}
				},
				dataType:"json"
				
			});
		}
	});
	
	$("#search_channelId").click(function(){
		var channelId=$("#channelId").val();
		if(channelId=='' || channelId==null || channelId==undefined){
			$('.popup_de .show_msg').text('请输入通道');
			$('.popup_de .btn_cancel').css('display', 'none');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				$('.popup_de').removeClass('bbox');
			})
			return ;
		}else{
			$.ajax({
				url:"findChannelFailLeaderboard.action",
				data:{channelId:channelId},
				type:"post",
				success:function(data){
					$("#channelBody").empty();
					if(data !=null && data.length>0){
						for(var i in data){
							$("#channelBody").append("<tr><td>"+(parseInt(i)+parseInt(1))+"</td><td>"+data[i].key+"</td><td>"+data[i].score+"</td></tr>");
						}
					}
				},
				dataType:"json"
					
			});
		}
		
	});
	
	
})