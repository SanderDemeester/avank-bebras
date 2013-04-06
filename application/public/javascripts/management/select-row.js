/*
 * Script for selecting rows in a table.
 * author: Kevin Stobbelaar
 */

$(document).ready(function() {

    var aSelected = [];

    /* Click event handler */
    $('#my_table tbody tr').live('click', function () {
        var id = this.id;
        var index = jQuery.inArray(id, aSelected);
        if ( index === -1 ) {
            aSelected.push( id );
        } else {
            aSelected.splice( index, 1 );
        }
        $(this).toggleClass('success');
    });

});