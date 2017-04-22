function getContextPath(){
    var pathname = window.location.pathname.substr(1);
    return "/" + pathname.substring(0, pathname.indexOf('/'));
}