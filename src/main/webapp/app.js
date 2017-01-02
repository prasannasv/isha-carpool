'use strict';

// Make all table's rows as clickable if it has data-href set.
// http://stackoverflow.com/a/29129307/7247103
// TODO: Make it run on a change to any of the tables we render. Looks like Angular directives can help here.
$(function() {
    $('.table tr[data-href]').each(function() {
        $(this).css('cursor','pointer').hover(
            function() {
                $(this).addClass('active');
            },
            function() {
                $(this).removeClass('active');
            }).click( function() {
                document.location = $(this).attr('data-href');
            }
        );
    });
});
