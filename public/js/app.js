$(function() {
	$("#alert").hide();
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
					$("#chatInput").val("");
					rollChatDown();
				},
				failure: function(errMsg) {
		        	$("#alert").show();
		        }
			});
		}
	});
	
	function doPoll() {
		$.ajax({
			type: "GET",
			url: server + "/chat",
			data: {"lastRequest": messagesUntil},
			dataType: "json",
			success: function(messages) {
				if(messages.length > 0) {
					messagesUntil = messages[messages.length - 1].timestamp;
					for(var i=0; i < messages.length; i++) {
						var newRow = "<tr><td>" + messages[i].user + "</td><td>" + messages[i].message + "</td></tr>";
						$("#chatTable > tbody:last").append(newRow);
                }
                }
                $("#alert").hide();
			}
		});
		setTimeout(doPoll,1000);
	};

	function rollChatDown() {
		$("#chat").stop().animate({scrollTop: $("#chat")[0].scrollHeight},1000);
	};
});
