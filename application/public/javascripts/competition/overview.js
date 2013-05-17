// When the counter reaches zero
function expired() {
	$("#finished-text").text(messages.timesUp);
	submit();// With time's up message
}

// Update timeleft in answers object
function updateTimeleft(periods) {
	var timeleft = periods[4]*60*60 + periods[5]*60 + periods[6];
	var progress = (deadline-starting-(timeleft*1000))/(deadline-starting);
	$("#timeprogress").find(".bar").width(""+progress*100+"%");
}

function updateFinished() {
	$.getJSON('../competition-overview-data/'+competitionid, function(data) {
		console.log(data);
		
		$("#finished").find(".finished").text(data.amountFinished);
		$("#finished").find(".total").text(data.amountRegistered);
		
		$("#finishedprogress").find(".bar").width(""+(data.amountFinished/data.amountRegistered)*100+"%");
	});
}

// On page loaded
$(document).ready(function() {
	
	deaddate = new Date(deadline);
	$('#countdown').countdown({
		until: deaddate,
		format: 'HMS',
		layout: '{hn} : {mn} : {sn}',
		onExpiry: expired,
		onTick: updateTimeleft,
	});
	
	updateFinished();
	setInterval(function(){updateFinished()},5000);
});

$("#stopCompetition").live("click", function() {
	if(confirm(messages.confirmFinish)) {
		$("#stopCompetition").attr("disabled", "disabled");
		
		// Try to send to server
		var request = $.ajax({
			type: "GET",
			url: "../competition/finish/"+competitionid,
		})
		
		request.done(function(msg) {
			$("#alert").show('fast').attr("class", "alert alert-success").text(messages.finished);
			window.setTimeout(function() { $("#alert").hide('fast'); }, 5000);
			$('#countdown').countdown("destroy");
		});	
		
		// If timeout -> show modal with cookie data & info
		request.fail(function(jqXHR, textStatus) {
			$("#alert").show('fast').attr("class", "alert alert-error").text(jqXHR.responseText);
			window.setTimeout(function() { $("#alert").hide('fast'); }, 5000);
			$("#stopCompetition").removeAttr("disabled");
		});
	}
});

$("#submitAnswers").live("click", function() {
	$("#submitAnswersModal").modal();
});

$("#submitAnswersCancel").live("click", function() {
	$("#submitAnswersModal").modal("hide");
});

$("#submitAnswersConfirm").live("click", function() {
	$("#submitAnswersModal").modal("hide");
	$("#submitAnswersModal").on("hidden", function() {
		// Show modal
		$("#submitting").modal({
			keyboard: false,
			backdrop: "static",
		});
		var spinner = new Spinner(spinner_opts).spin();
		$(".icon-spinner").append(spinner.el);
		
		$("#submitting").on("shown", function() {
			// Try to send to server
			var request;
			try {
				request = $.ajax({
					type: "GET",
					url: "../../available-contests/forceSubmit",
					data: { json: $.base64.decode($("#answerscode").val()) },
					timeout: 5000,
				});
			} catch (e) {
				$("#alert").show('fast').attr("class", "alert alert-error").text(messages.submitinvalid);
				window.setTimeout(function() { $("#alert").hide('fast'); }, 5000);
				$("#submitting").modal("hide");
			}
			$("#answerscode").val("");
			
			request.done(function(msg) {
				alert(messages.submitok);
				$("#alert").show('fast').attr("class", "alert alert-success").text(messages.submitok);
				window.setTimeout(function() { $("#alert").hide('fast'); }, 5000);
			});	
			
			// If timeout -> show modal with cookie data & info
			request.fail(function(jqXHR, textStatus) {
				$("#alert").show('fast').attr("class", "alert alert-error").text(jqXHR.responseText);
				window.setTimeout(function() { $("#alert").hide('fast'); }, 5000);
			});
			
			request.always(function(jqXHR, textStatus, errorThrown) {
				$("#submitting").modal("hide");
			});	
		});
	});
});