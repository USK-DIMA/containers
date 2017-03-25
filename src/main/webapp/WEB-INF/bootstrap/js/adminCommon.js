$(document).ready(function() {
    setActiveLiMenu();
});

function setActiveLiMenu(){
var pathname = window.location.pathname;
    $("#admin-menu").find('a').each(function(){
        if(pathname.startsWith($(this).attr('href'))) {
            $(this).parent().addClass('active');
        }
    });
}