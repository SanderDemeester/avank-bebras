$(document).ready(function(){
	$.validator.addMethod(
			"date_check",
			function(value, element){
				return value.match(/^\s*[\w\-\+_]+(\.[\w\-\+_]+)*\@[\w\-\+_]+\.[\w\-\+_]+(\.[\w\-\+_]+)*\s*$/);
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
			}

		},
		errorClass: "help-inline"
	});
});