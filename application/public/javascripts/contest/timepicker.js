$(function() {
    $('#starttimepicker').datetimepicker({
        language: 'fr',
        pickSeconds: false,
        startDate: new Date()
    });
    $('#endtimepicker').datetimepicker({
        language: 'fr',
        pickSeconds: false,
        startDate: new Date()
    });
});