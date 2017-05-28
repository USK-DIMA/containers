var stompClient = null;
var REQUEST_UPDATE_TABLE_PAUSE = 5000; //период, через который отпрвялется запрос на сервер с просьбой обновить таблицу

var currentTimestamp;
function connectToMonitorWebSocket(initCallback, updateCallBack) {
    var socket = new SockJS(getContextPath() +'/monitor-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        //setConnected(true);
        stompClient.subscribe('/topic/init', function(initTimestamp){
            currentTimestamp = initTimestamp.body;
            initCallback().done(function(){
                //stompClient = Stomp.over(socket);
                startUpdatingTable(updateCallBack);
            });
        });
        stompClient.send("/monitor/init", {});
    });
}

function startUpdatingTable(updateCallBack) {
    /**
    * Обработчик новых данных датчиков, пришедших с сервера
    */
    stompClient.subscribe('/topic/getNew', function(monitorTableContainer) {
        var message = JSON.parse(monitorTableContainer.body);
        currentTimestamp = message.timestamp;
        updateCallBack(message.monitorTableForm);
    });
    // в цикле отправляем запросы на обновление
    setInterval(requestUpdateTableWebSocket, REQUEST_UPDATE_TABLE_PAUSE);
}

function getCurrentUserId() {
    return $('[data-user-id]').attr('data-user-id');
}

/**
 Отправляет запрос на сервер с просьбой прислать обновлённые данные
*/
function requestUpdateTableWebSocket() {
    stompClient.send("/monitor/getNew", {}, JSON.stringify({userId: getCurrentUserId(), timestamp: currentTimestamp}));
}