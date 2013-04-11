$(document).ready(function(){
	$("#signup").validate({
		rules:{
			gender:"required",
			name:"required",
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