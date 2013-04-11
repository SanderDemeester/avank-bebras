/**
 * Makes text fields with the class ".enterish" submit the form with id
 * "#enterish" when the user hits enter.
 */
$("input.enterish").bind("keyup", function(event) {
    if(event.which == 13) $("form.enterish").submit();
});
