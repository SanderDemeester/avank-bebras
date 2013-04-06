/**
 * Client-side code for the question editor index uploader
 * @author: Ruben Taelman
 */

$("#importUpload").live("click", function(){
	$('input[type=file]').trigger('click');
});

$("input:file").change(function (){
    $(this).parent().submit();
});