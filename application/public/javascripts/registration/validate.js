$(document).ready(function(){
	$.validator.addMethod(
			"date_check",
			function(value, element){
	            return value.match(/^\d{4}[\/\](0?[1-9]|1[012])[\/\](0?[1-9]|[12][0-9]|3[01])$/);
			},
			"dd/mm/yyyy"
			);
	$("#signup").validate({
		rules:{
			gender:"required",
			name:"required",
			prefLanguage:"required",
			email:{
				required:true,
				email: true
			},
			login_passwd:{
				required:true,
				minlength: 8
			},
			controle_passwd:{
				required:true
			},
			bday:{
				required:true,
				date_check:true
			}

		},
		errorClass: "help-inline"
	});
});