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
	$("#"+id).show()
})

// When the user initiates a submit
$("#submit").live("click", function() {
	if(confirm("Are you sure you want to submit your answers and finish the competition?")) submit();
});

// Submit the answers to the server
function submit() {
	// Try to send to server with modal
	$("#submitting").modal({
		keyboard: false,
		backdrop: "static",
	});
	var spinner = new Spinner(spinner_opts).spin();
	$(".icon-spinner").append(spinner.el);
	
	// If timeout -> show modal with cookie data & info
	
	// If error (invalid countdown)
	lostConnection();
}

// When the user has lost internetconnection on submitting
function lostConnection() {
	$("#submitting").modal("hide");
	$("#lostConnection").modal({
		keyboard: false,
		backdrop: "static",
	});
	$("#answerscode").text(JSON.stringify(answers));
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
	$("#finished-text").text("Time's up!");
	submit();// With time's up message
}

// Update timeleft in answers object
function updateTimeleft(periods) {
	answers.timeleft = periods[0]*60*60 + periods[1]*60 + periods[2];
}

// On page loaded
$(document).ready(function() {
	
	// Set countdown
	deaddate = new Date(deadline);
	$('#countdown').countdown({
		until: deaddate,
		format: 'HMS',
		layout: '{hn} : {mn} : {sn}',
		onExpiry: expired,
		onTick: updateTimeleft,
	});
	
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