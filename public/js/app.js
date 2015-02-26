$(function() {
	//$("#alert").hide();
	var server = "http://finanssi.nuthou.se:8080";
	var name = null;
	var messagesUntil = 1;
	var chatLoaded = false;
	var loggedUser = null;

	setTimeout(doPoll,1000);
	
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
				if(!chatLoaded) {
					rollChatDown();
					chatLoaded = true;
				}
			}
		});
		setTimeout(doPoll,1000);
	};

	function rollChatDown() {
		$("#chat").stop().animate({scrollTop: $("#chat")[0].scrollHeight},500);
	};

	$("#buttonNewUser").click(function() {
		$("#newUserEmail").val("");
		$("#newUserNickname").val("");
		$("body").append('<div class="modalOverlay" style="display: none;">');
		$(".modalOverlay").fadeIn(200);
		$("#newUser").fadeIn(200);
	});
	
	$("#newUserClose").click(function() {
		closeOverlay();
	});
	
	function closeOverlay() {
		$("#newUser").fadeOut(200);
		$(".modalOverlay").fadeOut(200, function() {
			$(".modalOverlay").remove();
		});
	};
	
	$("#buttonSignIn").click(function() {
		var user = new Object();
		user.email = $("#signInEmail").val();
		user.password = $("#signInPassword").val();
		var jsonUser = JSON.stringify(user);
		$.ajax({
			type: "POST",
			url: server + "/user/login",
			data: jsonUser,
			contentType: "application/json",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(data) {
					$("#userStuff").hide();
					$("#loggedIn").show();
					loggedUser = data.email;
					name = data.userName;
					$("#chatInput").prop('disabled', false);
					$("#loggedInEmail").html("Signed in: " + loggedUser);
				},
				400: function(){
					alert("Login failed");
				}
			}
		});
	});
	
	$("#register").click(function() {
		var user = new Object();
		user.email = $("#newUserEmail").val();
		user.userName = $("#newUserNickname").val();
		var jsonUser = JSON.stringify(user);
		$.ajax({
			type: "POST",
			url: server + "/user/create",
			data: jsonUser,
			contentType: "application/json",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(data) {
					alert("OK");
					closeOverlay();
				},
				400: function(){
					alert("Registration failed");
				}
			}
		});
	});
	
	$("#buttonSignOut").click(function() {
		$("#userStuff").show();
		$("#loggedIn").hide();
		loggedUser = null;
		name = null;
		$("#chatInput").prop('disabled', true);
	});
});
