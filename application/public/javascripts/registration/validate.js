$(document).ready(function(){
	$.validator.addMethod(
			"date_check",
			function(value, element){
			     return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
			},
			"dd/mm/yyyy"
			);
	$.validator.addMethod(
			"email_check",
			function(value, element){
				return value.match(/^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/);
			},
			"email"
			);
	$("#signup").validate({
		rules:{
			gender:"required",
			name:"required",
			prefLanguage:"required",
			email:{
				email_check: true
			},
			register_password:{
				required:true,
				minlength: 8
			},
			controle_passwd:{
				required:true,
				equalTo : "#register_password"
			},
			bday:{
				required:true,
				date_check: true
			}

		},
		errorClass: "help-inline"
	});
});