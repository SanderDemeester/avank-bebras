$(function() {

    $.getJSON('/questionset/questions/add-data', function(data) {

        $.each(data, function(key, val) {
            $('#search').typeahead({source: data.array});
        });

    });

});