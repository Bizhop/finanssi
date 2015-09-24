$(function() {
	//$("#alert").hide();
	var server = "http://finanssi.port0.org:8080";
	var name = null;
	var messagesUntil = 1;
	var chatLoaded = false;
	var loggedUser = null;
	var currentGame = null;

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
		$(".modalOverlay").click(function() {
			closeOverlay();
		});
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
					$("#game").show();
					loggedUser = data.email;
					name = data.userName;
					$("#chatInput").prop('disabled', false);
					$("#loggedInEmail").html("Signed in: " + loggedUser);
					listGames();
				},
				400: function(){
					alert("Login failed");
				}
			}
		});
	});
	
	function listGames() {
		$.ajax({
			type: "GET",
			url: server + "/game/getGames",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(games) {
					if(games.length > 0) {
						for(var i=0; i < games.length; i++) {
							var gameId = games[i];
							var newRow = "<tr><td>" + gameId + "</td><td><button class='button' type='button' data-value='" + gameId + "'>Join</button></td></tr>";
							$("#gameList > tbody:last").append(newRow);
							$("button[data-value='" + gameId + "']").click(function() {
								joinGame($(this).attr("data-value"));
							});
						}
					}
				}
			}
		});
	};
	
	$("#roll").click(function() {
		var gameForm = new Object();
		gameForm.player = name;
		gameForm.gameId = currentGame;
		var jsonMessage = JSON.stringify(gameForm);
		$.ajax({
			type: "POST",
			url: server + "/game/roll",
			data: jsonMessage,
			contentType: "application/json",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(data) {
					drawPlayers(game);
				},
				400: function(){
					alert("Game not found (rolling)");
				},
				403: function() {
					alert("Game not found (rolling)");
				}
			}
		});
	});
	
	function joinGame(gameId) {
		$.ajax({
			type: "GET",
			url: server + "/game/get",
			data: {"gameId": gameId},
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(game) {
					$("#gameList tr").remove();
					currentGame = game.gameId;
					$("#gameList > tbody:last").append("Joined game " + currentGame);
					addPlayerToGame();
				}
			}
		});
	};
	
	function addPlayerToGame() {
		var gameForm = new Object();
		gameForm.player = name;
		gameForm.gameId = currentGame;
		var jsonMessage = JSON.stringify(gameForm);
		$.ajax({
			type: "POST",
			url: server + "/game/addPlayer",
			data: jsonMessage,
			contentType: "application/json",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(data) {
					drawPlayers(game);
				},
				400: function(){
					alert("Game not found (adding player)");
				}
			}
		});
	};
	
	function drawPlayers() {
		$.ajax({
			type: "GET",
			url: server + "/game/get",
			data: {"gameId": currentGame},
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(game) {
					$.each(game.players, function(k,v) {
						var coords = v.position.coordinates;
						var imageId = v.name + "_image";
						$('<img />', {
							src: '/images/token-red.png',
							class: 'playerImages',
							id: imageId
						}).appendTo($("div#game"));
						$("img#" + imageId).css("top", (coords.y - 12) + "px");
						$("img#" + imageId).css("left", (coords.x - 12) + "px");
					});
				},
				400: function() {
					alert("Game not found (drawing players)");
				}
			}
		});
	};
	
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
		$("#game").hide();
		loggedUser = null;
		name = null;
		$("#chatInput").prop('disabled', true);
	});
});
