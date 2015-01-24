$(function() {
	$("#alert").hide();
	//var button = $("#send");
	var server = "http://finanssi.nuthou.se:8080";
	var name = prompt("Enter your nickname", "default");
	var messagesUntil = null;

	doPoll();
	rollChatDown();
	
	$("#chatInput").keyup(function(event) {
		if(event.keyCode == 13) {
			var chatmessage = new Object();
			chatmessage.user = name;
			chatmessage.message = $("#chatInput").val();
			var jsonMessage = JSON.stringify(chatmessage);
			$.ajax({
				type: "POST",
				url: server + "/chat",
				data: jsonMessage,
				contentType: "application/json",
				dataType: "json",
				crossDomain: true,
				success: function(data) {
//					printChat(data);
					$("#chatInput").val("");
					rollChatDown();
				},
				failure: function(errMsg) {
		        	$("#alert").show();
		        }
			});
		}
	});
	
//	function printChat(data) {
//		var messages = data.messages;
//		var chatHtml = "";
//		for(var i=0; i < messages.length; i++) {
//			chatHtml += "<tr><td>" + messages[i].user + "</td><td>" + messages[i].message + "</td></tr>";
//		}
//		$("#chatTable").html(chatHtml);
//		$("#alert").hide();
//	};
	
	function doPoll() {
		$.ajax({
			type: "GET",
			url: server + "/chat",
			data: {"lastRequest",messagesUntil},
			dataType: "json",
			success: function(messages) {
//				var messages = data.messages;
				messagesUntil = +new Date();
                var chatHtml = "";
                for(var i=0; i < messages.length; i++) {
                	chatHtml += "<tr><td>" + messages[i].user + "</td><td>" + messages[i].message + "</td></tr>";
                }
                $("#chatTable").html(chatHtml);
                $("#alert").hide();
			}
		});
		setTimeout(doPoll,1000);
	};

	function rollChatDown() {
		$("#chat").stop().animate({scrollTop: $("#chat")[0].scrollHeight},1000);
	};
});
