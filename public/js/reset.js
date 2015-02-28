$(function() {
	var server = "http://finanssi.nuthou.se:8080";
	$("#buttonNewPassword").click(function() {
		var user = new Object();
		user.password = $("#password").val();
		user.resetToken = getUrlParameter("token");
		var jsonUser = JSON.stringify(user);
		$.ajax({
			type: "POST",
			url: server + "/user/setPassword",
			data: jsonUser,
			contentType: "application/json",
			dataType: "json",
			crossDomain: true,
			statusCode: {
				200: function(data) {
					alert("OK");
				},
				400: function(){
					alert("Password setup failed");
				}
			}
		});
	});

	function getUrlParameter(sParam)
	{
		var sPageURL = window.location.search.substring(1);
		var sURLVariables = sPageURL.split('&');
		for (var i = 0; i < sURLVariables.length; i++) 
		{
			var sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] == sParam) 
			{
				return sParameterName[1];
			}
		}
	}  
});