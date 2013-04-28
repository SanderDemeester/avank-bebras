/**
 * Client-side code for the running of competitions
 * @author: Ruben Taelman
 */

// Apply our stylesheets to the iframe source
$(".questionframe").load(function() {
	var $head = $(this).contents().find("html");
	
	for (var i = 0; i < links.length; i++) {
		$head.append($("<link/>", 
    	{ rel: "stylesheet", href: links[i], type: "text/css" }));
	}
});

// Change question tab
$(".pageClick").live("click", function(){
	var id = $(this).find(".id").text();
	$(".question").hide();
	$(".pageClick").each(function() {
		$(this).parent().removeClass("active");
	});
	$("#"+id).show();
	$(this).parent().addClass("active");
})

// When the user initiates a submit
$("#submit").live("click", function() {
	if(!feedback && confirm(messages.confirmSubmit)) submit();
});

// Retry submitting if lost connection
$("#retry").live("click", function() {
	$("#lostConnection").modal("hide").on('hidden', function () {
		submit();
	})
});

// Submit the answers to the server
function submit() {
	// Show modal
	$("#submitting").modal({
		keyboard: false,
		backdrop: "static",
	});
	var spinner = new Spinner(spinner_opts).spin();
	$(".icon-spinner").append(spinner.el);
	
	$("#submitting").on("shown", function() {
		// Try to send to server
		var request = $.ajax({
			type: "GET",
			url: "./submit",
			data: { json: JSON.stringify(answers) },
		})
		
		request.done(function(msg) {
			// If ok -> redirect to finished page
			
			success();
			// If error (invalid countdown)
		});	
		
		// If timeout -> show modal with cookie data & info
		request.fail(function(jqXHR, textStatus) {
			if(textStatus == "timeout" || textStatus == "abort") lostConnection();
			else showError(jqXHR.responseText);
		});
		
		request.always(function(jqXHR, textStatus, errorThrown) {
			$("#submitting").modal("hide");
		});	
	});
}

// When the submission of answers went without errors
function success() {
	// Destroy countdown
	$("#countdown").countdown('destroy');
	
	$("#finished").modal({
		keyboard: false,
		backdrop: "static",
	});
	// TODO: depending on the competition type
	//$("#showFeedback").hide();
}

$("#ready").live("click", function(){
	window.location.href = "/";
});

$("#showFeedback").live("click", function(){
	alert("TODO");
	//window.location.href = "feedback/"+JSON.stringify(answers);
});

// When the user has lost internetconnection on submitting
function lostConnection() {
	showError(messages.noInternet);
	
	// Show specific lost connection content
	$("#lostConnectionContent").show();
	$("#answerscode").text($.base64.encode(JSON.stringify(answers)));
}

function showError(error) {
	// Try to hide lost connection content
	$("#lostConnectionContent").hide();
	
	// Set error message
	$("#modalError").text(error);
	
	// Destroy countdown
	$("#countdown").countdown('destroy');
	
	// Show modal
	$("#submitting").modal("hide");
	$("#lostConnection").modal({
		keyboard: false,
		backdrop: "static",
	});
}

$("#answerscode").live("focus", function(){	
	var $this = $(this);

	// Browser will try to select first
    window.setTimeout(function() {
        $this.select();
    }, 1);
	
	// Chrome bug
    $this.mouseup(function() {
        // Prevent further mouseup intervention
        $this.unbind("mouseup");
        return false;
    });
    return false;
});

// When the counter reaches zero
function expired() {
	$("#finished-text").text(messages.timesUp);
	submit();// With time's up message
}

// Update timeleft in answers object
function updateTimeleft(periods) {
	answers.timeleft = periods[4]*60*60 + periods[5]*60 + periods[6];
}

// On page loaded
$(document).ready(function() {
	
	// Set countdown
	if(!feedback) {
		deaddate = new Date(deadline);
		$('#countdown').countdown({
			until: deaddate,
			format: 'HMS',
			layout: '{hn} : {mn} : {sn}',
			onExpiry: expired,
			onTick: updateTimeleft,
		}).parent().show("fast");
		$("#submit").parent().show("fast");
	} else {
		$("#competitionScore").parent().show("fast");
	}
	
});

// Multiple choice element select
$(".multiple_choice").live("change", function() {
	var id = $(this).attr("name");
	var content = $(this).parent().find(".content").text();
	answers.questions[id] = content;
});

// Regex input change
$(".regex").live("change", function() {
	var id = $(this).attr("name");
	var content = $(this).attr("value");
	answers.questions[id] = content;
});

// Trigger regex input change with every keystroke
$(".regex").keyup(function() {
	$(this).trigger("change");
});