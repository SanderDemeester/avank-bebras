// validate used name attr.
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
				if(!value){
				return true;
				}
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
				email_check: true,
				required:false
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
	
	$("#reset_password").validate({
		
		rules:{
			bebras_id:"required",
			r_password:"required",
			controle_passwd:{
					required:true,
					equalTo: "#r_password"
			}
			
		},
		errorClass: "help-inline"
		
	});

	$("#change_password").validate({

    		rules:{
    			current_pwd:"required",
    			n_password:"required",
    			controle_password:{
    					required:true,
    					equalTo: "#n_password"
    			}

    		},
    		errorClass: "help-inline"

    });
});