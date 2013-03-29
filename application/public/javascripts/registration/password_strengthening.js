$(document).ready(function(){
	$("#btn").click(function(){
		var password = $("#password").val();
		var username = $("#email").val();
		var salt = CryptoJS.SHA256(password.concat(username));

		var PBKDF2_password = CryptoJS.PBKDF2(password,salt,{ keySize: 4, iterations: 2500});
		$("#password").val(PBKDF2_password);
		$("#controle_passwd").val(PBKDF2_password);

		alert(PBKDF2_password);

	});
});