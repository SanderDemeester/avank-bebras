$(document).ready(function(){
	$("#btn").live("click",function(){
		var password = $("#passwd").val();
		var salt = CryptoJS.SHA256(password); // eventueel kunnen hier later ook al onze bebras id bepalen en als salt gebruiken voor de hash.

		var PBKDF2_password = CryptoJS.PBKDF2(password,salt,{ keySize: 4, iterations: 2500});
		$("#passwd").val(PBKDF2_password);
		$("#controle_passwd").val(PBKDF2_password);
		alert(PBKDF2_password);
	});
});