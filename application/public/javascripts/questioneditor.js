/**
 * Client-side code for the question editor
 * @author: Ruben Taelman
 */

var questionType = "@questionType";
var activeEditor;
var fileUploader;

// Custom templates for the WYSIWYG editor
var myCustomTemplates = {
	    image : function(locale) {
	      return "<li><div class='btn-group'>" +
	        "<a id='addFiles' class='btn' title='" + locale.link.insert + "'><i class='icon-picture'></i></a>" +
	      "</div></li>";
	    },
};

// Adds a file to the file list
function appendFile(file) {
	$('<tr/>').html("<td>"+file.name+"</td>"
      		//+ "<td>"+file.size+"</td>"
      		+ "<td><img src='"+file.url+"' width='50' /></td>"
      		+ "<td><a class='addImage btn'><i class='icon-plus-sign'></i></a><a class='removeImage btn'><i class='icon-remove'></i></a></td>")
      		.data("name", file.name)
      		.data("url", file.url)
      		.appendTo($("#files"));
}
	    
$(document).ready(function() {
	// Show confirm dialog on leaving page
	window.onbeforeunload = function() {
	    return 'Are you sure you want to navigate away from this page? Unsaved changes will be lost.';
	};
	
	// Call this for removing the warning after save
	//window.onbeforeunload = null;
	
	// Load the uploaded files for the first time
	$.getJSON('../files.json')
	.done(function(data) {
		$.each(data.files, function(i, file) {
			  appendFile(file);
		  });
	})
	.fail(function () {
		$("#uploadStatus").html('<div class="alert alert-error">Upload server unavailable.</div>');
    });
	
	// Initialize the file uploader
	fileUploader = $('#fileupload').fileupload({
        dataType: 'json',
        add: function (e, data) {
            data.context = $("#uploadStatus").html('<div class="alert alert-info">Uploading...</div>');
            data.submit();
            $('#fileProgress').show();
        },
        done: function (e, data) {
            $.each(data.result.files, function (index, file) {
                appendFile(file);
            });
            data.context.html('<div class="alert alert-success">File(s) uploaded.</div>');
            $('#fileProgress .bar').css('width', '0%').parent().hide();
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#fileProgress .bar').css(
                'width',
                progress + '%'
            );
        },
        fail: function(e, data) {
        	$("#uploadStatus").html('<div class="alert alert-error">Invalid image file.</div>');
        	$('#fileProgress').hide();
        },
        dropZone: $('#dropzone'),
    });
	
});

// Enable drag & drop file uploader
$(document).bind('dragover', function (e) {
    var dropZone = $('#dropzone'),
        timeout = window.dropZoneTimeout;
    if (!timeout) {
        dropZone.addClass('in');
    } else {
        clearTimeout(timeout);
    }
    if (e.target === dropZone[0]) {
        dropZone.addClass('hover');
    } else {
        dropZone.removeClass('hover');
    }
    window.dropZoneTimeout = setTimeout(function () {
        window.dropZoneTimeout = null;
        dropZone.removeClass('in hover');
    }, 100);
});

// Open file upload modal
$("#addFiles").live("click", function(){
	$('#uploadFile').modal();
}); 

// Close modal and cancel any uploads
$(".cancelUpload").live("click", function(){
	$('#fileProgress').hide();
	$("#uploadStatus").html('');
	fileUploader.abort();
}); 

//Add the image from this row to the current editor
$(".addImage").live("click", function(){
	var row = $(this).parent().parent();
	var name = row.data("name");
	var url = row.data("url");
	var editorInstance = activeEditor.data("editor").data("wysihtml5").editor;
	editorInstance.composer.commands.exec("insertImage", { src: url, alt: name });
	editorInstance.currentView.element.focus();
	$("#uploadFile").modal('toggle');
});

// Remove this file from the question
$(".removeImage").live("click", function(){
	var row = $(this).parent().parent();
	
	$.ajax({
		type: "GET",
		url: "../delete",
		data: { name: row.data("name") }
	}).done(function( msg ) {
		row.hide('fast', function() {
			row.remove();
		});
	});	
});

// Add a language to the question
$('#addLanguage').click(function(e) {
	e.preventDefault();
	
	selected = $('#languageList').find('option:selected').remove();
	if(selected.val()!=undefined) {
		// Remove active class on other items
		$(".languageItem").removeClass("active");
		
		// Fix lists content
		var newLang = $(document.createElement('li')).addClass("languageItem active").css('display', 'none');
		newLang.html('<i class="icon-remove removelang"></i><a href="#" class="selectLang">'+selected.val()+'</a>');
		$("#languages").append(newLang);
		newLang.show('fast');
		$("#questionPanel").append();
		
		// Hide the other panels
		$(".questionFrame").hide();
		
		// Make new question panel
		template = $('#panelTemplate').clone();
		template.removeClass("hide");
		template.attr("id", "panel-"+selected.val());
		var editor = template.find('.editor');
		editor.each(function() {
			var instance = $(this).wysihtml5({
				"html": false,
				"link": false,
				"image": true,
				customTemplates: myCustomTemplates,
			});
			$(this).data("editor", instance);
		});
		activeEditor = editor;
		$('#questionPanel').append(template);
		
		// Show the newly created panel
		$(template).show('fast');
		
		// Hide default panel
		$('#noPanelSelected').hide();
		
		// Type specific logics
		if(questionType=="MULTIPLE_CHOICE") {
			template.find(".input").data("counter", 0);
			template.find(".input").data("lang", selected.val());
		} else if(questionType=="REGEX") {
			
		}
	
	}
	
	return false;
});

// Open confirmation modal for removing a language from the question
$("i.removelang").live("click", function(){
	lang = $(this).parent().find("a").text();
	$("#removelang").text(lang);
	$('#removeLanguageConfirm').modal();
}); 

// Remove a language from the question
$("#confirmRemoveLang").live("click", function(){
	// Fix lists content
	lang = $("#removelang").text();
	removeLang = $("#languages").find("li a:contains('"+lang+"')").parent();
	removeLang.hide('fast', function() {
		removeLang.remove();
	});
	$("#languageList").append('<option value="'+lang+'">'+lang+'</option>');
	
	// Remove active class on other items
	$(".languageItem").removeClass("active");
	
	// Hide the other panels and remove this panel
	$(".questionFrame").hide(function(e) {
		$("#panel-"+lang).remove();
	});
	$('#removeLanguageConfirm').modal('toggle');
	
	// Show default panel
	$('#noPanelSelected').show('fast');
});

//Select a language tab
$(".selectLang").live("click", function(){
	lang = $(this).text();
	
	// Remove active class on other items
	$(".languageItem").removeClass("active");
	
	// Set active class on this item
	$(this).parent().addClass("active");
	
	// Hide the other panels
	$(".questionFrame").hide();
	
	// Hide default panel
	$('#noPanelSelected').hide();
	
	// Show the correct panel
	$("#panel-"+lang).show();
	
	// Set the default editor
	activeEditor = $("#panel-"+lang).find("div:visible").find(".editor");
});

/* --- Tabs logic --- */
$(".tabGeneral").live("click", function(){	
	$(this).parent().parent().parent().find(".generalTab").show();
	$(this).parent().parent().parent().find(".answersTab").hide();
	$(this).parent().parent().parent().find(".indexTab").hide();
	$(this).parent().parent().parent().find(".feedbackTab").hide();
});

$(".tabAnswers").live("click", function(){	
	$(this).parent().parent().parent().find(".generalTab").hide();
	$(this).parent().parent().parent().find(".answersTab").show();
	$(this).parent().parent().parent().find(".indexTab").hide();
	$(this).parent().parent().parent().find(".feedbackTab").hide();
});

$(".tabIndex").live("click", function(){
	$(this).parent().parent().parent().find(".generalTab").hide();
	$(this).parent().parent().parent().find(".answersTab").hide();
	activeEditor = $(this).parent().parent().parent().find(".indexTab").show().find('.editor');
	$(this).parent().parent().parent().find(".feedbackTab").hide();
});

$(".tabFeedback").live("click", function(){
	$(this).parent().parent().parent().find(".generalTab").hide();
	$(this).parent().parent().parent().find(".answersTab").hide();
	$(this).parent().parent().parent().find(".indexTab").hide();
	activeEditor = $(this).parent().parent().parent().find(".feedbackTab").show().find('.editor');
});
/* ---         --- */

// Add MC input element
$(".addMCElement").live("click", function(){
	var counter = $(this).parent().data("counter");
	var lang = $(this).parent().data("lang");
	
	var answerDiv = $(document.createElement('div')).attr("id", 'answer-' + counter).css('display', 'none');
	answerDiv.html('<label class="mc_answer row-fluid">'
			+'<div class="span1"><i class="icon-remove removeanswer"></i></div>'
			+'<div class="span8"><input class="span12" type="text" name="answer-' + counter
		      + '" id="answer-textbox-' + counter + '" value="" placeholder="A possible answer"/></div>'
		    +'<div class="span3">Correct: <input type="radio" name="correct_answer_' +
		      lang+'" value="'+ counter+'"></div>'
		    +'</label>');
	$(this).parent().find(".answers").append(answerDiv);
	answerDiv.show('fast');
	
	$(this).parent().data("counter", counter+1);
});

// Remove an MC input element
$(".removeanswer").live("click", function(){
	// Don't lower counter, because an earlier element can also be removed instead of the last one
	$(this).parent().parent().hide('fast', function() {
		$(this).parent().parent();
	});
});