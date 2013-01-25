$(function () {
    "use strict";

    var myName = false;
    var author = null;
    var logged = false;
    var socket = $.atmosphere;
    var subSocket;
    var socket = $.atmosphere;
    var transport = 'websocket';

    // We are now ready to cut the request
    var request = { url: 'http://172.24.39.54:8080/' ,
        contentType : "application/json",
        logLevel : 'debug',
        shared : true,
        transport : transport ,
        trackMessageLength : true,
        fallbackTransport: 'long-polling',
				crossDomain: true	
};


    request.onOpen = function(response) {
        console.log('Atmosphere connected using ' + response.transport );
        transport = response.transport;

        if (response.transport == "local") {
            subSocket.pushLocal("Name?");
        }
    };

    <!-- You can share messages between window/tabs.   -->
    request.onLocalMessage = function(message) {
        if (transport != 'local') {
            console.log($('<h4>', { text: 'A new tab/window has been opened'}).css('color', 'green'));
            if (myName) {
                subSocket.pushLocal(myName);
            }
        } else {

        }
    };

    <!-- For demonstration of how you can customize the fallbackTransport using the onTransportFailure function -->
    request.onTransportFailure = function(errorMsg, request) {
        jQuery.atmosphere.info(errorMsg);
        if (window.EventSource) {
            request.fallbackTransport = "sse";
        }
        console.log($('<h3>', { text: 'Atmosphere Chat. Default transport is WebSocket, fallback is ' + request.fallbackTransport }));
    };

    request.onReconnect = function (request, response) {
        socket.info("Reconnecting")
    };

    request.onMessage = function (response) {
					
			 	

        var message = response.responseBody;
				console.log(message);

    };

    request.onClose = function(response) {
        logged = false;
    }

    request.onError = function(response) {
        console.log('Sorry, but there\'s some problem with your socket or the server is down');
    };

		window.subSocket = socket.subscribe(request);


});
