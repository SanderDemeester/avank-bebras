$(document).ready(function(){
	$("#btn").live("click",function(){
		if($("#signup").validate().form() == true){
		var password = $("#register_password").val();
		var salt = CryptoJS.SHA256(password); // eventueel kunnen hier later ook al onze bebras id bepalen en als salt gebruiken voor de hash.
		var PBKDF2_password = CryptoJS.PBKDF2(password,salt,{ keySize: 4, iterations: 10});
		$("#register_password").val(PBKDF2_password);
		$("#controle_passwd").val(PBKDF2_password);
		}else{
			return false;
		}
	});
	
	$("#btn_reset").live("click",function(){
		
		
		if($("#reset_password").validate().form()){
		var password = $("#r_password").val();
		var salt = CryptoJS.SHA256(password); // eventueel kunnen hier later ook al onze bebras id bepalen en als salt gebruiken voor de hash.
		var PBKDF2_password = CryptoJS.PBKDF2(password,salt,{ keySize: 4, iterations: 10});
		$("#r_password").val(PBKDF2_password);
		$("#controle_passwd").val(PBKDF2_password);
		}else{
			return false;
		}
		
	});

	$("#btn_changepwd").live("click",function(){
		if($("#change_password").validate().form()){
    		var password = $("#n_password").val();
    		var salt = CryptoJS.SHA256(password); // eventueel kunnen hier later ook al onze bebras id bepalen en als salt gebruiken voor de hash.
    		var PBKDF2_password = CryptoJS.PBKDF2(password,salt,{ keySize: 4, iterations: 10});
    		$("#current_pwd").val(PBKDF2_password);
    		$("#n_password").val(PBKDF2_password);
    		$("#controle_password").val(PBKDF2_password);
    	}else{
    		return false;
    	}
	});

});