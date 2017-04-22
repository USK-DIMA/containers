$(document).ready(function() {
    setActiveLiMenu();
});


/**
*  Функция автоматически выделяет актиный пункт меню, основываясь на атрибует href пункта меню и текущему адресу старницы
*/
function setActiveLiMenu(){
    var pathname = window.location.pathname;
    $("#admin-menu").find('a').each(function(){
        if(pathname.startsWith($(this).attr('href'))) {
            $(this).parent().addClass('active');
        }
    });
}