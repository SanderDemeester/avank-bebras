$(document).ready(function(){
	$("#signup").validate({
		rules:{
			gender:"required",
			fname:"required",
			lname:"required",
			email:{
				required:true,
				email: true
			},
			passwd:{
				required:true,
				minlength: 8
			},
			controle_passwd:{
				required:true
			},
			bday:{
				required:true
			}

		},
		errorClass: "help-inline"
	});
});